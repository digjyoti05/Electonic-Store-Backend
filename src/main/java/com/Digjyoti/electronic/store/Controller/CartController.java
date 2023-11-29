package com.Digjyoti.electronic.store.Controller;

import com.Digjyoti.electronic.store.dtos.AddItemToCartRequest;
import com.Digjyoti.electronic.store.dtos.ApiResponseMessage;
import com.Digjyoti.electronic.store.dtos.CartDto;
import com.Digjyoti.electronic.store.services.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart")
public class CartController {
    @Autowired
    private CartService cartService;
    @PostMapping("/{userId}")
    public ResponseEntity<CartDto>addItemToCart(@PathVariable int userId, @RequestBody AddItemToCartRequest request){

        CartDto cartDto = cartService.addItemToCart(userId, request);
        return  new ResponseEntity<>(cartDto, HttpStatus.CREATED);
    }
    @DeleteMapping("/{userId}/item/{itemId}")
    public  ResponseEntity<ApiResponseMessage>removeItemFromCart(@PathVariable int userId,@PathVariable int itemId){
        cartService.removeItemFromCart(userId,itemId);
        ApiResponseMessage itemIsDeleted = ApiResponseMessage.builder().message("Item is Deleted").success(true).status(HttpStatus.OK).build();
        return  new ResponseEntity<>(itemIsDeleted,HttpStatus.OK);
    }
    @DeleteMapping("/{userId}")
    public  ResponseEntity<ApiResponseMessage> clearItemFromCart(@PathVariable int userId){
        cartService.clearCart(userId);
        ApiResponseMessage cartIsClear = ApiResponseMessage.builder().message("Cart Is clear").status(HttpStatus.OK).success(true).build();
        return  new ResponseEntity<>(cartIsClear,HttpStatus.OK);

    }
    @GetMapping("/{userId}")
    public ResponseEntity<CartDto>getCart(@PathVariable int userId){
        CartDto cartByUser = cartService.getCartByUser(userId);
        return new ResponseEntity<>(cartByUser,HttpStatus.OK);

    }
}
