package com.example.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * RLock lock = redisson.getLock("anyLock");
 *
 * lock.lock();
 *
 * lock.unlock();
 */
@Configuration
public class RedissonConfig {

        @Value("${spring.redis.host}")

        private String host;

        @Value("${spring.redis.port}")

        private String port;

        @Value("${spring.redis.password}")

        private String password;

        @Bean
        public RedissonClient getSingleClient(){

            Config config = new Config();

            config.useSingleServer().setAddress("redis://" + host + ":" + port).setPassword(password);

        RedissonClient redisson = Redisson.create(config);
        return redisson;
    }

    public  RedissonClient getClusterClient() {
        //集群模式
        Config config = new Config();
        config.useClusterServers()
                .setScanInterval(2000) // cluster state scan interval in milliseconds
                .addNodeAddress("redis://127.0.0.1:7000", "redis://127.0.0.1:7001")
                .addNodeAddress("redis://127.0.0.1:7002");
        RedissonClient redisson = Redisson.create(config);
        return redisson;
    }

    public  RedissonClient getSentinelClient() {
        Config config = new Config();
        config.useSentinelServers()
                .setMasterName("mymaster")
                .addSentinelAddress("redis://127.0.0.1:26389", "redis://127.0.0.1:26379")
                .addSentinelAddress("redis://127.0.0.1:26319");
        RedissonClient redisson = Redisson.create(config);
        return redisson;
    }

    public  RedissonClient getMainPrivateClient() {
        Config config = new Config();
        config.useMasterSlaveServers()
                .setMasterAddress("redis://127.0.0.1:6379")
                .addSlaveAddress("redis://127.0.0.1:6389", "redis://127.0.0.1:6332", "redis://127.0.0.1:6419")
                .addSlaveAddress("redis://127.0.0.1:6399");
        RedissonClient redisson = Redisson.create(config);
        return redisson;
    }

}
