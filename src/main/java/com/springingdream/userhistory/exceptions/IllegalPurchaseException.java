package com.springingdream.userhistory.exceptions;

public class IllegalPurchaseException extends IllegalArgumentException {
    public IllegalPurchaseException(Integer badQuantity) {
        super("Illegal quantity amount: " + badQuantity);
    }
}
