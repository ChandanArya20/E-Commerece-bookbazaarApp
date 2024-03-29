package in.ineuron.restcontrollers;

import java.util.ArrayList;
import java.util.List;

import in.ineuron.services.CartService;
import in.ineuron.utils.AppUtils;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import in.ineuron.dto.BookResponse;
import in.ineuron.dto.CartResponse;
import in.ineuron.models.Cart;
import in.ineuron.models.User;

@RestController
@AllArgsConstructor
@RequestMapping("api/cart")
public class CartController {

	private CartService cartService;
	private AppUtils appUtils;
	
	@PostMapping("/add-to-cart")
	public ResponseEntity<String> addToCart(@RequestBody Cart cart){

		cartService.insertCartData(cart);
		
		return ResponseEntity.ok("Item inserted into cart successfully...");
	}
		 
	@GetMapping("/user/{user-id}/all-cart-data")
	public ResponseEntity<List<CartResponse>> getAllCartDataByUser(@PathVariable("user-id") Long userId){
		
		User user = new User();
		user.setId(userId);
		List<Cart> cartList = cartService.getAllCartDataByUser(user);
		List<CartResponse> cartResponseList=new ArrayList<>();
		
		cartList.forEach((cart)->{
			
			CartResponse cartResponse = new CartResponse();
			BeanUtils.copyProperties(cart, cartResponse);
			
			BookResponse bookResponse = new BookResponse();
			BeanUtils.copyProperties(cart.getBook(), bookResponse);
			bookResponse.setImageURL(appUtils.getBaseURL()+"/api/image/"+cart.getBook().getCoverImage().getId());
			
			cartResponse.setBook(bookResponse);
			cartResponseList.add(cartResponse);
		});
		
		return ResponseEntity.ok(cartResponseList);
	}
	
	@PatchMapping("/update-cart-quantity")
	public ResponseEntity<String> updateCart(@RequestBody Cart cart){

		cartService.updateCartItemQuantity(cart);
		return ResponseEntity.ok("Cart Item quantity got updated...");
	}
	
	@DeleteMapping("/delete")
	public ResponseEntity<String> deleteCart(@RequestBody Cart[] carts){

		cartService.deleteCartItems(carts);
		return ResponseEntity.ok("Cart Item quantity got updated...");		
	}
	
	
}





