package ru.clevertec.clevertecTaskRest.service;

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
import org.springframework.data.domain.Pageable;
import ru.clevertec.clevertecTaskRest.dao.entity.BaseEntity;
import ru.clevertec.clevertecTaskRest.dao.entity.Product;
import ru.clevertec.clevertecTaskRest.dao.entity.SaleCard;
import ru.clevertec.clevertecTaskRest.service.dto.ReadProductDto;
import ru.clevertec.clevertecTaskRest.service.dto.ReadSaleCardDto;
import ru.clevertec.clevertecTaskRest.service.dto.Receipt;
import ru.clevertec.clevertecTaskRest.util.ModelMapperUtils;
import ru.clevertec.clevertecTaskRest.util.builder.MyPageBuilder;
import ru.clevertec.clevertecTaskRest.util.builder.ProductBuilder;
import ru.clevertec.clevertecTaskRest.util.builder.SaleCardBuilder;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;


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

    @InjectMocks
    private IShopServiceImpl shopService;

    public IShopServiceImplTest() {
        mapper = new ModelMapper();
    }

    @Nested
    class GetReceiptTest{
        @ParameterizedTest
        @MethodSource("ru.clevertec.clevertecTaskRest.service.IShopServiceImplTest#provideProductsList")
        void shouldReturnReceiptWithExistedProductsId(List<Product> testProducts){
            mockUpdateMethod(testProducts);

            Receipt actualReceipt = Receipt.Builder.create()
                    .setProductDtos(testProducts.stream().map(ProductBuilder::convertToDto).toList())
                    .setTotalSum(testProducts.stream().mapToDouble(Product::getCost).sum())
                    .build();

            assertThat(shopService.getReceipt(testProducts.stream().map(BaseEntity::getId).toList()))
                    .isEqualTo(actualReceipt);
        }

        @ParameterizedTest
        @MethodSource("ru.clevertec.clevertecTaskRest.service.IShopServiceImplTest#provideProductsListAndSaleCard")
        void shouldReturnReceiptWithExistedProductsIdAndSaleCard(List<Product> testProducts, SaleCard saleCard){
            mockUpdateMethod(testProducts);
            doReturn(saleCard).when(saleCardService).getSaleCardById(saleCard.getId());

            Receipt actualReceipt = Receipt.Builder.create()
                    .setProductDtos(testProducts.stream().map(ProductBuilder::convertToDto).toList())
                    .setTotalSum((testProducts.stream().mapToDouble(Product::getCost).sum()), saleCard)
                    .build();

            assertThat(shopService.getReceipt(testProducts.stream().map(BaseEntity::getId).toList(), saleCard.getId()))
                    .isEqualTo(actualReceipt);
        }
    }

    @Nested
    class getAllTest{
        @ParameterizedTest
        @MethodSource("ru.clevertec.clevertecTaskRest.service.IShopServiceImplTest#provideProductListAndPageable")
        void ShouldGetPageOfProducts(Pageable pageable, List<Product> testProducts){
            testProducts.forEach(product -> doReturn(ProductBuilder.convertToDto(product))
                    .when(conversionService).convert(product, ReadProductDto.class));

            PageImpl<Product> springPage = new PageImpl<>(testProducts, pageable, testProducts.size());
            doReturn(springPage).when(productService).getAll(pageable);


            assertThat(MyPageBuilder.fromSpringPage(springPage, testProducts.stream().map(ProductBuilder::convertToDto).toList()))
                    .isEqualTo(shopService.getAllProducts(pageable));
        }

        @ParameterizedTest
        @MethodSource("ru.clevertec.clevertecTaskRest.service.IShopServiceImplTest#provideSaleCardListAndPageable")
        void ShouldGetPageOfSaleCards(Pageable pageable, List<SaleCard> testSaleCards){
            testSaleCards.forEach(saleCard -> doReturn(SaleCardBuilder.convertToDto(saleCard))
                    .when(conversionService).convert(saleCard, ReadSaleCardDto.class));

            PageImpl<SaleCard> springPage = new PageImpl<>(testSaleCards, pageable, testSaleCards.size());
            doReturn(springPage).when(saleCardService).getAllSaleCards(pageable);

            assertThat(MyPageBuilder.fromSpringPage(springPage, testSaleCards.stream().map(SaleCardBuilder::convertToDto).toList()))
                    .isEqualTo(shopService.getAllSaleCards(pageable));
        }
    }

    private void mockUpdateMethod(List<Product> testProducts) {
        testProducts.forEach(
                productStub -> {
                    doReturn(productStub)
                            .when(productService).getById(productStub.getId());
                    doReturn(productStub.setCount(productStub.getCount() - 1))
                            .when(productService).update(productStub.getId(), productStub);
                    doReturn(ProductBuilder.convertToDto(productStub))
                            .when(conversionService).convert(productStub, ReadProductDto.class);
                });
    }

    static Stream<Arguments> provideProducts(){
        return Stream.of(
                Arguments.of(ProductBuilder.randomValues().build()),
                Arguments.of(ProductBuilder.randomValues().build()),
                Arguments.of(ProductBuilder.randomValues().build()),
                Arguments.of(ProductBuilder.randomValues().build()));
    }

    static Stream<Arguments> provideProductsList(){
        return Stream.of(
                Arguments.of(
                        provideProducts()
                                .map(Arguments::get)
                                .map(array -> (Product) array[0])
                                .toList()));
    }

    static Stream<Arguments> provideSaleCardListAndPageable(){
        List<SaleCard> saleCardList = Arrays.asList(
                SaleCardBuilder.randomValues().build(),
                SaleCardBuilder.randomValues().build(),
                SaleCardBuilder.randomValues().build());
        return Stream.of(
                Arguments.of(PageRequest.of(1,5), saleCardList),
                Arguments.of(PageRequest.of(2, 2), saleCardList),
                Arguments.of(PageRequest.of(3, 1), saleCardList));
    }

    static Stream<Arguments> provideProductsListAndSaleCard() {
        List<Product> productList = provideProducts()
                .map(Arguments::get)
                .map(array -> (Product) array[0])
                .toList();
        return Stream.of(
                Arguments.of(productList, SaleCardBuilder.randomValues().build()),
                Arguments.of(productList, SaleCardBuilder.randomValues().build()),
                Arguments.of(productList, SaleCardBuilder.randomValues().build()));
    }

    static Stream<Arguments> provideProductListAndPageable() {
        List<Product> productList = provideProducts()
                .map(Arguments::get)
                .map(array -> (Product) array[0])
                .toList();
        return Stream.of(
                Arguments.of(PageRequest.of(1,5), productList),
                Arguments.of(PageRequest.of(2, 2), productList),
                Arguments.of(PageRequest.of(3, 1), productList));
    }
}