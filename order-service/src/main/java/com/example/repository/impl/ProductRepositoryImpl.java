package com.example.repository.impl;

import com.example.entitys.ProductEntity;
import com.example.dao.IProductDao;
import com.example.repository.MPProductRepository;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author <a href="https://www.fengwenyi.com?code">Erwin Feng</a>
 * @since 2022-05-04
 */
@Service
public class ProductRepositoryImpl extends ServiceImpl<IProductDao, ProductEntity> implements MPProductRepository {

}
