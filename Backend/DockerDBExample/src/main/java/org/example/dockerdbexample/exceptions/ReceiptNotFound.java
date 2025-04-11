package org.example.dockerdbexample.exceptions;

public class ReceiptNotFound extends RuntimeException{
    public ReceiptNotFound(Long id){
        super("Receipt not found with ID " + id);
    }
}
