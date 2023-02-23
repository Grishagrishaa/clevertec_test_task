package ru.clevertec.clevertecTaskRest.dao.api;

import ru.clevertec.clevertecTaskRest.dao.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IProductRepository extends JpaRepository<Product, Long> {
}
