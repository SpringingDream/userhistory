package com.springingdream.userhistory.repositories;

import com.springingdream.userhistory.model.UserHistory;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserHistoryRepository extends MongoRepository<UserHistory, Long> {
}
