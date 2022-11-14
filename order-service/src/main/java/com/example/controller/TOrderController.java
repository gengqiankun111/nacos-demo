package com.example.controller;

import com.example.entitys.TOrderEntity;
import com.example.repository.impl.TOrderRepositoryImpl;
import com.example.service.IOrderService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author <a href="https://www.fengwenyi.com?code">Erwin Feng</a>
 * @since 2022-05-04
 */
@RestController
@RequestMapping("/t-order-entity")
@ApiOperation("订单")
public class TOrderController {
    @Autowired
    TOrderRepositoryImpl orderRepository;
    @Autowired
    private IOrderService orderService;

    @RequestMapping("/list")
    @ApiOperation("查询list")
    public List<TOrderEntity> selectList() {
        return orderRepository.list();
    }

    @RequestMapping("/page")
    @ApiOperation("查询page")
    public Map<Long, List<TOrderEntity>> selectList(int pageNo, int limit) {
        return orderService.selectByPage(pageNo, limit);
    }
}
