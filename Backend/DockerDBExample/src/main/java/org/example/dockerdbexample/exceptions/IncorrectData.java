package org.example.dockerdbexample.exceptions;


public class IncorrectData  extends RuntimeException {
    public IncorrectData(){
        super("Incorrect data");
    }
}
