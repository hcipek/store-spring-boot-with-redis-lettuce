package com.ebebek.reactiveredis.model.product;

import com.ebebek.reactiveredis.model.cart.Cart;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductRequest {

    private String cartId;
    private Long productId;

}
