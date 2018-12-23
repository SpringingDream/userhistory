package com.springingdream.userhistory.exceptions;

public class UserHistoryNotFoundException extends RuntimeException {
    public UserHistoryNotFoundException(Long uid) {
        super("No user history for user " + uid);
    }
}
