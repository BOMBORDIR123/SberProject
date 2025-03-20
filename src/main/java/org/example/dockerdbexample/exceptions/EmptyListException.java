package org.example.dockerdbexample.exceptions;


public class EmptyListException extends RuntimeException {

    public EmptyListException(){
        super("This list is empty :(");
    }
}
