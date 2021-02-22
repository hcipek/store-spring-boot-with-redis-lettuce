package com.ebebek.reactiveredis.model.cart;

import com.ebebek.reactiveredis.model.cart.CartItem;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartRequest {

    private Cart cart;

}
