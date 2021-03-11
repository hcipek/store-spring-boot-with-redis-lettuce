package com.ebebek.reactiveredis.model.product;

import com.ebebek.reactiveredis.model.cart.Cart;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponse {

    private List<Product> results;
    private String resultMessage;
    private int resultCode;
}
