package ru.clevertec.clevertecTaskRest.aspects.cache;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;
import ru.clevertec.clevertecTaskRest.cache.api.ICache;
import ru.clevertec.clevertecTaskRest.dao.entity.Product;

@Aspect
@Component
public class ProductServiceCaching {
    private final ICache<Long, Product> productCache;

    public ProductServiceCaching(ICache<Long, Product> productCache) {
        this.productCache = productCache;
    }

    @Around(value = "cacheableCall(id)", argNames = "pjp,id")
    public Object getFromCache(ProceedingJoinPoint pjp, Long id) throws Throwable {
        return productCache.get(id).isPresent() ?
               productCache.get(id).get() :
               pjp.proceed(pjp.getArgs());

    }

    @AfterReturning(value = "cacheableCall(id)", returning = "retProduct", argNames = "id,retProduct")
    public void saveCacheableIfNotPresent(Long id, Object retProduct) {
        Product product = (Product) retProduct;
        if (productCache.get(id).isEmpty()) {
            productCache.put(id, product);
        }
    }

    @AfterReturning(value = "cachePutCall()", returning = "retProduct")
    public void putInCache(Object retProduct) {
        Product product = (Product) retProduct;
        productCache.put(product.getId(), product);
    }

    @After(value = "cacheEvict(id)", argNames = "id")
    public void deleteFromCache(Long id) {
        productCache.remove(id);
    }

    @Pointcut("@annotation(ru.clevertec.clevertecTaskRest.cache.annotations.Cacheable) && productServiceMethods() && args(id,..))")
    public void cacheableCall(Long id){
    }


    @Pointcut("@annotation(ru.clevertec.clevertecTaskRest.cache.annotations.CachePut) && productServiceMethods()")
    private void cachePutCall() {
    }

    @Pointcut("@annotation(ru.clevertec.clevertecTaskRest.cache.annotations.CacheEvict) && productServiceMethods() && args(id,..))")
    private void cacheEvict(Long id) {
    }


    @Pointcut("within(ru.clevertec.clevertecTaskRest.service.IProductServiceImpl)")
    public void productServiceMethods(){
    }

}
