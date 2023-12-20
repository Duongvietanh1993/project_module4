package com.ra.model.service.cart;

import com.ra.model.entity.admin.Product;
import com.ra.model.entity.user.CartItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Service
public class CartService {
    @Autowired
    private HttpSession httpSession;
    private List<CartItem> cartItems = new ArrayList<>();
    private int cartItemCount;

    public List<CartItem> getCartItems() {
        cartItems = httpSession.getAttribute("cart") != null ? (List<CartItem>) httpSession.getAttribute("cart") : new ArrayList<>();
        cartItemCount = cartItems.size();
        return cartItems;
    }

    public void addToCart(CartItem item) {
        CartItem oldItem = findCartItemById(item.getProduct());
        if (oldItem != null){
            oldItem.setQuantity(oldItem.getQuantity()+item.getQuantity());
        } else {
            // Đẩy vào cart
            cartItems.add(item);
            cartItemCount++;
        }

        // Lưu vào session
        httpSession.setAttribute("cart", cartItems);
        httpSession.setAttribute("cartItemCount", cartItemCount);
    }

    public void update(Integer productId, Integer quantity) {
        List<CartItem> cartItems = getCartItems();
        for (CartItem item : cartItems) {
            if (item.getProduct().getProductId() == productId) {
                item.setQuantity(quantity);
                break;
            }
        }
        httpSession.setAttribute("cart", cartItems);
    }

    public void delete(Integer id) {
        List<CartItem> cartItems = getCartItems();
        cartItems.removeIf(item -> item.getProduct().getProductId() == id);
        cartItemCount--;
        httpSession.setAttribute("cart", cartItems);
        httpSession.setAttribute("cartItemCount", cartItemCount);
    }

    public CartItem findCartItemById(Product product) {
        for (CartItem item : cartItems) {
            if (item.getProduct().getProductId() == product.getProductId()) {
                return item;
            }
        }
        return null;
    }

    public void removeCartItem() {
        httpSession.removeAttribute("cart");
        httpSession.removeAttribute("cartItemCount");
    }
}