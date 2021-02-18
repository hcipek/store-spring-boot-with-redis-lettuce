package com.ebebek.reactiveredis.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product implements Serializable {

    private Long id;
    private String name;
    private String size;
    private BigDecimal weight;

}
