package ru.clevertec.clevertecTaskRest.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;
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
import org.springframework.data.domain.Pageable;
import ru.clevertec.clevertecTaskRest.dao.api.IProductRepository;
import ru.clevertec.clevertecTaskRest.dao.entity.Product;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class IProductServiceImplTest {
    private static final Predicate<Product> isNegativeCount = testProduct -> testProduct.getCount() < 0;

    @Mock
    private IProductRepository productRepository;
    @Spy
    private ModelMapper mapper;

    @InjectMocks
    private IProductServiceImpl service;

    @Nested
    class SaveProductTest {

        @ParameterizedTest
        @NullSource
        void saveFailedIfProductIsNull(Product product){
            doThrow(NullPointerException.class).when(productRepository).saveAndFlush(isNull());


            assertThatNullPointerException()
                    .isThrownBy(() -> service.save(product));


        }

        @ParameterizedTest
        @MethodSource("ru.clevertec.clevertecTaskRest.service.IProductServiceImplTest#provideProductsWithNegativeCount")
        void saveFailedIfCountIsNotPositive(Product product){
            if(isNegativeCount.test(product)){
                doThrow(ConstraintViolationException.class).when(productRepository).saveAndFlush(product);
            }else {
                doReturn(product).when(productRepository).saveAndFlush(product);
            }

            assertThatExceptionOfType(ConstraintViolationException.class)
                    .isThrownBy(() -> service.save(product));
        }

        @ParameterizedTest
        @MethodSource("ru.clevertec.clevertecTaskRest.service.IProductServiceImplTest#provideProductsWithPositiveCount")
        void shouldSaveWithPositiveCount(Product product){
            if(isNegativeCount.test(product)){
                doThrow(ConstraintViolationException.class).when(productRepository).saveAndFlush(product);
            }else {
                doReturn(product).when(productRepository).saveAndFlush(product);
            }

            assertThat(product).isEqualTo(service.save(product));
        }
    }

    @Nested
    class GetProductTest {
        @Test
        void shouldFailGetIfIdNonPresented(){
            when(productRepository.findById(1L)).thenReturn(Optional.of(new Product()));
            when(productRepository.findById(2L)).thenReturn(Optional.of(new Product()));
            when(productRepository.findById(3L)).thenReturn(Optional.of(new Product()));

            doThrow(EntityNotFoundException.class).when(productRepository).findById(4L);
            doThrow(EntityNotFoundException.class).when(productRepository).findById(5L);

            assertAll(
                    () -> assertDoesNotThrow(() -> service.getById(1L)),
                    () -> assertDoesNotThrow(() -> service.getById(2L)),
                    () -> assertDoesNotThrow(() -> service.getById(3L)),
                    () -> assertThrows(EntityNotFoundException.class, () -> service.getById(4L)),
                    () -> assertThrows(EntityNotFoundException.class, () -> service.getById(5L))
            );
        }

        @Test
        void shouldGetIfIdPresented(){
            when(productRepository.findById(1L)).thenReturn(Optional.of(new Product()));
            when(productRepository.findById(2L)).thenReturn(Optional.of(new Product()));
            when(productRepository.findById(3L)).thenReturn(Optional.of(new Product()));

            assertAll(
                    () -> assertThat(service.getById(1L)).isInstanceOf(Product.class),
                    () -> assertThat(service.getById(2L)).isInstanceOf(Product.class),
                    () -> assertThat(service.getById(3L)).isInstanceOf(Product.class)
            );
        }

        @DisplayName("Page of products: :")
        @ParameterizedTest()
        @MethodSource("ru.clevertec.clevertecTaskRest.service.IProductServiceImplTest#provideProductsAndPageable")
        void shouldReturnProductsPage(Pageable pageable, List<Product> productList){

            Page<Product> actualPage = new PageImpl<>(productList, pageable, productList.size());

            BDDMockito.given(productRepository.findAll(pageable))
                    .willReturn(new PageImpl<>(productList, pageable, productList.size()));

            assertEquals(service.getAll(pageable), actualPage);
            verify(productRepository).findAll(pageable);
        }
    }

    @Nested
    class UpdateProductTest{
        @ParameterizedTest
        @MethodSource("ru.clevertec.clevertecTaskRest.service.IProductServiceImplTest#provideProductsAndPageable")
        public void whenGivenIdShouldUpdateIfFound(Pageable pageable, List<Product> productList) {
            Product actualProduct = productList.get(1);
            actualProduct.setId(1L);
            Product newProduct = productList.get(2);
            newProduct.setId(2L);

            mapper.getConfiguration()
                    .setAmbiguityIgnored(true)
                    .setSkipNullEnabled(false);
            mapper.addMappings(
                    new PropertyMap<Product, Product>() {
                        @Override
                        protected void configure() {
                            skip(destination.getId());
                            skip(destination.getUpdatedDate());
                            skip(destination.getCreatedDate());
                        }
                    });


            doReturn(Optional.of(actualProduct)).when(productRepository).findById(actualProduct.getId());
            doReturn(actualProduct).when(productRepository).saveAndFlush(actualProduct);

            Product expectedProduct = service.update(actualProduct.getId(), newProduct);
            assertAll(
                    () -> assertThat(expectedProduct).isEqualTo(newProduct),
                    () -> assertThat(expectedProduct.getId()).isEqualTo(actualProduct.getId())
            );

            verify(productRepository).findById(actualProduct.getId());
            verify(productRepository).saveAndFlush(newProduct);
        }

        @ParameterizedTest
        @MethodSource("ru.clevertec.clevertecTaskRest.service.IProductServiceImplTest#provideProductsAndPageable")
        public void shouldThrowExceptionWhenSaleCardDoesntExist(Pageable pageable, List<Product> productList) {
            Product actualProduct = productList.get(1);
            actualProduct.setId(1L);
            Product newProduct = productList.get(2);
            newProduct.setId(2L);

            doReturn(Optional.empty()).when(productRepository).findById(anyLong());

            assertThrows(EntityNotFoundException.class, () -> service.update(actualProduct.getId(), newProduct));
        }

    }

    @Nested
    class DeleteProductTest{
        @ParameterizedTest
        @ValueSource(longs = {1, 2, 3})
        void shouldDeleteWithGivenId(Long id){
            doNothing().when(productRepository).deleteById(id);

            service.deleteById(id);

            verify(productRepository).deleteById(id);
        }

        @ParameterizedTest
        @ValueSource(longs = {4, 5, 6})
        void shouldNotDeleteAndThrowExceptionIfIdIsNull(Long id){
            doThrow(IllegalArgumentException.class).when(productRepository).deleteById(id);

            assertThrows(IllegalArgumentException.class, () -> service.deleteById(id));
            verify(productRepository).deleteById(id);

        }
    }


    static Stream<Arguments> provideProductsWithNegativeCount(){
        return provideProductsWithPositiveCount()
                .map(Arguments::get)
                .map(array -> (Product) array[0])
                .peek(product -> product.setCount(product.getCount() * -1))
                .map(Arguments::of);
    }

    static Stream<Arguments> provideProductsAndPageable() {
        List<Product> productList = provideProductsWithPositiveCount()
                .map(Arguments::get)
                .map(array -> (Product) array[0])
                .toList();
        return Stream.of(
                Arguments.of(PageRequest.of(1,5), productList),
                Arguments.of(PageRequest.of(2, 2), productList),
                Arguments.of(PageRequest.of(3, 1), productList));
    }

    static Stream<Arguments> provideProductsWithPositiveCount(){
        return Stream.of(
                Arguments.of(Product.Builder.create()
                        .setName("TestProduct1")
                        .setManufacturer("Toyota")
                        .setCost(53.9)
                        .setWeight(355)
                        .setExpirationDate(LocalDateTime.MIN)
                        .setCount(10L)
                        .build()),
                Arguments.of(Product.Builder.create()
                        .setName("TestProduct2")
                        .setManufacturer("Porsche")
                        .setCost(21.0)
                        .setWeight(123)
                        .setExpirationDate(LocalDateTime.MIN)
                        .setCount(3L)
                        .build()),
                Arguments.of(Product.Builder.create()
                        .setName("TestProduct3")
                        .setManufacturer("BMW")
                        .setCost(28.0)
                        .setWeight(123)
                        .setExpirationDate(LocalDateTime.MIN)
                        .setCount(1238L)
                        .build()),
                Arguments.of(Product.Builder.create()
                        .setName("TestProduct4")
                        .setManufacturer("AUDI")
                        .setCost(2.0)
                        .setWeight(11)
                        .setExpirationDate(LocalDateTime.MIN)
                        .setCount(123L)
                        .build())
        );
    }
}
