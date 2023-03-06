package ru.clevertec.clevertecTaskRest.aspects.cache;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;
import ru.clevertec.clevertecTaskRest.cache.api.ICache;
import ru.clevertec.clevertecTaskRest.dao.entity.SaleCard;

@Aspect
@Component
public class SaleCardServiceCaching {
    private final ICache<Long, SaleCard> saleCardCache;

    public SaleCardServiceCaching(ICache<Long, SaleCard> saleCardCache) {
        this.saleCardCache = saleCardCache;
    }

    @Around(value = "cacheableCall(id)", argNames = "pjp,id")
    public Object getFromCache(ProceedingJoinPoint pjp, Long id) throws Throwable {
        return saleCardCache.get(id).isPresent() ?
               saleCardCache.get(id).get() :
               pjp.proceed(pjp.getArgs());

    }

    @AfterReturning(value = "cacheableCall(id)", returning = "retSaleCard", argNames = "id,retSaleCard")
    public void saveCacheableIfNotPresent(Long id, Object retSaleCard) {
        SaleCard saleCard = (SaleCard) retSaleCard;
        if (saleCardCache.get(id).isEmpty()) {
            saleCardCache.put(id, saleCard);
        }
    }

    @AfterReturning(value = "cachePutCall()", returning = "retSaleCard")
    public void putInCache(Object retSaleCard) {
        SaleCard saleCard = (SaleCard) retSaleCard;
        saleCardCache.put(saleCard.getId(), saleCard);
    }

    @After(value = "cacheEvict(id)", argNames = "id")
    public void deleteFromCache(Long id) {
        saleCardCache.remove(id);
    }

    @Pointcut("@annotation(ru.clevertec.clevertecTaskRest.cache.annotations.Cacheable) && saleCardServiceMethods() && args(id,..))")
    public void cacheableCall(Long id){
    }


    @Pointcut("@annotation(ru.clevertec.clevertecTaskRest.cache.annotations.CachePut) && saleCardServiceMethods()")
    private void cachePutCall() {
    }

    @Pointcut("@annotation(ru.clevertec.clevertecTaskRest.cache.annotations.CacheEvict) && saleCardServiceMethods() && args(id,..))")
    private void cacheEvict(Long id) {
    }


    @Pointcut("within(ru.clevertec.clevertecTaskRest.service.ISaleCardServiceImpl)")
    public void saleCardServiceMethods(){
    }

}
