package com.ebebek.reactiveredis.model.store;

import com.ebebek.reactiveredis.model.BaseModel;
import com.ebebek.reactiveredis.model.stock.Stock;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Store extends BaseModel {
    private Long id;
    private String name;
    private String location;
    private Stock stock;
}