package com.ebebek.reactiveredis.model.cart;

import com.ebebek.reactiveredis.model.BaseModel;
import com.ebebek.reactiveredis.model.product.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartItem extends BaseModel {

    private Product product;
    private int count;

    public void increment() {
        count++;
    }

    public void decrement() {
        if(count > 0)
            count--;
    }
}
