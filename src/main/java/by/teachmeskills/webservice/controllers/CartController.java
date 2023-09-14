package by.teachmeskills.webservice.controllers;

@RestController
@RequestMapping("/cart")
@SessionAttributes({"cart"})
public class CartController {
    private final ProductService productService;
    private final UserService userService;
    private final OrderService orderService;

    @Autowired
    public CartController(ProductServiceImpl productService, UserServiceImpl userService, OrderServiceImpl orderService) {
        this.productService = productService;
        this.userService = userService;
        this.orderService = orderService;
    }

    @GetMapping
    public ModelAndView openCartPage(@ModelAttribute("cart") Cart cart) {
        if (cart.getProducts().isEmpty()) {
            return new ModelAndView(PagesPathEnum.EMPTY_CART_PAGE.getPath());
        }
        ModelMap modelMap = new ModelMap();
        return new ModelAndView(PagesPathEnum.CART_PAGE.getPath(), modelMap.addAttribute("cart", cart));
    }

    @GetMapping("/add")
    public ModelAndView addProduct(@RequestParam("product_id") int id, @ModelAttribute("cart") Cart cart) throws DBConnectionException {
        return productService.addProductToCart(id, cart);

    }

    @GetMapping("/delete")
    public ModelAndView deleteProductFromCart(@ModelAttribute("cart") Cart cart, @RequestParam("product_id") int id) {
        return productService.deleteProductFromCart(id, cart);
    }

    @GetMapping("/clear")
    public ModelAndView clearCart(@ModelAttribute("cart") Cart cart) {
        return productService.clearCart(cart);
    }

    @GetMapping("/checkout")
    public ModelAndView checkout(@ModelAttribute("cart") Cart cart) {
        return userService.checkout(cart);
    }

    @PostMapping("/createOrder")
    public ModelAndView buy(@ModelAttribute("cart") Cart cart, @SessionAttribute("user") User user, @RequestParam("address") String address) throws DBConnectionException, UserAlreadyExistsException, NoOrderAddressException {
        return orderService.createUserOrder(user, cart, address);
    }

    @ModelAttribute("cart")
    public Cart setShoppingCart() {
        return new Cart();
    }
}
