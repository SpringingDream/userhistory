package com.springingdream.userhistory.controlllers;

import com.springingdream.userhistory.model.UserHistory;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;

@Component
public class UserHistoryResourceAssembler implements ResourceAssembler<UserHistory, Resource<UserHistory>> {
    @Override
    public Resource<UserHistory> toResource(UserHistory entity) {
        return new Resource<>(entity);
    }
}
