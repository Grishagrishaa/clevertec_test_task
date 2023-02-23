package ru.clevertec.clevertecTaskRest.dao.api;

import ru.clevertec.clevertecTaskRest.dao.entity.SaleCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ISaleCardRepository extends JpaRepository<SaleCard, Long> {
}
