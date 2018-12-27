package com.springingdream.userhistory.exceptions;

public class UserNotFoundException extends NotFoundException {
    public UserNotFoundException(Long uid) {
        super("No user with id " + uid);
    }
}
