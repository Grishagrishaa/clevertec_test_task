package ru.clevertec.clevertecTaskRest.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import ru.clevertec.clevertecTaskRest.controllers.pagination.MyPage;
import ru.clevertec.clevertecTaskRest.service.IShopServiceImpl;
import ru.clevertec.clevertecTaskRest.service.dto.ReadProductDto;
import ru.clevertec.clevertecTaskRest.service.dto.ReadSaleCardDto;
import ru.clevertec.clevertecTaskRest.service.dto.Receipt;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(Controller.class)
@ExtendWith(SpringExtension.class)
public class ControllerTest {
    @MockBean
    private IShopServiceImpl shopService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private StringBuffer buffer;

    public ControllerTest() {
        this.buffer= new StringBuffer();
    }

    @Nested
    class GetAllTests {
        @ParameterizedTest
        @MethodSource("ru.clevertec.clevertecTaskRest.controllers.ControllerTest#provideProductsAndPageable")
        void shouldReturnMyPageOfProducts(Pageable pageable, List<ReadProductDto> productList) throws Exception {
            PageImpl<ReadProductDto> springPage = new PageImpl<>(productList, pageable, productList.size());//for easy MyPage initialization

            MyPage<ReadProductDto> actualPage = MyPage.Builder.<ReadProductDto>create()
                    .setContent(springPage.getContent())
                    .setFirst(springPage.isFirst())
                    .setLast(springPage.isLast())
                    .setNumber(springPage.getNumber())
                    .setNumberOfElements(springPage.getNumberOfElements())
                    .setTotalElements(springPage.getTotalElements())
                    .setTotalPages(springPage.getTotalPages())
                    .setSize(springPage.getSize())
                    .build();

            when(shopService.getAllProducts(pageable)).thenReturn(actualPage);
            mockMvc.perform(get(String.format("/api/v1/shop/products?page=%d&size=%d", pageable.getPageNumber(), pageable.getPageSize())))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("totalElements").value(actualPage.getTotalElements()))
                    .andDo(print());

            verify(shopService).getAllProducts(pageable);

        }

        @Test
        void shouldReturnEmptyPageOfProductsIfNoProductsInDb() throws Exception {
            Page<ReadProductDto> springPage = Page.empty();//for easy MyPage initialization

            MyPage<ReadProductDto> actualPage = MyPage.Builder.<ReadProductDto>create()
                    .setContent(springPage.getContent())
                    .setFirst(springPage.isFirst())
                    .setLast(springPage.isLast())
                    .setNumber(springPage.getNumber())
                    .setNumberOfElements(springPage.getNumberOfElements())
                    .setTotalElements(springPage.getTotalElements())
                    .setTotalPages(springPage.getTotalPages())
                    .setSize(springPage.getSize())
                    .build();

            PageRequest pageable = PageRequest.of(1, 5);
            when(shopService.getAllProducts(pageable)).thenReturn(actualPage);

            mockMvc.perform(get(String.format("/api/v1/shop/products?page=%d&size=%d", pageable.getPageNumber(), pageable.getPageSize())))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("totalElements").value(actualPage.getTotalElements()))
                    .andDo(print());

