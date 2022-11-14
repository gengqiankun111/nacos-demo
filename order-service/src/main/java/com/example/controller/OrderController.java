package com.example.controller;

import com.example.entitys.TOrderEntity;
import com.example.pojo.Order;
import com.example.pojo.Product;
import com.example.repository.impl.TOrderRepositoryImpl;
import com.example.service.FeignOrderService;
import com.example.service.IOrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/order")
@Api(tags="订单服务")
@ApiOperation("订单服务 Operation")
public class OrderController {

    @Autowired
    private IOrderService orderService;
    @Autowired
    private FeignOrderService feignOrderService;
    @Autowired
    TOrderRepositoryImpl orderRepository;

    @RequestMapping("/listdb")
    @ApiOperation("查询listdb")
    public List<TOrderEntity> selectList() {
        return orderRepository.list();
    }

    @RequestMapping("/page")
    @ApiOperation("查询page")
    public Map<Long, List<TOrderEntity>> selectList(int pageNo, int limit) {
        return orderService.selectByPage(pageNo, limit);
    }

    @GetMapping("/session")
    @ApiOperation(value="session方法")
    public String session(HttpServletRequest request) {
        return "session: " + request.getSession().getId() + "  port: " + request.getServerPort();
    }
    /**
     * 根据主键查询订单
     *
     * @param id
     * @return
     */
    @RequestMapping("/{id}")
    @ApiOperation(value="byId方法")
    public Order selectOrderById(@PathVariable("id") Integer id) {
        Order o =  orderService.selectOrderById(id);
        return o;
    }
    @RequestMapping("/list")
    @ApiOperation(value="list方法")
    public List<Product> selectProductListByLoadBalancerAnnotation() {
        return feignOrderService.selectProductListByLoadBalancerAnnotation();
    }

    @RequestMapping("/a")
    @ApiOperation(value="测试方法")
    public String a() {
        return "a";
    }

    @RequestMapping("/")
    public String nu() {
        return "abbba";
    }


    @RequestMapping("/b")
    public String b() {
        return "b";
    }
    }
