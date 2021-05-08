package com.tyy.y2021.m04.d29.controller;

import com.tyy.y2021.m04.d29.dao.OrderRepository;
import com.tyy.y2021.m04.d29.dto.ItemDO;
import com.tyy.y2021.m04.d29.dto.OrderDO;
import com.tyy.y2021.m04.d29.dto.Result;
import com.tyy.y2021.m04.d29.service.InventoryService;
import com.tyy.y2021.m04.d29.service.ItemService;


import com.tyy.y2021.m04.d29.util.SessionUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author:tyy
 * @date:2021/4/29
 */

@RestController
@RequestMapping("/")
public class CheckoutController {

    @Resource
    private ItemService itemService;

    @Resource
    private InventoryService inventoryService;

    @Resource
    private OrderRepository orderRepository;

    @PostMapping
    public Result<OrderDO> checkout(Long itemId, Integer quantity) {
        //1)session管理
        Long userId = SessionUtils.getLoggedInUserId();
        if(userId<=0) {
            return Result.fail("Not Logged In");
        }
        //2)参数校验
        if(itemId<=0 || quantity<=0 || quantity>=1000){
            return Result.fail("Invalid Args");
        }

        //3)外部数据补全
        ItemDO item = itemService.getItem(itemId);
        if(item == null) {
            return Result.fail("Item Not Found");
        }

        //4)调用外部服务
        boolean withholdSuccess = inventoryService.withhold(itemId,quantity);
        if(!withholdSuccess){
            return Result.fail("InventorgetLoggedInUserIdy not enough");
        }
        //5)领域计算
        Long cost = item.getPriceInCents() *quantity;

        //6)领域对象操作
        OrderDO order = new OrderDO();
        order.setItemId(itemId);
        order.setBuyerId(userId);
        order.setSellerId(item.getSellerId());
        order.setCount(quantity);
        order.setTotalCost(cost);

        //7)数据持久化
        orderRepository.createOrder(order);

        //8)返回
        return Result.success(order);
    }

}
