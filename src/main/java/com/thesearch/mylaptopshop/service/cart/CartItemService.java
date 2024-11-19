package com.thesearch.mylaptopshop.service.cart;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;

import com.thesearch.mylaptopshop.exception.ResourceNotFoundException;
import com.thesearch.mylaptopshop.model.Cart;
import com.thesearch.mylaptopshop.model.CartItem;
import com.thesearch.mylaptopshop.model.Product;
import com.thesearch.mylaptopshop.repository.CartItemRepository;
import com.thesearch.mylaptopshop.repository.CartRepository;
import com.thesearch.mylaptopshop.service.product.IProductService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CartItemService implements ICartItemService{

    private final CartItemRepository cartItemRepository;
    private final IProductService productService;
    private final ICartService cartService;
    private final CartRepository cartRepository;

    @Override
    public void addItemToCart(Long cartId, Long productId,int quantity){
        Cart cart = cartService.getCart(cartId);
        Product product = productService.getProductById(productId);
        CartItem cartItem = cart.getItems()
            .stream()
            .filter(item -> item.getProduct().getId().equals(productId))
            .findFirst().orElse(new CartItem());

        if(cartItem.getId()==null){
            cartItem.setCart(cart);
            cartItem.setProduct(product);
            cartItem.setQuantity(quantity);
            cartItem.setUnitPrice(product.getPrice());
        }else{
            cartItem.setQuantity(quantity+cartItem.getQuantity());
        }
        cartItem.setTotalPrice();
        cart.addItem(cartItem);
        cartItemRepository.save(cartItem);
        cartRepository.save(cart);
    }

    @Override
    public void removeItemFromCart(Long cartId,Long productId){
        Cart cart = cartService.getCart(cartId);
        CartItem itemToRemove = getCartItem(cartId, productId);
        cart.removeItem(itemToRemove);
        cartRepository.save(cart);
    }

    @Override
    public void updateItemQuantity(Long cartId,Long productId,int quantity){
        Cart cart = cartService.getCart(cartId);
        cart.getItems()
            .stream()
            .filter(item -> item.getProduct().getId().equals(productId))
            .findFirst().ifPresent(item -> {
                item.setQuantity(quantity);
                item.setUnitPrice(item.getProduct().getPrice());
                item.setTotalPrice();
            });
        BigDecimal totalAmount = cart.getItems()
            .stream().map(CartItem :: getTotalPrice)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
        cart.setTotalAmount(totalAmount);
        cartRepository.save(cart);
    }

    @Override
    public CartItem getCartItem(Long cartId, Long productId){
        Cart cart = cartService.getCart(cartId);
        return cart.getItems()
            .stream()
            .filter(item -> item.getProduct().getId().equals(productId))
            .findFirst().orElseThrow(()-> new ResourceNotFoundException("Item not found!"));
        
    }
}
