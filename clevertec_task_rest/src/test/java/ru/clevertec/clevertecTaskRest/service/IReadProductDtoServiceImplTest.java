package ru.clevertec.clevertecTaskRest.service;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import ru.clevertec.clevertecTaskRest.dao.api.ISaleCardRepository;
import ru.clevertec.clevertecTaskRest.dao.entity.SaleCard;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNullPointerException;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class IReadProductDtoServiceImplTest {
    @Mock
    private ISaleCardRepository saleCardRepository;
    @Spy
    private ModelMapper mapper;

    @InjectMocks
    private ISaleCardServiceImpl service;

    @Nested
    class SaveReadProductDtoTest {
        @ParameterizedTest
        @NullSource
        void saveFailedIfSaleCardIsNull(SaleCard saleCard){
            doThrow(NullPointerException.class).when(saleCardRepository).saveAndFlush(isNull());


            assertThatNullPointerException()
                    .isThrownBy(() -> service.save(saleCard));


        }
    }

    @Nested
    class GetReadProductDtoTest {
        @Test
        void shouldFailGetIfIdNonPresented(){
            when(saleCardRepository.findById(1L)).thenReturn(Optional.of(new SaleCard()));
            when(saleCardRepository.findById(2L)).thenReturn(Optional.of(new SaleCard()));
            when(saleCardRepository.findById(3L)).thenReturn(Optional.of(new SaleCard()));

            doThrow(EntityNotFoundException.class).when(saleCardRepository).findById(4L);
            doThrow(EntityNotFoundException.class).when(saleCardRepository).findById(5L);

            assertAll(
                    () -> assertDoesNotThrow(() -> service.getSaleCardById(1L)),
                    () -> assertDoesNotThrow(() -> service.getSaleCardById(2L)),
                    () -> assertDoesNotThrow(() -> service.getSaleCardById(3L)),
                    () -> assertThrows(EntityNotFoundException.class, () -> service.getSaleCardById(4L)),
                    () -> assertThrows(EntityNotFoundException.class, () -> service.getSaleCardById(5L))
            );
        }

        @Test
        void shouldGetIfIdPresented(){
            when(saleCardRepository.findById(1L)).thenReturn(Optional.of(new SaleCard()));
            when(saleCardRepository.findById(2L)).thenReturn(Optional.of(new SaleCard()));
            when(saleCardRepository.findById(3L)).thenReturn(Optional.of(new SaleCard()));

            assertAll(
                    () -> assertThat(service.getSaleCardById(1L)).isInstanceOf(SaleCard.class),
                    () -> assertThat(service.getSaleCardById(2L)).isInstanceOf(SaleCard.class),
                    () -> assertThat(service.getSaleCardById(3L)).isInstanceOf(SaleCard.class)
            );
        }

        @DisplayName("Page of saleCards: :")
        @ParameterizedTest(name = "{index} => {0}, {1}")
        @CsvSource(delimiter = '|', textBlock = """
                    testCase1   |   1  |   5  |
                    testCase2   |   2  |   2  |
                    testCase3   |   3  |   1  |
                """)
        void shouldReturnSaleCardsPage(String description, Integer page, Integer size){
            PageRequest pageable = PageRequest.of(page, size);

            List<SaleCard> productList = List.of(SaleCard.Builder.create()
                                                            .setSalePercentage(10)
                                                            .setYear(2021L)
                                                            .build(),
                                                 SaleCard.Builder.create()
                                                            .setSalePercentage(20)
                                                            .setYear(2022L)
                                                            .build(),
                                                 SaleCard.Builder.create()
                                                            .setSalePercentage(30)
                                                            .setYear(2023L)
                                                            .build());

            Page<SaleCard> actualPage = new PageImpl<>(productList, pageable, productList.size());

            BDDMockito.given(saleCardRepository.findAll(pageable))
                    .willReturn(new PageImpl<>(productList, pageable, productList.size()));

            assertEquals(service.getAllSaleCards(pageable), actualPage);
            verify(saleCardRepository).findAll(pageable);
        }
    }

    @Nested
    class UpdateReadProductDtoTest {
        @Test
        public void whenGivenIdShouldUpdateSaleCardIfFound() {
            SaleCard actualSaleCard = SaleCard.Builder.create()
                    .setSalePercentage(10)
                    .setYear(2021L)
                    .build();
            actualSaleCard.setId(1L);

            SaleCard newSaleCard = SaleCard.Builder.create()
                    .setSalePercentage(20)
                    .setYear(2022L)
                    .build();
            newSaleCard.setId(2L);

            mapper.getConfiguration()
                    .setAmbiguityIgnored(true)
                    .setSkipNullEnabled(false);
            mapper.addMappings(
                    new PropertyMap<SaleCard, SaleCard>() {
                        @Override
                        protected void configure() {
                            skip(destination.getId());
                            skip(destination.getUpdatedDate());
                            skip(destination.getCreatedDate());
                        }
                    });


            doReturn(Optional.of(actualSaleCard)).when(saleCardRepository).findById(actualSaleCard.getId());
            doReturn(actualSaleCard).when(saleCardRepository).saveAndFlush(actualSaleCard);

            SaleCard expectedSaleProduct = service.update(actualSaleCard.getId(), newSaleCard);
            assertAll(
                    () -> assertThat(expectedSaleProduct).isEqualTo(newSaleCard),
                    () -> assertThat(expectedSaleProduct.getId()).isEqualTo(actualSaleCard.getId())
            );

            verify(saleCardRepository).findById(actualSaleCard.getId());
            verify(saleCardRepository).saveAndFlush(newSaleCard);
        }

        @Test
        public void shouldThrowExceptionWhenUserDoesntExist() {
            SaleCard actualSaleCard = SaleCard.Builder.create()
                    .setSalePercentage(10)
                    .setYear(2021L)
                    .build();
            actualSaleCard.setId(1L);

            SaleCard newSaleCard = SaleCard.Builder.create()
                    .setSalePercentage(20)
                    .setYear(2022L)
                    .build();
            newSaleCard.setId(2L);

            doReturn(Optional.empty()).when(saleCardRepository).findById(anyLong());

            assertThrows(EntityNotFoundException.class, () -> service.update(actualSaleCard.getId(), newSaleCard));
        }

    }

    @Nested
    class DeleteProductTest{
        @ParameterizedTest
        @ValueSource(longs = {1, 2, 3})
        void shouldDeleteWithGivenId(Long id){
            doNothing().when(saleCardRepository).deleteById(id);

            service.deleteById(id);

            verify(saleCardRepository).deleteById(id);
        }

        @ParameterizedTest
        @ValueSource(longs = {4, 5, 6})
        void shouldNotDeleteAndThrowExceptionIfIdIsNull(Long id){
            doThrow(IllegalArgumentException.class).when(saleCardRepository).deleteById(id);

            assertThrows(IllegalArgumentException.class, () -> service.deleteById(id));
            verify(saleCardRepository).deleteById(id);
        }
    }

}