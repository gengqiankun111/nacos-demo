package com.example.service.redis;

import jodd.util.StringUtil;
import org.redisson.api.RListMultimap;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class RedissonClientService {
    @Autowired
    private RedissonClient redissonClient;
    public String setCache(String userId, String password) {
        //这一步可以理解是给map取了个名字"cacheInfo" 我们到其他地方可以用这个名字调出这个map
        //这里举例用map存数据，redis支持的远不止map
        RMap<String, String> map = redissonClient.getMap("cacheInfo");
        //将数据存入缓存
        map.put(userId, password);
        //设置缓存的时间
        map.expire(1, TimeUnit.DAYS);
        return map.get(userId);
    }

    public String getCache(String userId) throws Exception {
        //这里就是通过上面方法给map取的名字"cacheInfo"调到对应的map
        RMap<String, String> map = redissonClient.getMap("cacheInfo");
        if (StringUtil.isEmpty(map.get(userId))) {
            throw new Exception("获取缓存失败，请重新登陆！！");
        }
        return map.get(userId);
    }

    public boolean setList(String listName, String userId, String password) throws Exception {
        //这里就是通过上面方法给map取的名字"cacheInfo"调到对应的map

        RListMultimap<String, String>  rmap = redissonClient.getListMultimap("list");
//        if (rmap == null || rmap.size() == 0) {
//            throw new Exception("获取缓存失败，请重新登陆！！");
//        }
        rmap.put(listName, userId);
        rmap.put(listName, userId+"_2");
        rmap.put(listName, userId+"_3");
        return true;
    }

}
