package com.disalvo.peter;

public class TooManyRollsException extends RuntimeException {
    public TooManyRollsException() {
        super("Too many rolls for frame");
    }
}
