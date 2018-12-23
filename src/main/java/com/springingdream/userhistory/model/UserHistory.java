package com.springingdream.userhistory.model;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Data
@RequiredArgsConstructor
@Document
public class UserHistory {
    @Id
    private final long uid;

    private final List<UserAction> actions;

    public void add(UserAction action) {
        actions.add(action);
    }

    public static UserHistory emptyHistoryFor(Long uid) {
        return new UserHistory(uid, new ArrayList<>());
    }
}
