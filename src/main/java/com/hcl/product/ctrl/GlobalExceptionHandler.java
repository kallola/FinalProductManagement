package com.hcl.product.ctrl;

import com.hcl.product.exception.ApplicationError;
import com.hcl.product.exception.ProductNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = ProductNotFoundException.class)
    public ResponseEntity<ApplicationError>  handleCustomerNotFoundException(ProductNotFoundException productNotFoundException, WebRequest webRequest){
        ApplicationError error = new ApplicationError();
        error.setError_code("NO_product_DATA_FOUND");
        error.setError_details(productNotFoundException.getMessage());
        return new ResponseEntity<>(error,HttpStatus.NOT_FOUND);
    }

   /* @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(value = Exception.class)
    public String handelException()
    {
        return "some internal server error is going on";
    }*/
}
