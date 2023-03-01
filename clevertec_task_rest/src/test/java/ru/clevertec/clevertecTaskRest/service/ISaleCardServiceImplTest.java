package ru.clevertec.clevertecTaskRest.service;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import ru.clevertec.clevertecTaskRest.dao.api.ISaleCardRepository;
import ru.clevertec.clevertecTaskRest.dao.entity.SaleCard;
import ru.clevertec.clevertecTaskRest.util.ModelMapperUtils;
import ru.clevertec.clevertecTaskRest.util.builder.SaleCardBuilder;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ISaleCardServiceImplTest {
    @Mock
    private ISaleCardRepository saleCardRepository;
    @Spy
    private ModelMapper mapper;

    @InjectMocks
    private ISaleCardServiceImpl service;

    public ISaleCardServiceImplTest() {
        mapper = ModelMapperUtils.saleCardMapper();
    }

    @Nested
    class SaveSaleCardTest {
        @ParameterizedTest
        @MethodSource("ru.clevertec.clevertecTaskRest.service.ISaleCardServiceImplTest#provideSaleCards")
        void shouldSaveIfSaleCardProvided(SaleCard saleCard){
            doReturn(saleCard).when(saleCardRepository).saveAndFlush(saleCard);

            assertThat(service.save(saleCard)).isEqualTo(saleCard);
        }
    }

    @Nested
    class GetSaleCardTest {
        @ParameterizedTest
        @MethodSource("ru.clevertec.clevertecTaskRest.service.ISaleCardServiceImplTest#provideSaleCards")
        void shouldFailGetIfIdNonPresented(SaleCard saleCard){
            doThrow(EntityNotFoundException.class).when(saleCardRepository).findById(saleCard.getId());

            assertThrows(EntityNotFoundException.class, () -> service.getSaleCardById(saleCard.getId()));
        }

        @ParameterizedTest
        @MethodSource("ru.clevertec.clevertecTaskRest.service.ISaleCardServiceImplTest#provideSaleCards")
        void shouldGetSaleCardIfIdPresented(SaleCard saleCard){
            when(saleCardRepository.findById(saleCard.getId())).thenReturn(Optional.of(saleCard));

            assertThat(service.getSaleCardById(saleCard.getId())).isEqualTo(saleCard);//todo refactor
        }

        @ParameterizedTest
        @MethodSource("ru.clevertec.clevertecTaskRest.service.ISaleCardServiceImplTest#provideProductsAndPageable")
        void shouldReturnSaleCardsPage(Pageable pageable, List<SaleCard> saleCardList){
            Page<SaleCard> actualPage = new PageImpl<>(saleCardList, pageable, saleCardList.size());

            doReturn(new PageImpl<>(saleCardList, pageable, saleCardList.size()))
                    .when(saleCardRepository).findAll(pageable);

            assertEquals(service.getAllSaleCards(pageable), actualPage);
            verify(saleCardRepository).findAll(pageable);
        }
    }

    @Nested
    class UpdateSaleCardTest {
        @ParameterizedTest
        @MethodSource("ru.clevertec.clevertecTaskRest.service.ISaleCardServiceImplTest#provideTwoSaleCards")
        public void whenGivenIdShouldUpdateSaleCardIfFound(SaleCard saleCardToUpdate, SaleCard updateDataSaleCard) {

            doReturn(Optional.of(saleCardToUpdate)).when(saleCardRepository).findById(saleCardToUpdate.getId());
            doReturn(saleCardToUpdate).when(saleCardRepository).saveAndFlush(saleCardToUpdate);

            assertThat(service.update(saleCardToUpdate.getId(), updateDataSaleCard)).isEqualTo(updateDataSaleCard);

            verify(saleCardRepository).findById(saleCardToUpdate.getId());
            verify(saleCardRepository).saveAndFlush(updateDataSaleCard);
        }

        @ParameterizedTest
        @MethodSource("ru.clevertec.clevertecTaskRest.service.ISaleCardServiceImplTest#provideTwoSaleCards")
        public void shouldThrowExceptionWhenUserDoesntExist(SaleCard saleCardToUpdate, SaleCard updateDataSaleCard) {

            doReturn(Optional.empty()).when(saleCardRepository).findById(saleCardToUpdate.getId());

            assertThrows(EntityNotFoundException.class, () -> service.update(saleCardToUpdate.getId(), updateDataSaleCard));
        }

    }

    @Nested
    class DeleteSaleCardTest {
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

    static Stream<Arguments> provideSaleCards(){
        return Stream.of(
                Arguments.of(
                        SaleCardBuilder.randomValues().build(),
                        SaleCardBuilder.randomValues().build(),
                        SaleCardBuilder.randomValues().build()
                ));
    }

    static Stream<Arguments> provideTwoSaleCards(){
        return Stream.of(
                Arguments.of(SaleCardBuilder.randomValues().build(), SaleCardBuilder.randomValues().build()),
                Arguments.of(SaleCardBuilder.randomValues().build(), SaleCardBuilder.randomValues().build()),
                Arguments.of(SaleCardBuilder.randomValues().build(), SaleCardBuilder.randomValues().build()),
                Arguments.of(SaleCardBuilder.randomValues().build(), SaleCardBuilder.randomValues().build())
        );
    }

    static Stream<Arguments> provideProductsAndPageable() {
        List<SaleCard> saleCardList = provideSaleCards()
                .map(Arguments::get)
                .map(array -> (SaleCard) array[0])
                .toList();
        return Stream.of(
                Arguments.of(PageRequest.of(1,5), saleCardList),
                Arguments.of(PageRequest.of(2, 2), saleCardList),
                Arguments.of(PageRequest.of(3, 1), saleCardList));
    }


}