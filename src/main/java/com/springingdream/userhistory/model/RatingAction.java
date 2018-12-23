package com.springingdream.userhistory.model;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.Data;

import java.util.Date;

@Data
@JsonTypeName("rating")
class RatingAction {
    private Long productId;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private Date timestamp;
    private Integer rating;
}
