package com.springingdream.userhistory.controlllers;

import com.springingdream.userhistory.exceptions.IllegalRatingException;
import com.springingdream.userhistory.exceptions.UserHistoryNotFoundException;
import com.springingdream.userhistory.model.Rating;
import com.springingdream.userhistory.repositories.RatingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(path = "/api/history")
public class RatingController {
    private final RatingRepository repository;
    private final RatingResourceAssembler assembler;

    @Autowired
    public RatingController(RatingRepository repository, RatingResourceAssembler assembler) {
        this.repository = repository;
        this.assembler = assembler;
    }

    @GetMapping(path = "user/{uid}/ratings/")
    public List<Rating> getForUser(@PathVariable Long uid) {
        return repository.findAllByUid(uid);
    }

    @GetMapping(path = "user/{uid}/ratings/{pid}")
    public Resource<Rating> getForUserProduct(@PathVariable Long uid, @PathVariable Long pid) {
        return pack(repository.findByUidAndPid(uid, pid).orElseThrow(() -> new UserHistoryNotFoundException(uid)));
    }

    @GetMapping(path = "product/{pid}/ratings/")
    public List<Rating> getForProduct(@PathVariable Long pid) {
        return repository.findAllByPid(pid);
    }

    @DeleteMapping(path = "forget/{uid}/rating")
    public void delete(@PathVariable Long uid) {
        repository.deleteById(uid);
    }

    @PostMapping(path = "user/{uid}/rate/{pid}", produces = {MediaType.APPLICATION_JSON_VALUE, "application/hal+json"})
    public Resource<Rating> set(@PathVariable Long uid, @PathVariable Long pid, @RequestBody Integer rating) {
        if (rating > 5 || rating < 0)
            throw new IllegalRatingException(rating);
        Rating r = repository.findByUidAndPid(uid, pid).orElse(new Rating(uid, pid, rating, new Date()));
        r.setRating(rating);
        r.setTimestamp(new Date());
        return pack(repository.save(r));
    }

    private Resource<Rating> pack(Rating uh) {
        return assembler.toResource(uh);
    }
}
