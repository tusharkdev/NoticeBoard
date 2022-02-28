package com.board.announcement.exceptions;

public class UserNotFoundException extends RuntimeException{

    public UserNotFoundException(String errorMessage) {
        super(errorMessage);
    }
}
