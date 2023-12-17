package com.ra.controller.user.cart;

import com.ra.model.dto.user.response.UserResponesDTO;
import com.ra.model.entity.admin.Product;
import com.ra.model.entity.admin.User;
import com.ra.model.entity.user.CartItem;
import com.ra.model.service.product.ProductService;
import com.ra.model.service.user.CartService;
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

    @RequestMapping("/cart")
    public String index(Model model) {
        List<CartItem> cartItems = cartService.getCartItems();
        model.addAttribute("listCartItem", cartItems);
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




}
