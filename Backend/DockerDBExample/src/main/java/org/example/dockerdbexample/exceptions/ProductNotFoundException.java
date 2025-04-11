package org.example.dockerdbexample.exceptions;

public class ProductNotFoundException extends RuntimeException {
    public ProductNotFoundException(Long id){
        super("Product not found with ID " + id);
    }
}
