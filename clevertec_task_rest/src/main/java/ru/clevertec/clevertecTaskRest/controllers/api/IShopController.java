package ru.clevertec.clevertecTaskRest.controllers.api;

import ru.clevertec.clevertecTaskRest.controllers.pagination.MyPage;
import ru.clevertec.clevertecTaskRest.service.dto.ReadProductDto;
import ru.clevertec.clevertecTaskRest.service.dto.ReadSaleCardDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RequestMapping("${app.shopController.path}")
public interface IShopController {
    @PutMapping("/products")
    ResponseEntity<String> getReceipt(@RequestParam(required = true, name = "itemId")List<Long> itemId,
                                      @RequestParam(required = false, name = "saleCardId")Long saleCardId);

    @GetMapping("/products")
    ResponseEntity<MyPage<ReadProductDto>> getAllProducts(@RequestParam(required = false, defaultValue = "0", name = "page") Integer page,
                                                          @RequestParam(required = false, defaultValue = "5", name = "size") Integer size);
    @GetMapping("/salecards")
    ResponseEntity<MyPage<ReadSaleCardDto>> getAllSaleCards(@RequestParam(required = false, defaultValue = "0", name = "page") Integer page,
                                                            @RequestParam(required = false, defaultValue = "5", name = "size") Integer size);

}
