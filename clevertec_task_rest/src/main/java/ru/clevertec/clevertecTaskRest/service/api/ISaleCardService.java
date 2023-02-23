package ru.clevertec.clevertecTaskRest.service.api;

import ru.clevertec.clevertecTaskRest.dao.entity.SaleCard;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ISaleCardService {
   SaleCard save(SaleCard saleCard);

   SaleCard getSaleCardById(Long id);

   Page<SaleCard> getAllSaleCards(Pageable pageable);

   SaleCard update(Long id, SaleCard saleCard);

   void deleteById(Long id);

}
