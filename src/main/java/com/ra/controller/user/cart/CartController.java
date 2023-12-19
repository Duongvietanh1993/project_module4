package com.ra.controller.user.cart;

import com.ra.model.dto.user.response.UserResponesDTO;
import com.ra.model.entity.admin.Order;
import com.ra.model.entity.admin.Product;
import com.ra.model.entity.admin.User;
import com.ra.model.entity.user.CartItem;
import com.ra.model.service.order.OrderService;
import com.ra.model.service.product.ProductService;
import com.ra.model.service.cart.CartService;
import com.ra.model.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/")
public class CartController {
    @Autowired
    private CartService cartService;
    @Autowired
    private ProductService productService;
    @Autowired
    private UserService userService;
    @Autowired
    private HttpSession session;
    @Autowired
    private OrderService orderService;


    @RequestMapping("/cart")
    public String index(Model model) {
        List<CartItem> cartItems = cartService.getCartItems();
        double totalAmount = totalAmount();
        model.addAttribute("listCartItem", cartItems);
        model.addAttribute("totalAmount", totalAmount);
        return "user/cart/cart";
    }

    @PostMapping("/addCart")
    public String create(@RequestParam("quantity") Integer quantity, @RequestParam("productId") Integer productId) {
        CartItem cartItem = new CartItem();
        Product product = productService.findById(productId);
        cartItem.setQuantity(quantity);
        cartItem.setProduct(product);
        cartService.addToCart(cartItem);
        return "redirect:/cart";
    }

    @GetMapping("/checkout")
    public String checkout(Model model) {
        double totalAmount = totalAmount();
        if (session.getAttribute("user") == null) {
            return "redirect:/cart";
        }
        UserResponesDTO user = (UserResponesDTO) session.getAttribute("user");
        model.addAttribute("userCheckout", user);
        model.addAttribute("totalAmount", totalAmount);
        return "user/checkout/checkout";
    }

    @PostMapping("/checkout")
    public String handleCheckout(@ModelAttribute("user") UserResponesDTO userResponesDTO) {
        Order order = new Order();

        UserResponesDTO userDTO = (UserResponesDTO) session.getAttribute("user");
        User user = userService.findById(userDTO.getUserId());

        order.setUser(user);
        order.setTotal((float) totalAmount());
        order.setAddress(userResponesDTO.getUserAddress());
        order.setPhone(userResponesDTO.getUserPhone());

        orderService.order(order);
        return "user/checkout/orderThank";
    }

    public double totalAmount() {
        List<CartItem> cartItems = cartService.getCartItems();
        double total = 0;

        for (CartItem item : cartItems) {
            double price = item.getProduct().getProductPrice();
            int quantity = item.getQuantity();
            total += price * quantity;
        }

        return total;
    }

}
