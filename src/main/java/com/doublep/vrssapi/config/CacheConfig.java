package com.doublep.vrssapi.config;

import org.springframework.cache.Cache;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.jcache.JCacheCacheManager;
import org.springframework.cache.jcache.JCacheManagerFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

@Configuration
@EnableCaching
public class CacheConfig {

    @Bean
    public JCacheManagerFactoryBean jCacheManagerFactoryBean() throws Exception {
        JCacheManagerFactoryBean jCacheManagerFactoryBean = new JCacheManagerFactoryBean();
        jCacheManagerFactoryBean.setCacheManagerUri(new ClassPathResource("/ehcache.xml").getURI());
        return jCacheManagerFactoryBean;
    }

    @Bean
    public JCacheCacheManager jCacheCacheManager(JCacheManagerFactoryBean jCacheManagerFactoryBean) {
        JCacheCacheManager jCacheCacheManager = new JCacheCacheManager();
        if (jCacheManagerFactoryBean.getObject() != null) {
            jCacheCacheManager.setCacheManager(jCacheManagerFactoryBean.getObject());
        }
        jCacheCacheManager.setAllowNullValues(true);
        return jCacheCacheManager;
    }

    @Bean(name = "aisFileCollectHistCache")
    public Cache getAisFileCollectHistCache(JCacheCacheManager jCacheCacheManager) {
        return jCacheCacheManager.getCache("aisFileCollectHistCache");
    }
}
