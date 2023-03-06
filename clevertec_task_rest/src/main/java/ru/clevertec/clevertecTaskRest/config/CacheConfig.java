package ru.clevertec.clevertecTaskRest.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.clevertec.clevertecTaskRest.cache.CacheFactory;
import ru.clevertec.clevertecTaskRest.cache.api.ICache;
import ru.clevertec.clevertecTaskRest.cache.enums.ECacheType;
import ru.clevertec.clevertecTaskRest.dao.entity.Product;
import ru.clevertec.clevertecTaskRest.dao.entity.SaleCard;

@Configuration
public class CacheConfig {
    @Value("${app.cache.size}")
    private Integer size;
    @Value("#{'${app.cache.type}'.toUpperCase()}")
    private ECacheType cacheType;

    private final CacheFactory cacheFactory;

    public CacheConfig(CacheFactory cacheFactory) {
        this.cacheFactory = cacheFactory;
    }

    @Bean
    public ICache<Long, Product> productCache(){
        return cacheFactory.getCache(size, cacheType);
    }

    @Bean
    public ICache<Long, SaleCard> saleCardCache(){
        return cacheFactory.getCache(size, cacheType);
    }
}
