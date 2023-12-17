package com.ra.model.service.user;

import com.ra.model.entity.admin.Product;
import com.ra.model.entity.user.CartItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Service
public class CartService {
    @Autowired
    private HttpSession httpSession;
    List<CartItem> cartItems = new ArrayList<>();

    public List<CartItem> getCartItems() {
        //kiểm tra trong sesion có không thì lấy và gán vào cartItem không có thì không
        cartItems = httpSession.getAttribute("cart") != null ? (List<CartItem>) httpSession.getAttribute("cart") : new ArrayList<>();


        return cartItems;
    }

    public void addToCart(CartItem item) {
        CartItem oldItem = findCartItemById(item.getProduct());
        if (oldItem != null){
            oldItem.setQuantity(oldItem.getQuantity()+item.getQuantity());
        }else {
            //Đẩy vào cart
            cartItems.add(item);
        }
        //lưu vào session
        httpSession.setAttribute("cart", cartItems);
    }

    public void update(Integer quantity, Integer productId) {
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

        httpSession.setAttribute("cart", cartItems);
    }

    public CartItem findCartItemById(Product product) {
        for (CartItem item : cartItems) {
            if (item.getProduct().getProductId() == product.getProductId()) {
                return item;
            }
        }
        return null;
    }

}
