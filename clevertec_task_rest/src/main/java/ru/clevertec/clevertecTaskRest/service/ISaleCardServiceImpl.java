package ru.clevertec.clevertecTaskRest.service;

import ru.clevertec.clevertecTaskRest.dao.api.ISaleCardRepository;
import ru.clevertec.clevertecTaskRest.dao.entity.SaleCard;
import ru.clevertec.clevertecTaskRest.service.api.ISaleCardService;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.cache.annotation.Cacheable;
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
    public SaleCard save(SaleCard saleCard) {
        return cardRepository.saveAndFlush(saleCard);
    }

    @Override
    @Cacheable(cacheNames = "saleCard")
    public SaleCard getSaleCardById(Long id) {
        return cardRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("CARD WASN'T FOUND"));
    }

    @Override
    public Page<SaleCard> getAllSaleCards(Pageable pageable) {
        return cardRepository.findAll(pageable);
    }

    @Override
    @Transactional
    public SaleCard update(Long id, SaleCard saleCard) {
        SaleCard saleCardFromDb = getSaleCardById(id);

        mapper.map(saleCard, saleCardFromDb);

        return save(saleCardFromDb);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        cardRepository.deleteById(id);
    }
}
