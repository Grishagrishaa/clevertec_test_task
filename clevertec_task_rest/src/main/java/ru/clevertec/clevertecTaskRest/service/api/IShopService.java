package ru.clevertec.clevertecTaskRest.service.api;

import ru.clevertec.clevertecTaskRest.controllers.pagination.MyPage;
import ru.clevertec.clevertecTaskRest.service.dto.ReadProductDto;
import ru.clevertec.clevertecTaskRest.service.dto.ReadSaleCardDto;
import ru.clevertec.clevertecTaskRest.service.dto.Receipt;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IShopService {
    Receipt getReceipt(List<Long> ids);

    Receipt getReceipt(List<Long> ids, Long saleCardNumber);

    MyPage<ReadProductDto> getAllProducts(Pageable pageable);

    MyPage<ReadSaleCardDto> getAllSaleCards(Pageable pageable);

}
