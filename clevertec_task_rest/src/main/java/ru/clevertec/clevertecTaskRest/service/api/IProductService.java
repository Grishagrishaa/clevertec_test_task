package ru.clevertec.clevertecTaskRest.service.api;

import ru.clevertec.clevertecTaskRest.dao.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IProductService {
    Product getById(Long id);
    Page<Product> getAll(Pageable pageable);
    Product save(Product product);
    void deleteById(Long id);
    Product update(Long id, Product product);
}
