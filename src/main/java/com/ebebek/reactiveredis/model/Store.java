package com.ebebek.reactiveredis.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Store implements Serializable {
    private Long id;
    private String name;
    private String location;
    private Stock stock;
}