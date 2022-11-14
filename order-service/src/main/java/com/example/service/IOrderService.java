package com.example.service;

import com.example.entitys.TOrderEntity;
import com.example.pojo.Order;

import java.util.List;
import java.util.Map;

public interface IOrderService {
   /**
    * 根据主键查询订单
    *
    * @param id
    * @return
    */
   Order selectOrderById(Integer id);
   List<TOrderEntity> selectList();
   Map<Long, List<TOrderEntity>> selectByPage(int page, int limit);
}