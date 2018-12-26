package com.springingdream.userhistory.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@RequiredArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "purchases", indexes = {
        @Index(name = "user_index", columnList = "uid"),
        @Index(name = "product_index", columnList = "pid")
})
public class Purchase {
    @Id
    @GeneratedValue
    private Long id;

    @NonNull
    private Long uid;

    @NonNull
    private Long pid;

    @NonNull
    private Date timestamp;

    @NonNull
    private Integer quantity;
}
