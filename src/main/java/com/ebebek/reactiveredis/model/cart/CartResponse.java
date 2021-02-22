package com.ebebek.reactiveredis.model.cart;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartResponse {

    private List<Cart> results;
    private String resultMessage;
    private int resultCode;
}
