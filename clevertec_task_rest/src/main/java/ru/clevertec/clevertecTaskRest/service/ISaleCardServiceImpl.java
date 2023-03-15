package ru.clevertec.clevertecTaskRest.service;

import ru.clevertec.clevertecTaskRest.cache.annotations.CacheEvict;
import ru.clevertec.clevertecTaskRest.cache.annotations.CachePut;
import ru.clevertec.clevertecTaskRest.cache.annotations.Cacheable;
import ru.clevertec.clevertecTaskRest.cache.api.ICache;
import ru.clevertec.clevertecTaskRest.dao.api.ISaleCardRepository;
import ru.clevertec.clevertecTaskRest.dao.entity.SaleCard;
import ru.clevertec.clevertecTaskRest.service.api.ISaleCardService;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class ISaleCardServiceImpl implements ISaleCardService {
    private final ISaleCardRepository cardRepository;
    private final ModelMapper mapper;

    public ISaleCardServiceImpl(ISaleCardRepository cardRepository, ModelMapper mapper) {
        this.cardRepository = cardRepository;
        this.mapper = mapper;
    }

    @Override
    @Transactional
    @CachePut
    public SaleCard save(SaleCard saleCard) {
        return cardRepository.saveAndFlush(saleCard);
    }

    @Override
    @Cacheable
    public SaleCard getSaleCardById(Long id) {
        return cardRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("CARD WASN'T FOUND"));
    }

    @Override
    public Page<SaleCard> getAllSaleCards(Pageable pageable) {
        return cardRepository.findAll(pageable);
    }

    @Override
    @Transactional
    @CachePut
    public SaleCard update(Long id, SaleCard saleCard) {
        SaleCard saleCardFromDb = getSaleCardById(id);

        mapper.map(saleCard, saleCardFromDb);

        return save(saleCardFromDb);
    }

    @Override
    @Transactional
    @CacheEvict
    public void deleteById(Long id) {
        cardRepository.deleteById(id);
    }
}
