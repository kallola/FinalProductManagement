package com.hcl.product.ctrl;

import com.hcl.product.bo.PriceRangeCriteria;
import com.hcl.product.bo.Product;
import com.hcl.product.bo.ProductSearchCriteria;
import com.hcl.product.dao.ProductRepository;
import com.hcl.product.exception.ProductNotFoundException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
@Slf4j
@RestController
@RequestMapping("/productmanagement")
@Api(value = "Product Service", description = "Ecommerce Portal")
//@Profile(value = { "local", "test", "prod" })
@Profile("prod")
public class ProductController {

    @Autowired
    private ProductRepository productRepository;

    //Saving the product into database
   @PostMapping("/products")
   @ApiOperation(value = "Create Product API")
   public ResponseEntity<Product> createProduct(@RequestBody  ProductFacadeRequest productFacadeRequest) throws Exception{

        Product productResponse = productRepository.save(productFacadeRequest.getProductDetails());
       return new ResponseEntity(new ProductFacadeResponse(productResponse), HttpStatus.CREATED);
    }
    //Finding the single product
    @GetMapping("/products/{product_id}")
    @ApiOperation(value = "Retrive Product API")
    public ResponseEntity<Product> fetchProduct(@PathVariable String product_id){
            log.info("product id:{}",product_id);
       Optional<Product> response=productRepository.findById(UUID.fromString(product_id));

        if(!response.isPresent())
            throw new ProductNotFoundException("product is not available.please provide another product name");
        return new ResponseEntity(new ProductFacadeResponse(response.get()), HttpStatus.OK);
    }

    //Searching The product List
    @PostMapping("/products/list")
    @ApiOperation(value = "List Of Product API")
    public ResponseEntity<Product> searchProduct(@RequestBody ProductFacadeSearchRequest productFacadeSearchRequest){
        //log.info("ProductSearch elements are:{}{}{}{}", productFacadeSearchRequest.price_range.getMax());
       if(productFacadeSearchRequest.price_range!=null){
        if(new Double(productFacadeSearchRequest.price_range.getMax())==0.0) {
           // log.info("ProductSearch minimum value is:{}{}{}{}", productFacadeSearchRequest.price_range.getMin());
            productFacadeSearchRequest.price_range.setMax(productFacadeSearchRequest.price_range.getMin());
            productFacadeSearchRequest.price_range.setMin(0.0);
            //log.info("ProductSearch maximum value is:{}{}{}{}", productFacadeSearchRequest.price_range.getMax());
        }
       }
      List<Product> productListResponse = productRepository.searchByProductLike(productFacadeSearchRequest.getProductSearchCriteria().getPartname(),
              productFacadeSearchRequest.getProductSearchCriteria().getType(),
                productFacadeSearchRequest.getProductSearchCriteria().getMin(), productFacadeSearchRequest.getProductSearchCriteria().getMax());

       return new ResponseEntity(ProductFacadeResponse.getProductListFacadeResponse(productListResponse), HttpStatus.OK);
    }


    static class  ProductFacadeSearchRequest {

        String part_name;
        String type;
        PriceRangeCriteria price_range;

        public void setPart_name(String part_name) {
                this.part_name = part_name;
        }

        public void setType(String type) {
                this.type = type;
        }
        public void setPrice_range(PriceRangeCriteria price_range) {
                this.price_range = price_range;
        }

        public ProductSearchCriteria getProductSearchCriteria() {
            return ProductSearchCriteria.builder()
                    .partname(this.part_name)
                    .type(this.type)
                    .min(this.price_range!=null?this.price_range.getMin():0.0)
                    .max(this.price_range!=null?this.price_range.getMax():1200)
                    .build();
        }
    }


    //Converting the request actual business object according to the need.
   static class ProductFacadeRequest {
        private String name;
        private String desciption;
        private String type;
        private double price;

        public void setName(String name) {
            this.name = name;
        }

        public void setDesciption(String desciption) {
            this.desciption = desciption;
        }

        public void setType(String type) {
            this.type = type;
        }

        public void setPrice(double price) {
            this.price = price;
        }
        public Product getProductDetails() {
            return Product.builder()
                    .name(this.name)
                    .desciption(this.desciption)
                    .price(this.price)
                    .type(this.type)
                    .build();
        }

    }

    //Converting to customize Bo response
    static class ProductFacadeResponse {
        private String id;
        private String name;
        private String desciption;
        private String type;
        private double price;


        public ProductFacadeResponse(Product product) {
            UUID uuid = product.getId();
            this.id = uuid.toString();
            this.name = product.getName();
            this.desciption = product.getDesciption();
            this.type = product.getType();
            this.price = product.getPrice();
        }

        static public List<Product> getProductListFacadeResponse(List<Product> productList) {
            List<Product> newProductList=new ArrayList<>();
            for(Product product:productList){
               newProductList.add(product);
            }
            return newProductList;
            }

        public String getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public String getDesciption() {
            return desciption;
        }

        public String getType() {
            return type;
        }

        public double getPrice() {
            return price;
        }


    }
}
