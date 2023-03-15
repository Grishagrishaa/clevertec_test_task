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
import ru.clevertec.clevertecTaskRest.dao.entity.Product;
import ru.clevertec.clevertecTaskRest.service.api.IProductService;
import ru.clevertec.clevertecTaskRest.service.dto.CreateDto.CreateProductDto;
import ru.clevertec.clevertecTaskRest.service.dto.ReadDto.ReadProductDto;

import java.util.List;

@RestController
@RequestMapping("${app.shopController.path}/products")
public class ProductController implements IEntityController<ReadProductDto, CreateProductDto> {
    private final IProductService service;
    private final ConversionService conversionService;
    private final ModelMapper mapper;

    public ProductController(IProductService service, ConversionService conversionService, ModelMapper mapper) {
        this.service = service;
        this.conversionService = conversionService;
        this.mapper = mapper;
    }

    @Override
    public ResponseEntity<ReadProductDto> createProduct(CreateProductDto createDto) {//todo createProductDto to Product, same with salecard
        Product productToCreate = conversionService.convert(createDto, Product.class);

        return new ResponseEntity<>(conversionService.convert(service.save(productToCreate), ReadProductDto.class),
                   HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<ReadProductDto> getProduct(Long id) {
        return new ResponseEntity<>(conversionService.convert(service.getById(id), ReadProductDto.class),
                   HttpStatus.OK);
    }

    @Override
    public ResponseEntity<PageDtos<ReadProductDto>> getAllProducts(Integer page, Integer size) {
        Page<Product> springPage = service.getAll(PageRequest.of(page, size));

        List<ReadProductDto> readProductDtoList = springPage.getContent()
                .stream()
                .map(e -> conversionService.convert(e, ReadProductDto.class))
                .toList();

        PageDtos<ReadProductDto> pageDtos = mapper.map(springPage, PageDtos.class);
        pageDtos.setContent(readProductDtoList);
        return new ResponseEntity<>(pageDtos,
                   HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ReadProductDto> updateProduct(Long id, CreateProductDto createDto) {
        Product updateDataProduct = conversionService.convert(createDto, Product.class);

        return new ResponseEntity<>(conversionService.convert(service.update(id, updateDataProduct), ReadProductDto.class),
                   HttpStatus.ACCEPTED);
    }

    @Override
    public void deleteProduct(Long id) {
        service.deleteById(id);
    }
}
