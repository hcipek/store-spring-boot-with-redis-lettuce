package com.ebebek.reactiveredis.model.stock;

import com.ebebek.reactiveredis.model.product.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Stock implements Serializable {
    private Long id;
    private String name;
    private List<Product> productList;
}
