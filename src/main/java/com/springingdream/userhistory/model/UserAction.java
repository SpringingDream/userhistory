package com.springingdream.userhistory.model;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class UserAction {
    private final Long uid;
    private final Action action;
}
