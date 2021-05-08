package com.tyy.y2021.m04.d29.dto;

import lombok.Data;

/**
 * @author:tyy
 * @date:2021/4/29
 */

@Data
public class OrderDO {


    private Long itemId;

    private Long buyerId;

    private Long sellerId;

    private Integer count;

    private Long totalCost;
}
