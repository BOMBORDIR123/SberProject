package org.example.dockerdbexample.exceptions;

public class DifferentPassword extends RuntimeException{
    public DifferentPassword(){
        super("Пароли не совпадают");
    }
}
