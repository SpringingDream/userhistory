package com.springingdream.userhistory.controlllers;

import com.springingdream.userhistory.model.Purchase;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;

@Component
public class PurchaseResourceAssembler implements ResourceAssembler<Purchase, Resource<Purchase>> {
    @Override
    public Resource<Purchase> toResource(Purchase entity) {
        return new Resource<>(entity);
    }
}
