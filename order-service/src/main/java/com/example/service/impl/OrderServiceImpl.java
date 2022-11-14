package com.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import com.example.entitys.TOrderEntity;
import com.example.pojo.Order;
import com.example.repository.impl.TOrderRepositoryImpl;
import com.example.service.FeignOrderService;
import com.example.service.IOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class OrderServiceImpl implements IOrderService {
    @Autowired
    private FeignOrderService feignOrderService;

    @Autowired
    private TOrderRepositoryImpl orderDao;

    /**
     * 根据主键查询订单
     *
     * @param id
     * @return
     */
    @Override
    public Order selectOrderById(Integer id) {
        log.info("订单服务查询订单信息...");
        return new Order(id, "order-001", "中国", 22788D,
                feignOrderService.selectProductListByLoadBalancerAnnotation());
    }

    public List<TOrderEntity> selectList() {
        //queryWrapper.eq("age", 20);
        //QueryWrapper queryWrapper = new QueryWrapper<>();

        List list = orderDao.list();
        return list;
    }

    public Map<Long, List<TOrderEntity>> selectByPage(int current, int size) {
        System.out.println("page + \", \" + limit = " + current + ", " + size);
        LambdaQueryWrapper queryWrapper = new LambdaQueryWrapper<>();
        //queryWrapper.gt("total_price", 20);
        // 分页对象 如果传false的话，代表不执行查询总记录数的那条sql语句
        Page queryPage = new Page<>(current, size, true);
        // 分页查询
        IPage iPage = orderDao.page(queryPage, queryWrapper);
        // 数据总数
        Long total = iPage.getTotal();
        System.out.println("total = " + total);
        // 集合数据
        List list = iPage.getRecords();
        Map<Long, List<TOrderEntity>> map = new HashMap<>(16);
        map.put(total, list);
        return map;
    }
}
