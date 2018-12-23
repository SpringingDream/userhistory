package com.springingdream.userhistory.controlllers;

import com.springingdream.userhistory.exceptions.UserHistoryNotFoundException;
import com.springingdream.userhistory.model.UserAction;
import com.springingdream.userhistory.model.UserHistory;
import com.springingdream.userhistory.repositories.UserHistoryRepository;
import org.springframework.hateoas.Resource;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping(path = "/api/userhistory")
public class UserHistoryController {
    private final UserHistoryRepository repository;
    private final UserHistoryResourceAssembler assembler;

    public UserHistoryController(UserHistoryRepository repository, UserHistoryResourceAssembler assembler) {
        this.repository = repository;
        this.assembler = assembler;
    }

    @GetMapping(path = "{uid}", produces = {MediaType.APPLICATION_JSON_VALUE, "application/hal+json"})
    public Resource<UserHistory> get(@PathVariable Long uid) {
        Optional<UserHistory> history = repository.findById(uid);
        return pack(history.orElse(UserHistory.emptyHistoryFor(uid)));
    }

    @DeleteMapping(path = "{uid}")
    public void delete(@PathVariable Long uid) {
        repository.deleteById(uid);
    }

    @PostMapping
    public void insert(@RequestBody UserAction action) {
        if (action.getUid() == null)
            throw new UserHistoryNotFoundException(null);
        UserHistory history = repository.findById(action.getUid())
                .orElse(UserHistory.emptyHistoryFor(action.getUid()));
        history.add(action);
        repository.save(history);
    }

    private Resource<UserHistory> pack(UserHistory uh) {
        return assembler.toResource(uh);
    }

    private UserHistory findOrDie(Long uid) {
        if (uid == null)
            throw new UserHistoryNotFoundException(null);

        return repository.findById(uid)
                .orElseThrow(() -> new UserHistoryNotFoundException(uid));
    }
}
