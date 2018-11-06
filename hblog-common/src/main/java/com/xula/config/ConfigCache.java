package com.xula.config;


import com.xula.base.cache.MCacheKit;
import com.xula.base.helper.SpringFactory;
import net.rubyeye.xmemcached.MemcachedClient;
import net.rubyeye.xmemcached.XMemcachedClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

/**
 * memcached 配置启动
 * @author xla
 */
@Configuration
public class ConfigCache {

    @Value("${memcached.ip}")
    private String ip;

    @Value("${memcached.port}")
    private int port;


    @Bean
    public MemcachedClient create() throws IOException {
        XMemcachedClient xMemcachedClient = new XMemcachedClient();
        xMemcachedClient.addServer(ip,port);
        return xMemcachedClient;
    }


    @Bean
    public MCacheKit mCacheKit() throws IOException {
        return new MCacheKit(create());
    }


    @Bean
    public SpringFactory springFactory() {
        return new SpringFactory();
    }
}
