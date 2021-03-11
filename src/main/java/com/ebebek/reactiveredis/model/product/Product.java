package com.ebebek.reactiveredis.model.product;

import com.ebebek.reactiveredis.model.base.BaseModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product extends BaseModel {

    private Long id;
    private String name;
    private String size;
    private BigDecimal weight;
}
