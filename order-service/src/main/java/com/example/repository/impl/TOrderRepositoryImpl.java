package com.example.repository.impl;

import com.example.entitys.TOrderEntity;
import com.example.dao.ITOrderDao;
import com.example.repository.MPTOrderRepository;
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
public class TOrderRepositoryImpl extends ServiceImpl<ITOrderDao, TOrderEntity> implements MPTOrderRepository {

}
