package by.teachmeskills.webservice.services;


import by.teachmeskills.springbootproject.entities.Cart;
import by.teachmeskills.springbootproject.entities.Product;
import by.teachmeskills.springbootproject.exceptions.DBConnectionException;
import org.springframework.web.servlet.ModelAndView;

public interface ProductService extends BaseService<Product> {
    Product findById(int id) throws DBConnectionException;

    ModelAndView getProductsByCategory(int id) throws DBConnectionException;

    ModelAndView addProductToCart(int id, Cart cart) throws DBConnectionException;

    ModelAndView deleteProductFromCart(int id, Cart cart);

    ModelAndView findProductByIdForProductPage(int id) throws DBConnectionException;

    ModelAndView clearCart(Cart cart);

    public ModelAndView searchProductsPaged(int pageNumber, String keyWords) throws DBConnectionException;
}
