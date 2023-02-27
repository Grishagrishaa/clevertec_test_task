package ru.clevertec.clevertecTaskRest.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import ru.clevertec.clevertecTaskRest.controllers.pagination.MyPage;
import ru.clevertec.clevertecTaskRest.dao.entity.BaseEntity;
import ru.clevertec.clevertecTaskRest.dao.entity.Product;
import ru.clevertec.clevertecTaskRest.service.dto.ReadProductDto;
import ru.clevertec.clevertecTaskRest.service.dto.ReadSaleCardDto;
import ru.clevertec.clevertecTaskRest.service.dto.Receipt;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class IShopServiceImplTest {
    @Mock
    private IProductServiceImpl productService;
    @Mock
    private ISaleCardServiceImpl saleCardService;
    @Spy
    private ConversionService conversionService;

    @Spy
    private ModelMapper mapper;

    private ObjectMapper mapperO = new ObjectMapper();

    @InjectMocks
    private IShopServiceImpl shopService;

    @Nested
    class GetReceiptTest{
        @ParameterizedTest
        @MethodSource("ru.clevertec.clevertecTaskRest.service.IShopServiceImplTest#provideProductsWithPositiveCount")
        void shouldReturnReceiptWithExistedProductsId(List<Product> testProducts){
            AtomicLong id = new AtomicLong(-1);
            testProducts.forEach(
                        product -> {
                            product.setId(id.getAndIncrement());
                            Product toBeReturned = testProducts.get(id.intValue());
                            doReturn(toBeReturned).when(productService).getById(toBeReturned.getId());

                            toBeReturned.setCount(toBeReturned.getCount() - 1);
                            doReturn(toBeReturned).when(productService).update(toBeReturned.getId(), toBeReturned);

                            ReadProductDto readProductDto = ReadProductDto.Builder.create()
                                    .setName(toBeReturned.getName())
                                    .setManufacturer(toBeReturned.getManufacturer())
                                    .setCost(toBeReturned.getCost())
                                    .setWeight(toBeReturned.getWeight())
                                    .setExpirationDate(toBeReturned.getExpirationDate())
                                    .setCount(toBeReturned.getCount())
                                    .build();
                            doReturn(readProductDto).when(conversionService).convert(toBeReturned, ReadProductDto.class);
                        });


            Receipt actualReceipt = Receipt.Builder.create()
                    .setProducts(testProducts.stream()
                            .map(product -> conversionService.convert(product, ReadProductDto.class))
                            .collect(Collectors.toList()))
                    .setTotalSum(testProducts.stream()
                            .mapToDouble(Product::getCost)
                            .sum())
                    .build();

            Receipt expectedReceipt = shopService.getReceipt(testProducts
                    .stream()
                    .map(BaseEntity::getId)
                    .collect(Collectors.toList()));


            assertThat(expectedReceipt).isEqualTo(actualReceipt);
        }

        @ParameterizedTest
        @MethodSource("ru.clevertec.clevertecTaskRest.service.IShopServiceImplTest#provideProductsWithPositiveCountAndSaleCard")
        void shouldReturnReceiptWithExistedProductsIdAndSaleCard(List<Product> testProducts, ru.clevertec.clevertecTaskRest.dao.entity.SaleCard saleCard){
            AtomicLong id = new AtomicLong(-1);
            testProducts.forEach(
                    product -> {
                        product.setId(id.getAndIncrement());
                        Product toBeReturned = testProducts.get(id.intValue());
                        doReturn(toBeReturned).when(productService).getById(toBeReturned.getId());

                        toBeReturned.setCount(toBeReturned.getCount() - 1);
                        doReturn(toBeReturned).when(productService).update(toBeReturned.getId(), toBeReturned);

                        ReadProductDto readProductDto = ReadProductDto.Builder.create()
                                .setName(toBeReturned.getName())
                                .setManufacturer(toBeReturned.getManufacturer())
                                .setCost(toBeReturned.getCost())
                                .setWeight(toBeReturned.getWeight())
                                .setExpirationDate(toBeReturned.getExpirationDate())
                                .setCount(toBeReturned.getCount())
                                .build();
                        doReturn(readProductDto).when(conversionService).convert(toBeReturned, ReadProductDto.class);
                    });
            doReturn(saleCard).when(saleCardService).getSaleCardById(saleCard.getId());


            Receipt actualReceipt = Receipt.Builder.create()
                    .setProducts(testProducts.stream()
                            .map(product -> conversionService.convert(product, ReadProductDto.class))
                            .collect(Collectors.toList()))
                    .setTotalSum((testProducts.stream()
                            .mapToDouble(Product::getCost)
                            .sum() * ((100.0 - saleCard.getSalePercentage())/100)))
                    .build();

            Receipt expectedReceipt = shopService.getReceipt(testProducts
                    .stream()
                    .map(BaseEntity::getId)
                    .collect(Collectors.toList()),
                    saleCard.getId());


            assertThat(expectedReceipt).isEqualTo(actualReceipt);
        }
    }

    @Nested
    class getAllTest{
        @ParameterizedTest
        @MethodSource("ru.clevertec.clevertecTaskRest.service.IShopServiceImplTest#provideProductsWithPositiveCount")
        void ShouldGetPageOfProducts(List<Product> testProducts){
            testProducts.forEach(product -> doReturn(ReadProductDto.Builder.create()
                                                                            .setId(product.getId())
                                                                            .setName(product.getName())
                                                                            .setManufacturer(product.getManufacturer())
                                                                            .setCost(product.getCost())
                                                                            .setWeight(product.getWeight())
                                                                            .setCount(product.getCount())
                                                                            .setExpirationDate(product.getExpirationDate())
                                                                            .build())
                                            .when(conversionService)
                                            .convert(product, ReadProductDto.class));

            PageRequest pageable = PageRequest.of(1, 5);
            PageImpl<Product> springPage = new PageImpl<>(testProducts, pageable, testProducts.size());

            MyPage<ReadProductDto> actualPage = MyPage.Builder.<ReadProductDto>create()
                    .setContent(springPage.getContent().stream()
                            .map(product -> ReadProductDto.Builder.create()
                                    .setId(product.getId())
                                    .setName(product.getName())
                                    .setManufacturer(product.getManufacturer())
                                    .setCost(product.getCost())
                                    .setWeight(product.getWeight())
                                    .setCount(product.getCount())
                                    .setExpirationDate(product.getExpirationDate())
                                    .build())
                            .toList())
                    .setFirst(springPage.isFirst())
                    .setLast(springPage.isLast())
                    .setNumber(springPage.getNumber())
                    .setNumberOfElements(springPage.getNumberOfElements())
                    .setTotalElements(springPage.getTotalElements())
                    .setTotalPages(springPage.getTotalPages())
                    .setSize(springPage.getSize())
                    .build();

            doReturn(springPage).when(productService).getAll(pageable);


            assertThat(actualPage).isEqualTo(shopService.getAllProducts(pageable));
        }

        @ParameterizedTest
        @MethodSource("ru.clevertec.clevertecTaskRest.service.IShopServiceImplTest#provideSaleCards")
        void ShouldGetPageOfSaleCards(List<ru.clevertec.clevertecTaskRest.dao.entity.SaleCard> testSaleCards){
            testSaleCards.forEach(saleCard -> doReturn(ReadSaleCardDto.Builder.create()
                                                                                .setId(saleCard.getId())
                                                                                .setYear(saleCard.getYear())
                                                                                .setSalePercentage(saleCard.getSalePercentage())
                                                                                .build())
                    .when(conversionService)
                    .convert(saleCard, ReadSaleCardDto.class));

            PageRequest pageable = PageRequest.of(1, 5);
            PageImpl<ru.clevertec.clevertecTaskRest.dao.entity.SaleCard> springPage = new PageImpl<>(testSaleCards, pageable, testSaleCards.size());

            MyPage<ReadSaleCardDto> actualPage = MyPage.Builder.<ReadSaleCardDto>create()
                    .setContent(springPage.getContent().stream()
                            .map(saleCard -> ReadSaleCardDto.Builder.create()
                                    .setId(saleCard.getId())
                                    .setYear(saleCard.getYear())
                                    .setSalePercentage(saleCard.getSalePercentage())
                                    .build())
                            .toList())
                    .setFirst(springPage.isFirst())
                    .setLast(springPage.isLast())
                    .setNumber(springPage.getNumber())
                    .setNumberOfElements(springPage.getNumberOfElements())
                    .setTotalElements(springPage.getTotalElements())
                    .setTotalPages(springPage.getTotalPages())
                    .setSize(springPage.getSize())
                    .build();

            doReturn(springPage).when(saleCardService).getAllSaleCards(pageable);


            assertThat(actualPage).isEqualTo(shopService.getAllSaleCards(pageable));


        }
    }

    static Stream<Arguments> provideProductsWithPositiveCount(){
        return Stream.of(
                Arguments.of(Arrays.asList(
                        Product.Builder.create()
                                .setName("TestProduct1")
                                .setManufacturer("Toyota")
                                .setCost(53.9)
                                .setWeight(355)
                                .setExpirationDate(LocalDateTime.MIN)
                                .setCount(4L)
                                .build(),
                        Product.Builder.create()
                                .setName("TestProduct2")
                                .setManufacturer("Porsche")
                                .setCost(21.0)
                                .setWeight(123)
                                .setExpirationDate(LocalDateTime.MIN)
                                .setCount(3L)
                                .build(),
                        Product.Builder.create()
                                .setName("TestProduct3")
                                .setManufacturer("BMW")
                                .setCost(28.0)
                                .setWeight(123)
                                .setExpirationDate(LocalDateTime.MIN)
                                .setCount(21L)
                                .build(),
                        Product.Builder.create()
                                .setName("TestProduct4")
                                .setManufacturer("AUDI")
                                .setCost(2.0)
                                .setWeight(11)
                                .setExpirationDate(LocalDateTime.MIN)
                                .setCount(45L)
                                .build()
                )));
    }

    static Stream<Arguments> provideProductsWithPositiveCountAndSaleCard(){
        List<Product> productList = Arrays.asList(
                Product.Builder.create()
                        .setName("TestProduct1")
                        .setManufacturer("Toyota")
                        .setCost(53.9)
                        .setWeight(355)
                        .setExpirationDate(LocalDateTime.MIN)
                        .setCount(4L)
                        .build(),
                Product.Builder.create()
                        .setName("TestProduct2")
                        .setManufacturer("Porsche")
                        .setCost(21.0)
                        .setWeight(123)
                        .setExpirationDate(LocalDateTime.MIN)
                        .setCount(3L)
                        .build(),
                Product.Builder.create()
                        .setName("TestProduct3")
                        .setManufacturer("BMW")
                        .setCost(28.0)
                        .setWeight(123)
                        .setExpirationDate(LocalDateTime.MIN)
                        .setCount(21L)
                        .build(),
                Product.Builder.create()
                        .setName("TestProduct4")
                        .setManufacturer("AUDI")
                        .setCost(2.0)
                        .setWeight(11)
                        .setExpirationDate(LocalDateTime.MIN)
                        .setCount(45L)
                        .build()
        );
        return Stream.of(
                Arguments.of(productList, ru.clevertec.clevertecTaskRest.dao.entity.SaleCard.Builder.create()
                        .setYear(2021L)
                        .setSalePercentage(10)
                        .build()),
                Arguments.of(productList, ru.clevertec.clevertecTaskRest.dao.entity.SaleCard.Builder.create()
                        .setYear(2022L)
                        .setSalePercentage(20)
                        .build()),
                Arguments.of(productList, ru.clevertec.clevertecTaskRest.dao.entity.SaleCard.Builder.create()
                        .setYear(2023L)
                        .setSalePercentage(30)
                        .build()));
    }

    static Stream<Arguments> provideSaleCards(){
        return Stream.of(
                Arguments.of(Arrays.asList(
                        ru.clevertec.clevertecTaskRest.dao.entity.SaleCard.Builder.create()
                                .setYear(2021L)
                                .setSalePercentage(10)
                                .build(),
                        ru.clevertec.clevertecTaskRest.dao.entity.SaleCard.Builder.create()
                                .setYear(2022L)
                                .setSalePercentage(20)
                                .build(),
                        ru.clevertec.clevertecTaskRest.dao.entity.SaleCard.Builder.create()
                                .setYear(2023L)
                                .setSalePercentage(30)
                                .build())));
    }
}