package com.hcl.product.ctrl;

import com.hcl.product.bo.Product;
import com.hcl.product.dao.ProductRepository;
import com.hcl.product.exception.ProductNotFoundException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;
@Slf4j
@RestController
@RequestMapping("/productmanagement")
@Api(value = "Product Service", description = "Ecommerce Portal")
public class ProductController {

    @Autowired
    private ProductRepository productRepository;

    //Saving the product into database
   @PostMapping("/products")
   @ApiOperation(value = "Create Product API")
   public ResponseEntity<Product> createProduct(@RequestBody Product product) throws Exception{
        new ProductFacadeRequest(product).setName("name changed");
        product = productRepository.save(new ProductFacadeRequest(product).get());
        return ResponseEntity.ok(new ProductFacadeResponse(product).get());
    }
    //Finding the single product
    @GetMapping("/products/{product_id}")
    @ApiOperation(value = "Retrive Product API")
    public ResponseEntity<Product> fetchProduct(@PathVariable String product_id){
            log.info("product id:{}",product_id);
       Optional<Product> productOptional=productRepository.findById(UUID.fromString(product_id));

        if(!productOptional.isPresent())
            throw new ProductNotFoundException("product is not available.please provide another product name");
        return ResponseEntity.ok(new ProductFacadeResponse(productOptional).getProductOptional());
    }

    //Converting the request actual business object according to the need.
    class ProductFacadeRequest {
        Product product;

        public ProductFacadeRequest() {
        }

        public ProductFacadeRequest(Product product) {
            this.product = product;
        }

        public final Product get() {
            return this.product;
        }


        public void setId(UUID id) {
            product.setId(id);}
        public void setName(String name) {
            product.setName(name);
        }

        public void setDesciption(String desciption) {
            product.setDesciption(desciption);
        }

        public void setType(String type) {
            product.setType(type);
        }

        public void setPrice(Double price) {
            product.setPrice(price);
        }
    }

    //Converting to customize Bo response
    class ProductFacadeResponse {
        Product product;
        Optional<Product> productOptional;

        public ProductFacadeResponse() {
        }

        public ProductFacadeResponse(Product product) {
            this.product = product;
        }

        public ProductFacadeResponse(Optional<Product> productOptional) {
            this.productOptional=productOptional;
        }

        public final Product get() {
            return this.product;
        }
        public final Product getProductOptional() {
            return this.productOptional.get();
        }



        public UUID getId() {
            return product.getId();
        }
        public String getName() {
            return product.getName();
        }

        public String getDesciption() {
            return product.getDesciption();
        }

        public String getType() {
            return product.getType();
        }

        public Double getPrice() {
            return product.getPrice();
        }
    }
}
