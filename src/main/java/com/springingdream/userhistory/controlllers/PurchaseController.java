package com.springingdream.userhistory.controlllers;

import com.springingdream.userhistory.exceptions.IllegalPurchaseException;
import com.springingdream.userhistory.exceptions.HistoryNotFoundException;
import com.springingdream.userhistory.exceptions.ProductNotFoundException;
import com.springingdream.userhistory.exceptions.UserNotFoundException;
import com.springingdream.userhistory.model.Purchase;
import com.springingdream.userhistory.repositories.PassportService;
import com.springingdream.userhistory.repositories.ProductsService;
import com.springingdream.userhistory.repositories.PurchasesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(path = "/api/history")
public class PurchaseController {
    private final PurchasesRepository repository;
    private final PurchaseResourceAssembler assembler;
    private final ProductsService products;
    private final PassportService passport;

    @Autowired
    public PurchaseController(PurchasesRepository repository, PurchaseResourceAssembler assembler, ProductsService products, PassportService passport) {
        this.repository = repository;
        this.assembler = assembler;
        this.products = products;
        this.passport = passport;
    }

    @GetMapping(path = "user/{uid}/purchases/")
    public List<Purchase> getForUser(@PathVariable Long uid) {
        if (!passport.checkUserExists(uid))
            throw new UserNotFoundException(uid);
        return repository.findAllByUid(uid);
    }

    @GetMapping(path = "product/{pid}/purchases/")
    public List<Purchase> getForProduct(@PathVariable Long pid) {
        if (!products.checkProductExists(pid))
            throw new ProductNotFoundException(pid);
        return repository.findAllByPid(pid);
    }

    @GetMapping(path = "user/{uid}/purchases/{pid}")
    public List<Purchase> getForProductUser(@PathVariable Long pid, @PathVariable Long uid) {
        if (!passport.checkUserExists(uid))
            throw new UserNotFoundException(uid);
        if (!products.checkProductExists(pid))
            throw new ProductNotFoundException(pid);
        return repository.findAllByUidAndPid(uid, pid);
    }

    @DeleteMapping(path = "forget/{uid}/purchases")
    public void delete(@PathVariable Long uid) {
        repository.deleteById(uid);
    }

    @PostMapping(path = "user/{uid}/buy/{pid}", produces = {MediaType.APPLICATION_JSON_VALUE, "application/hal+json"})
    public Resource<Purchase> buy(@PathVariable Long uid, @PathVariable Long pid, @RequestBody Integer quantity) {
        if (quantity < 1)
            throw new IllegalPurchaseException(quantity);
        if (!passport.checkUserExists(uid))
            throw new UserNotFoundException(uid);
        if (!products.checkProductExists(pid))
            throw new ProductNotFoundException(pid);
        Purchase purchase = new Purchase(uid, pid, new Date(), quantity);
        return pack(repository.save(purchase));
    }

    private Resource<Purchase> pack(Purchase uh) {
        return assembler.toResource(uh);
    }
}
