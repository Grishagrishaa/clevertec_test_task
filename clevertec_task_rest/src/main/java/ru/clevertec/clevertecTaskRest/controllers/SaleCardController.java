package ru.clevertec.clevertecTaskRest.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.clevertec.clevertecTaskRest.controllers.api.IEntityController;
import ru.clevertec.clevertecTaskRest.controllers.pagination.PageDtos;
import ru.clevertec.clevertecTaskRest.dao.entity.SaleCard;
import ru.clevertec.clevertecTaskRest.service.api.ISaleCardService;
import ru.clevertec.clevertecTaskRest.service.dto.createDto.CreateSaleCardDto;
import ru.clevertec.clevertecTaskRest.service.dto.readDto.ReadProductDto;
import ru.clevertec.clevertecTaskRest.service.dto.readDto.ReadSaleCardDto;

import java.util.List;

@RestController
@RequestMapping("${app.shopController.path}/salecards")
public class SaleCardController implements IEntityController<ReadSaleCardDto, CreateSaleCardDto> {
    private final ISaleCardService service;
    private final ConversionService conversionService;
    private final ModelMapper mapper;

    public SaleCardController(ISaleCardService service, ConversionService conversionService, ModelMapper mapper) {
        this.service = service;
        this.conversionService = conversionService;
        this.mapper = mapper;
    }

    @Override
    public ResponseEntity<ReadSaleCardDto> createProduct(CreateSaleCardDto createDto) {
        SaleCard saleCardToCreate = conversionService.convert(createDto, SaleCard.class);

        return new ResponseEntity<>(conversionService.convert(service.save(saleCardToCreate), ReadSaleCardDto.class),
                   HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<ReadSaleCardDto> getProduct(Long id) {
        return new ResponseEntity<>(conversionService.convert(service.getSaleCardById(id), ReadSaleCardDto.class),
                   HttpStatus.OK);
    }

    @Override
    public ResponseEntity<PageDtos<ReadSaleCardDto>> getAllProducts(Integer page, Integer size) {
        Page<SaleCard> springPage = service.getAllSaleCards(PageRequest.of(page, size));

        List<ReadSaleCardDto> readSaleCardDtos = springPage.getContent()
                .stream()
                .map(e -> conversionService.convert(e, ReadSaleCardDto.class))
                .toList();

        PageDtos<ReadSaleCardDto> pageDtos = mapper.map(springPage, PageDtos.class);
        pageDtos.setContent(readSaleCardDtos);
        return new ResponseEntity<>(pageDtos,
                   HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ReadSaleCardDto> updateProduct(Long id, CreateSaleCardDto createDto) {
        SaleCard updateDataSaleCard = conversionService.convert(createDto, SaleCard.class);

        return new ResponseEntity<>(conversionService.convert(service.update(id, updateDataSaleCard), ReadSaleCardDto.class),
                   HttpStatus.ACCEPTED);
    }

    @Override
    public void deleteProduct(Long id) {
        service.deleteById(id);
    }
}
