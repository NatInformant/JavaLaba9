package org.example.controller;

import net.minidev.json.JSONObject;
import org.example.model.Product;
import org.example.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
public class ProductListController {
    @Autowired
    private ProductService productService;

    @GetMapping("/items")
    public Iterable<Product> itemList() { return productService.getProductList(); }

    @PostMapping("/items")
    public ResponseEntity<Product> createItem(@RequestBody JSONObject jsonItem) {
        return new ResponseEntity<>(productService.addProduct(jsonItem.getAsString("name")), HttpStatus.OK);
    }
    @DeleteMapping("/items/{id}")
    public HttpStatus deleteItem(@PathVariable int id) {
        productService.deleteProduct(id);
        return HttpStatus.OK;
    }
}
