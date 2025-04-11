package org.example.dockerdbexample.exceptions;

public class InsufficientQuantityException extends RuntimeException {
    public InsufficientQuantityException(String s){
        super(s);
    }
}
