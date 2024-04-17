package org.example.service;

import org.example.model.Product;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
@Service
public class ProductService {
    private final ArrayList<Product> products = new ArrayList<Product>();
    private int idCounter = 0;

    public void addProduct(String name) {
        products.add(new Product(idCounter, name));
        idCounter++;
    }

    public void markProduct(int id) {
        products.get(id).setIsMarked();
    }

    public void deleteProduct(int id) {
        products.remove(products.stream()
                .filter(product -> product.getId() == id)
                .findFirst()
                .get());
    }
}
