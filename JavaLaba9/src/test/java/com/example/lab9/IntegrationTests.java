package com.example.lab9;


import com.example.lab9.controller.ProductListController;
import com.example.lab9.model.Product;
import com.example.lab9.repository.ProductsRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;

import org.springframework.test.web.servlet.MockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class IntegrationTests {

    @Autowired
    private ProductListController productListController;

    @MockBean
    private ProductsRepository productsRepository;

    @Autowired
    private MockMvc mockMvc;
    private Product product1= new Product("Арбуз");
    private Product product2= new Product("Яблоко");
    private Product product3= new Product("Апельсин");

    @Test
    public void contextLoads() {}

    @Test
    public void controllerTest() throws Exception{
        Assert.assertNotNull(productListController);
    }

    @Test
    public void productListTest() throws Exception {
        List<Product> products = new ArrayList<>(Arrays.asList(product1, product2, product3));

        Sort sort = Sort.by(Sort.Direction.ASC, "id");
        Mockito.when(productsRepository.findAll(sort)).thenReturn(products);

        mockMvc.perform(get("/items").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[1].name", is("Яблоко")));
    }
    @Test
    public void deleteProductTest() throws Exception {
        product1.setId(4l);
        productsRepository.save(product1);


        mockMvc.perform(delete("/items/"+product1.getId().toString()).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        Optional<Product> deletedProduct = productsRepository.findById(product1.getId());
        Assert.assertFalse(deletedProduct.isPresent());
    }
}