            verify(shopService).getAllProducts(pageable);

        }

        @ParameterizedTest
        @MethodSource("ru.clevertec.clevertecTaskRest.controllers.ControllerTest#provideSaleCardsAndPageable")
        void shouldReturnMyPageOfSaleCards(Pageable pageable, List<ReadSaleCardDto> productList) throws Exception {
            PageImpl<ReadSaleCardDto> springPage = new PageImpl<>(productList, pageable, productList.size());//for easy MyPage initialization

            MyPage<ReadSaleCardDto> actualPage = MyPage.Builder.<ReadSaleCardDto>create()
                    .setContent(springPage.getContent())
                    .setFirst(springPage.isFirst())
                    .setLast(springPage.isLast())
                    .setNumber(springPage.getNumber())
                    .setNumberOfElements(springPage.getNumberOfElements())
                    .setTotalElements(springPage.getTotalElements())
                    .setTotalPages(springPage.getTotalPages())
                    .setSize(springPage.getSize())
                    .build();

            when(shopService.getAllSaleCards(pageable)).thenReturn(actualPage);
            mockMvc.perform(get(String.format("/api/v1/shop/salecards?page=%d&size=%d", pageable.getPageNumber(), pageable.getPageSize())))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("totalElements").value(actualPage.getTotalElements()))
                    .andDo(print());

            verify(shopService).getAllSaleCards(pageable);

        }

        @Test
        void shouldNotReturnEmptyPageOfOfSaleCardsIfNoSaleCardsInDb() throws Exception {
            Page<ReadSaleCardDto> springPage = Page.empty();//for easy MyPage initialization

            MyPage<ReadSaleCardDto> actualPage = MyPage.Builder.<ReadSaleCardDto>create()
                    .setContent(springPage.getContent())
                    .setFirst(springPage.isFirst())
                    .setLast(springPage.isLast())
                    .setNumber(springPage.getNumber())
                    .setNumberOfElements(springPage.getNumberOfElements())
                    .setTotalElements(springPage.getTotalElements())
                    .setTotalPages(springPage.getTotalPages())
                    .setSize(springPage.getSize())
                    .build();

            PageRequest pageable = PageRequest.of(1, 5);
            when(shopService.getAllSaleCards(pageable)).thenReturn(actualPage);
            mockMvc.perform(get(String.format("/api/v1/shop/salecards?page=%d&size=%d", pageable.getPageNumber(), pageable.getPageSize())))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("totalElements").value(actualPage.getTotalElements()))
                    .andDo(print());

            verify(shopService).getAllSaleCards(pageable);

        }
    }

    @ParameterizedTest
    @MethodSource("ru.clevertec.clevertecTaskRest.controllers.ControllerTest#provideProductsAndPageable")
    void shouldReturnReceiptIfIdsProvided(Pageable pageable, List<ReadProductDto> productList) throws Exception {
        buffer.setLength(0);
        List<Long> ids = productList.stream()
                .map(ReadProductDto::getId)
                .toList();

        Receipt actualReceipt = Receipt.Builder.create()
                .setProductDtos(productList)
                .setTotalSum(productList.stream()
                        .mapToDouble(ReadProductDto::getCost)
                        .sum())
                .build();

        when(shopService.getReceipt(ids))
                .thenReturn(actualReceipt);

        for (ReadProductDto product : productList) {
            buffer.append("itemId=").append(product.getId()).append("&");
        }


        String pathVariables = buffer.toString();
        mockMvc.perform(put("/api/v1/shop/products?" + pathVariables.substring(0, pathVariables.length() - 1)))
                .andExpect(status().isAccepted())
                .andDo(print());

        verify(shopService).getReceipt(ids);

    }

    @Test
    void shouldNotReturnReceiptIfProductIsNotProvided() throws Exception {
        List<Long> ids = List.of(1000L, 1001L, 1002L);
        when(shopService.getReceipt(ids)).thenThrow(ConstraintViolationException.class);

        for (long id : ids) {
            buffer.append("itemId=").append(id).append("&");
        }

        String pathVariables = buffer.toString();
        mockMvc.perform(put("/api/v1/shop/products?" + pathVariables.substring(0, pathVariables.length() - 1)))
                .andExpect(status().isBadRequest())
                .andDo(print());

        verify(shopService).getReceipt(ids);
    }

    static Stream<Arguments> provideProductsAndPageable() {
        List<ReadProductDto> readProductDtoList = Arrays.asList(
                ReadProductDto.Builder.create()
                        .setId(1L)
                        .setName("TestProduct1")
                        .setManufacturer("Toyota")
                        .setCost(53.9)
                        .setWeight(355)
                        .setExpirationDate(LocalDateTime.MIN)
                        .setCount(4L)
                        .build(),
                ReadProductDto.Builder.create()
                        .setId(2L)
                        .setName("TestProduct2")
                        .setManufacturer("Porsche")
                        .setCost(21.0)
                        .setWeight(123)
                        .setExpirationDate(LocalDateTime.MIN)
                        .setCount(3L)
                        .build(),
                ReadProductDto.Builder.create()
                        .setId(3L)
                        .setName("TestProduct3")
                        .setManufacturer("BMW")
                        .setCost(28.0)
                        .setWeight(123)
                        .setExpirationDate(LocalDateTime.MIN)
                        .setCount(21L)
                        .build(),
                ReadProductDto.Builder.create()
                        .setId(4L)
                        .setName("TestProduct4")
                        .setManufacturer("AUDI")
                        .setCost(2.0)
                        .setWeight(11)
                        .setExpirationDate(LocalDateTime.MIN)
                        .setCount(45L)
                        .build()
        );
        return Stream.of(
                Arguments.of(PageRequest.of(1,5), readProductDtoList),
                Arguments.of(PageRequest.of(2, 2), readProductDtoList),
                Arguments.of(PageRequest.of(3, 1), readProductDtoList));
    }

    static Stream<Arguments> provideSaleCardsAndPageable(){
        List<ReadSaleCardDto> readSaleCardDtos = Arrays.asList(
                ReadSaleCardDto.Builder.create()
                        .setYear(2021L)
                        .setSalePercentage(10)
                        .build(),
                ReadSaleCardDto.Builder.create()
                        .setYear(2022L)
                        .setSalePercentage(20)
                        .build(),
                ReadSaleCardDto.Builder.create()
                        .setYear(2023L)
                        .setSalePercentage(30)
                        .build());

        return Stream.of(
                Arguments.of(PageRequest.of(1,5), readSaleCardDtos),
                Arguments.of(PageRequest.of(2, 2), readSaleCardDtos),
                Arguments.of(PageRequest.of(3, 1), readSaleCardDtos));
    }
}
