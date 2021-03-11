package com.ebebek.reactiveredis.controller;

import com.ebebek.reactiveredis.model.product.ProductResponse;
import com.ebebek.reactiveredis.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/redis/products")
public class ProductController {

    @Autowired
    public ProductService productService;

    @PostMapping("/showAllProducts")
    public ProductResponse showAllProducts() {
        return productService.getAll();
    }
}
