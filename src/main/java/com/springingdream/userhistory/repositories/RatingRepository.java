package com.springingdream.userhistory.repositories;

import com.springingdream.userhistory.model.Rating;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface RatingRepository extends CrudRepository<Rating, Long> {
    List<Rating> findAllByUid(Long uid);
    List<Rating> findAllByPid(Long pid);
    Optional<Rating> findByUidAndPid(Long uid, Long pid);
}
