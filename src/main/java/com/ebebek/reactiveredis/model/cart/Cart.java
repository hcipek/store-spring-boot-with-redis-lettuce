package com.ebebek.reactiveredis.model.cart;

import com.ebebek.reactiveredis.model.base.BaseModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Cart extends BaseModel {

    private String id;
    private List<CartItem> cartItemList;

    public void refresh() {
        cartItemList.removeAll(cartItemList.stream().filter(e -> e.getCount() == 0).collect(Collectors.toList()));
    }

    public void addCartItem(CartItem item) {
        if(cartItemList == null) {
            cartItemList = new ArrayList<>();
        }
        cartItemList.add(item);
    }
}
