package com.springingdream.userhistory.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.Data;

import java.util.Date;

@Data
@JsonTypeName("purchase")
class PurchaseAction implements Action {
    private Long productId;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private Date timestamp;
}
