package org.example.dockerdbexample.exceptions;

public class InsufficientBalance extends RuntimeException {
    public InsufficientBalance(){
        super("Недостаточный баланс");
    }
}
