package com.springingdream.userhistory.controlllers;

import com.springingdream.userhistory.model.Rating;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;

@Component
public class RatingResourceAssembler implements ResourceAssembler<Rating, Resource<Rating>> {
    @Override
    public Resource<Rating> toResource(Rating entity) {
        return new Resource<>(entity);
    }
}
