package com.springingdream.userhistory.exceptions;

public class HistoryNotFoundException extends NotFoundException {
    public HistoryNotFoundException(Long uid) {
        super("No history for " + uid);
    }
}
