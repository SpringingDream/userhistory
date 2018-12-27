package com.springingdream.userhistory.exceptions;

public class ProductNotFoundException extends NotFoundException {
    public ProductNotFoundException(Long pid) {
        super("No product with id " + pid);
    }
}
