package com.hoozy.hoosshop.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.hoozy.hoosshop.dto.CartResponseDTO;
import com.hoozy.hoosshop.entity.Cart;
import com.hoozy.hoosshop.entity.Img;
import com.hoozy.hoosshop.entity.Product;
import com.hoozy.hoosshop.entity.Users;
import com.hoozy.hoosshop.jwt.SecurityUtil;
import com.hoozy.hoosshop.repository.CartRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CartService {

	private final CartRepository cartRepository;

	@Transactional
	public Cart save(Long id) {
		Users user = Users.builder()
				.id(Long.valueOf(SecurityUtil.getCurrentUserId()))
				.build();
		
		Img img = Img.builder()
					.id(id)
					.build();
		
		Product product = Product.builder()
								.id(id)
								.img(img)
								.build();

		Cart cart = Cart.builder()
						.count(1)
						.product(product)
						.user(user)
						.build();
				
		return cartRepository.save(cart);
	}

	public long count() {
		return cartRepository.count();
	}

	public Map<String, Object> getList(Long id) {
		List<Cart> cartList = cartRepository.findByUserId(id);
		List<CartResponseDTO> list = new ArrayList<>();
		int total = 0;
		for(Cart cart : cartList) {
			total += cart.getProduct().getPrice(); // 장바구니 총금액 담기
			list.add(CartResponseDTO.toCartResponseDTO(cart));
		}
		
		Map<String, Object> map = new HashMap<>();
		map.put("list", list);
		map.put("totalPrice", total);
		return map;
	}

	public void deleteCart(Long id) {
		cartRepository.deleteById(id);
	}

	public boolean isExist(Long id) {
		return cartRepository.existsByProductId(id);
	}

	public long getCount() {
		return cartRepository.count();
	}

	public void deleteAll() {
		cartRepository.deleteAll();
	}

}
