package com.example.redis;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

@Deprecated
public class RedissonUtil {
    public static RedissonClient getClient() {
        //集群模式
        Config config = new Config();
        config.useClusterServers()
                .setScanInterval(2000) // cluster state scan interval in milliseconds
                .addNodeAddress("redis://127.0.0.1:7000", "redis://127.0.0.1:7001")
                .addNodeAddress("redis://127.0.0.1:7002");
        RedissonClient redisson = Redisson.create(config);
        return redisson;
    }
}
