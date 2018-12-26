package com.springingdream.userhistory.repositories;

import com.springingdream.userhistory.model.Purchase;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PurchasesRepository extends CrudRepository<Purchase, Long> {
    List<Purchase> findAllByUid(Long uid);
    List<Purchase> findAllByPid(Long pid);
    List<Purchase> findAllByUidAndPid(Long uid, Long pid);
}
