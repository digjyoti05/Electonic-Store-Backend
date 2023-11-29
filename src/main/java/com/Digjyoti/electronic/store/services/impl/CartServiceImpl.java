package com.Digjyoti.electronic.store.services.impl;

import com.Digjyoti.electronic.store.dtos.AddItemToCartRequest;
import com.Digjyoti.electronic.store.dtos.CartDto;
import com.Digjyoti.electronic.store.entities.Cart;
import com.Digjyoti.electronic.store.entities.CartItem;
import com.Digjyoti.electronic.store.entities.Product;
import com.Digjyoti.electronic.store.entities.User;
import com.Digjyoti.electronic.store.exceptions.BadApiRequestException;
import com.Digjyoti.electronic.store.exceptions.ResourceNotFoundExcepation;
import com.Digjyoti.electronic.store.repositories.CartItemRepository;
import com.Digjyoti.electronic.store.repositories.CartRepository;
import com.Digjyoti.electronic.store.repositories.ProductRepository;
import com.Digjyoti.electronic.store.repositories.UserRepository;
import com.Digjyoti.electronic.store.services.CartService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
public class CartServiceImpl implements CartService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private CartItemRepository cartItemRepository;
    @Autowired
    private ModelMapper mapper;


    @Override
    public CartDto addItemToCart(int  userId, AddItemToCartRequest request) {
        int quantity = request.getQuantity();
        int productId = request.getProductId();

        if(quantity<=0){
            throw new BadApiRequestException("Request Quantity  is Not  Valid");


        }
//        fatch the product
        Product product = productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundExcepation("Product Is not found"));
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundExcepation("User Is not Found"));
        Cart cart =null;
        try {

                cart=cartRepository.findByUser(user).get();

        }catch (NoSuchElementException e){
            cart= new Cart();
            cart.setId(UUID.randomUUID().toString());
            cart.setCreatedAt(new Date());

        }
//        perform cart operation
//        if product is already Presant
        AtomicReference<Boolean>updated=new AtomicReference<>(false);
        List<CartItem> items = cart.getItems();
       items = items.stream().map(item -> {

            if (item.getProduct().getProductId()==(productId)) {
                //item already present in cart
                item.setQuantity(quantity);
                item.setTotalPrice(quantity * product.getDiscountedPrice());
                updated.set(true);
            }
            return item;
        }).collect(Collectors.toList());
//        cart.setItems(items);




//        CreateItems
        if(!updated.get()) {
            CartItem cartItem = CartItem.builder()
                    .quantity(quantity)
                    .totalPrice(quantity * product.getDiscountedPrice())
                    .cart(cart)
                    .product(product)
                    .build();
            cart.getItems().add(cartItem);
        }
       cart.setUser(user);
        Cart updatedCart = cartRepository.save(cart);
        return  mapper.map(updatedCart,CartDto.class);


    }

    @Override
    public void removeItemFromCart(int userId, int cartItem) {
//        caondition

        CartItem cartItemNotFound = cartItemRepository.findById(cartItem).orElseThrow(() -> new ResourceNotFoundExcepation("cart Item Not Found"));
        cartItemRepository.delete(cartItemNotFound);

    }

    @Override
    public void clearCart(int userId) {
        User userIsNotAvailable = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundExcepation("User is not Available"));
        Cart cartIsNotFound = cartRepository.findByUser(userIsNotAvailable).orElseThrow(() -> new ResourceNotFoundExcepation("Cart is not Found"));
        cartIsNotFound.getItems().clear();
        cartRepository.save(cartIsNotFound);



    }

    @Override
    public CartDto getCartByUser(int userId) {
        User userIsNotAvailable = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundExcepation("User is not Available"));
        Cart cartIsNotFound = cartRepository.findByUser(userIsNotAvailable).orElseThrow(() -> new ResourceNotFoundExcepation("Cart is not Found"));


        return mapper.map(cartIsNotFound,CartDto.class);
    }
}
