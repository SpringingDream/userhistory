package com.springingdream.userhistory.controlllers;

import com.springingdream.userhistory.exceptions.IllegalRatingException;
import com.springingdream.userhistory.exceptions.HistoryNotFoundException;
import com.springingdream.userhistory.exceptions.ProductNotFoundException;
import com.springingdream.userhistory.exceptions.UserNotFoundException;
import com.springingdream.userhistory.model.Rating;
import com.springingdream.userhistory.repositories.PassportService;
import com.springingdream.userhistory.repositories.ProductsService;
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
    private final ProductsService products;
    private final PassportService passport;

    @Autowired
    public RatingController(RatingRepository repository, RatingResourceAssembler assembler, ProductsService products, PassportService passport) {
        this.repository = repository;
        this.assembler = assembler;
        this.products = products;
        this.passport = passport;
    }

    @GetMapping(path = "user/{uid}/ratings")
    public List<Rating> getForUser(@PathVariable Long uid) {
        if (!passport.checkUserExists(uid))
            throw new UserNotFoundException(uid);
        return repository.findAllByUid(uid);
    }

    @GetMapping(path = "user/{uid}/ratings/{pid}")
    public Resource<Rating> getForUserProduct(@PathVariable Long uid, @PathVariable Long pid) {
        if (!passport.checkUserExists(uid))
            throw new UserNotFoundException(uid);
        if (!products.checkProductExists(pid))
            throw new ProductNotFoundException(pid);
        return pack(repository.findByUidAndPid(uid, pid).orElseThrow(() -> new HistoryNotFoundException(uid)));
    }

    @GetMapping(path = "product/{pid}/ratings/")
    public List<Rating> getForProduct(@PathVariable Long pid) {
        if (!products.checkProductExists(pid))
            throw new ProductNotFoundException(pid);
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
        if (!passport.checkUserExists(uid))
            throw new UserNotFoundException(uid);
        if (!products.checkProductExists(pid))
            throw new ProductNotFoundException(pid);
        Rating r = repository.findByUidAndPid(uid, pid).orElse(new Rating(uid, pid, rating, new Date()));
        r.setRating(rating);
        r.setTimestamp(new Date());
        return pack(repository.save(r));
    }

    private Resource<Rating> pack(Rating uh) {
        return assembler.toResource(uh);
    }
}
