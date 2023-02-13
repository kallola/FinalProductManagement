package com.hcl.product.dao;

import com.hcl.product.bo.Product;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
@RunWith(SpringRunner.class)
@DataJpaTest
public class ProductRepositoryTest {
   /* @Autowired
    private TestEntityManager entityManager;*/

    @Autowired
    private ProductRepository repository;

    /*@BeforeEach
    public void setup(){
        Product testProduct=Product.builder()
                .name("pen")
                .desciption("study related products want to store")
                .type("study")
                .price(699.54)
                .build();
       
    }*/

    @Test
    public void productRepository_saveSingleProduct_success() {
        //entityManager.pe
        Product testProduct=Product.builder()
                .name("pen")
                .desciption("study related products want to store")
                .type("study")
                .price(699.54)
                .build();
        Product product=repository.save(testProduct);
        assertNotNull(product);
        assertEquals(product.getId(),testProduct.getId());
    }

    @Test(expected=NullPointerException.class)
    public void productRepository_saveSingleProduct_fail() {
        Product testProduct=Product.builder()
                .name(null)
                .desciption(null)
                .type(null)
                .price(0)
                .build();
        Product product=repository.save(testProduct);
    }
}
