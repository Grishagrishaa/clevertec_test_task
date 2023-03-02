package ru.clevertec.clevertecTaskRest.service;

import ru.clevertec.clevertecTaskRest.controllers.pagination.PageDtos;
import ru.clevertec.clevertecTaskRest.dao.entity.Product;
import ru.clevertec.clevertecTaskRest.service.api.IProductService;
import ru.clevertec.clevertecTaskRest.service.api.ISaleCardService;
import ru.clevertec.clevertecTaskRest.service.api.IShopService;
import ru.clevertec.clevertecTaskRest.service.dto.ReadProductDto;
import ru.clevertec.clevertecTaskRest.service.dto.ReadSaleCardDto;
import ru.clevertec.clevertecTaskRest.service.dto.Receipt;
import org.modelmapper.ModelMapper;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class IShopServiceImpl implements IShopService {
    private final IProductService productService;
    private final ISaleCardService saleCardService;
    private final ConversionService conversionService;
    private final ModelMapper mapper;


    public IShopServiceImpl(IProductServiceImpl productService, ISaleCardService saleCardService, ConversionService conversionService, ModelMapper mapper) {
        this.productService = productService;
        this.saleCardService = saleCardService;
        this.conversionService = conversionService;
        this.mapper = mapper;
    }


    @Override
    @Transactional
    public Receipt getReceipt(List<Long> ids) {
        List<ReadProductDto> products = buyProducts(ids);

        double sum = products.stream()
                .mapToDouble(ReadProductDto::getCost)
                .sum();

        return Receipt.Builder.create()
                .setProductDtos(products)
                .setTotalSum(sum)
                .build();
    }

    @Override
    @Transactional
    public Receipt getReceipt(List<Long> ids, Long saleCardId) {
        ArrayList<ReadProductDto> products = buyProducts(ids);
        ru.clevertec.clevertecTaskRest.dao.entity.SaleCard saleCard = saleCardService.getSaleCardById(saleCardId);

        double sum = products.stream()
                  .mapToDouble(ReadProductDto::getCost)
                  .sum();

        return Receipt.Builder.create()
                .setProductDtos(products)
                .setTotalSum(sum, saleCard)
                .build();
    }

    @Override
    public PageDtos<ReadProductDto> getAllProducts(Pageable pageable) {
        Page<Product> springPage = productService.getAll(pageable);

        List<ReadProductDto> readProductDtoList = springPage.getContent()
                .stream()
                .map(e -> conversionService.convert(e, ReadProductDto.class))
                .toList();

        PageDtos<ReadProductDto> pageDtos = mapper.map(springPage, PageDtos.class);
        pageDtos.setContent(readProductDtoList);
        return pageDtos;
    }

    @Override
    public PageDtos<ReadSaleCardDto> getAllSaleCards(Pageable pageable) {
        Page<ru.clevertec.clevertecTaskRest.dao.entity.SaleCard> springPage = saleCardService.getAllSaleCards(pageable);

        List<ReadSaleCardDto> readSaleCardDtoList = springPage.getContent()
                .stream()
                .map(e -> conversionService.convert(e, ReadSaleCardDto.class))
                .toList();

        PageDtos<ReadSaleCardDto> pageDtos = mapper.map(springPage, PageDtos.class);
        pageDtos.setContent(readSaleCardDtoList);

        return pageDtos;
    }

    @Transactional//connot be private due annotation
    ArrayList<ReadProductDto> buyProducts(List<Long> ids) {
        ArrayList<ReadProductDto> products = new ArrayList<>();
        for (Long id : ids) {
            Product product = productService.getById(id);

            long newCount = product.getCount() - 1;
            product.setCount(newCount);
            productService.update(product.getId(), product);

            products.add(conversionService.convert(product, ReadProductDto.class));
        }
        return products;
    }
}
