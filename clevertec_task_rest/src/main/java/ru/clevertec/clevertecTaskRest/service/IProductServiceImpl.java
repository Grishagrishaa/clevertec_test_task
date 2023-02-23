package ru.clevertec.clevertecTaskRest.service;

import ru.clevertec.clevertecTaskRest.dao.api.IProductRepository;
import ru.clevertec.clevertecTaskRest.dao.entity.Product;
import ru.clevertec.clevertecTaskRest.service.api.IProductService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.cache.annotation.*;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class IProductServiceImpl implements IProductService {
    private final IProductRepository productRepository;
    private final ConversionService conversionService;
    private final ModelMapper mapper;

    public IProductServiceImpl(IProductRepository productRepository, ConversionService conversionService, ModelMapper mapper) {
        this.productRepository = productRepository;
        this.conversionService = conversionService;
        this.mapper = mapper;
    }

    @Override
    @Transactional
    @CachePut(cacheNames = "product")
    public Product save(@Valid Product product) {
        return productRepository.saveAndFlush(product);
    }

    @Override
    @Cacheable(cacheNames="product")
    public Product getById(Long id) {
        return productRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("PRODUCT WAS NOT FOUND"));
    }

    @Override
//    @Cacheable(cacheNames="product")
    public Page<Product> getAll(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    @Override
    @Transactional
    @CacheEvict(cacheNames="product")
    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }

    @Override
    @CachePut(cacheNames="product")
    @Transactional
    public Product update(Long id, @Valid Product product) {
        Product productFromDb = getById(id);

        mapper.map(product, productFromDb);

        return save(productFromDb);
    }
}
