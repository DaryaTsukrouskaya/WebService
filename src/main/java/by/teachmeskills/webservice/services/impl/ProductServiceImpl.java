package by.teachmeskills.webservice.services.impl;

import by.teachmeskills.springbootproject.entities.Cart;
import by.teachmeskills.springbootproject.entities.Product;
import by.teachmeskills.springbootproject.enums.PagesPathEnum;
import by.teachmeskills.springbootproject.exceptions.DBConnectionException;
import by.teachmeskills.springbootproject.exceptions.UserAlreadyExistsException;
import by.teachmeskills.springbootproject.repositories.ProductRepository;
import by.teachmeskills.springbootproject.repositories.impl.ProductRepositoryImpl;
import by.teachmeskills.springbootproject.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;

    @Autowired
    public ProductServiceImpl(ProductRepositoryImpl productRepository) {
        this.productRepository = productRepository;
    }


    @Override
    public List<Product> read() throws DBConnectionException {
        return productRepository.read();
    }

    @Override
    public void create(Product product) throws DBConnectionException, UserAlreadyExistsException {
        productRepository.create(product);

    }

    @Override
    public void delete(int id) throws DBConnectionException {
        productRepository.delete(id);
    }

    @Override
    public Product findById(int id) throws DBConnectionException {
        return productRepository.findById(id);
    }

    @Override
    public ModelAndView getProductsByCategory(int id) throws DBConnectionException {
        ModelMap modelMap = new ModelMap();
        modelMap.addAttribute("categoryProducts", productRepository.getProductsByCategory(id));
        return new ModelAndView(PagesPathEnum.CATEGORY_PAGE.getPath(), modelMap);
    }

    @Override
    public ModelAndView addProductToCart(int productId, Cart cart) throws DBConnectionException {
        ModelMap modelMap = new ModelMap();
        Product product = findById(productId);
        cart.addProduct(product);
        modelMap.addAttribute("cart", cart);
        modelMap.addAttribute("product", product);
        return new ModelAndView(PagesPathEnum.PRODUCT_PAGE.getPath(), modelMap);
    }

    @Override
    public ModelAndView deleteProductFromCart(int productId, Cart cart) {
        ModelMap modelMap = new ModelMap();
        cart.removeProduct(productId);
        modelMap.addAttribute("cart", cart);
        if (cart.getProducts().isEmpty()) {
            return new ModelAndView(PagesPathEnum.EMPTY_CART_PAGE.getPath());
        }
        return new ModelAndView(PagesPathEnum.CART_PAGE.getPath(), modelMap);
    }

    @Override
    public ModelAndView clearCart(Cart cart) {
        cart.clear();
        ModelMap modelMap = new ModelMap();
        if (cart.getProducts().isEmpty()) {
            return new ModelAndView(PagesPathEnum.EMPTY_CART_PAGE.getPath());
        }
        return new ModelAndView(PagesPathEnum.CART_PAGE.getPath(), modelMap.addAttribute("cart", cart));
    }


    @Override
    public ModelAndView findProductByIdForProductPage(int id) throws DBConnectionException {
        ModelMap modelMap = new ModelMap();
        Product product = findById(id);
        modelMap.addAttribute("categoryName", product.getName());
        modelMap.addAttribute("product", product);
        return new ModelAndView(PagesPathEnum.PRODUCT_PAGE.getPath(), modelMap);
    }

    @Override
    public ModelAndView searchProductsPaged(int pageNumber, String keyWords) throws DBConnectionException {
        Long totalRecords;
        List<Product> products;
        int pageMaxResult;
        ModelMap modelMap = new ModelMap();
        if (keyWords != null) {
            totalRecords = productRepository.findProductsQuantityByKeywords(keyWords);
            pageMaxResult = (int) (totalRecords / 3);
            products = productRepository.findProductsByKeywords(keyWords, pageNumber, pageMaxResult);
            modelMap.addAttribute("products", products);
        }
        return new ModelAndView(PagesPathEnum.SEARCH_PAGE.getPath(), modelMap);
    }
}
