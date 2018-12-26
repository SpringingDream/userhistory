package com.springingdream.userhistory.exceptions;

public class IllegalRatingException extends IllegalArgumentException {
    public IllegalRatingException(Integer rating) {
        super("Illegal rating value: " + rating);
    }
}
