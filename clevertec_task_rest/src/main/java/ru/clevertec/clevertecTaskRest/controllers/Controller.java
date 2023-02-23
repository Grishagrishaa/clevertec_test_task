package ru.clevertec.clevertecTaskRest.controllers;

import ru.clevertec.clevertecTaskRest.controllers.api.IShopController;
import ru.clevertec.clevertecTaskRest.controllers.pagination.MyPage;
import ru.clevertec.clevertecTaskRest.service.IShopServiceImpl;
import ru.clevertec.clevertecTaskRest.service.api.IShopService;
import ru.clevertec.clevertecTaskRest.service.dto.ReadProductDto;
import ru.clevertec.clevertecTaskRest.service.dto.ReadSaleCardDto;
import ru.clevertec.clevertecTaskRest.service.dto.Receipt;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${app.shopController.path}/shop")
public class Controller implements IShopController {
    private final IShopService shopService;

    private final StringBuffer strBuff;

    public Controller(IShopServiceImpl shopService) {
        this.shopService = shopService;
        this.strBuff = new StringBuffer();
    }

    @Override
    public ResponseEntity<String> getReceipt(List<Long> itemId, Long saleCardId) {
        strBuff.setLength(0);
        Receipt receipt = saleCardId == null ? shopService.getReceipt(itemId) : shopService.getReceipt(itemId, saleCardId);

        int i = 0;
        for (ReadProductDto product : receipt.getProducts()) {
            strBuff.append(String.format("%d. %s___%.2f RUB", ++i, product.getName(), product.getCost()));
            strBuff.append('\n');
        }

        strBuff.append("------------------");
        strBuff.append('\n');
        strBuff.append(String.format("Total Sum - %.2f", receipt.getTotalSum()));

        return new ResponseEntity<>(strBuff.toString(),
                                    HttpStatus.ACCEPTED);
    }

    @Override
    public ResponseEntity<MyPage<ReadProductDto>> getAllProducts(Integer page, Integer size) {
        return new ResponseEntity<>(shopService.getAllProducts(PageRequest.of(page, size)),
                                    HttpStatus.OK);
    }

    @Override
    public ResponseEntity<MyPage<ReadSaleCardDto>> getAllSaleCards(Integer page, Integer size) {
        return new ResponseEntity<>(shopService.getAllSaleCards(PageRequest.of(page, size)),
                                    HttpStatus.OK);
    }


}
