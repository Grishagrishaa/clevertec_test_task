package ru.clevertec.clevertecTaskRest.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
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
import ru.clevertec.clevertecTaskRest.dao.api.IProductRepository;
import ru.clevertec.clevertecTaskRest.dao.entity.Product;
import ru.clevertec.clevertecTaskRest.util.ModelMapperUtils;
import ru.clevertec.clevertecTaskRest.util.builder.ProductBuilder;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class IProductServiceImplTest {
    @Mock
    private IProductRepository productRepository;
    @Spy
    private ModelMapper mapper;

    @InjectMocks
    private IProductServiceImpl service;

    public IProductServiceImplTest() {
        mapper = ModelMapperUtils.productMapper();
    }

    @Nested
    class SaveProductTest {

        @ParameterizedTest
        @MethodSource("ru.clevertec.clevertecTaskRest.service.IProductServiceImplTest#provideProductWithNegativeCount")
        void shouldFailSaveIfProductHasNegativeCount(Product product){
            doThrow(ConstraintViolationException.class).when(productRepository).saveAndFlush(product);

            assertThatExceptionOfType(ConstraintViolationException.class)
                    .isThrownBy(() -> service.save(product));
        }

        @ParameterizedTest
        @MethodSource("ru.clevertec.clevertecTaskRest.service.IProductServiceImplTest#provideTwoProducts")
        void shouldSaveIfProductHasPositiveCount(Product product){
            doReturn(product).when(productRepository).saveAndFlush(product);

            assertThat(product).isEqualTo(service.save(product));
        }
    }

    @Nested
    class GetProductTest {
        @Test
        void shouldGetProductIfIdPresented(){
            Product actualProduct = ProductBuilder.randomValues().build();
            doReturn(Optional.of(actualProduct)).when(productRepository).findById(1L);

            assertThat(service.getById(1L)).isEqualTo(actualProduct);
        }

        @Test
        void shouldFailGetProductIfIdNonPresented(){
            doThrow(EntityNotFoundException.class).when(productRepository).findById(2L);

            assertThrows(EntityNotFoundException.class, () -> service.getById(2L));

        }

        @DisplayName("Page of products: :")
        @ParameterizedTest
        @MethodSource("ru.clevertec.clevertecTaskRest.service.IProductServiceImplTest#provideProductsAndPageable")
        void shouldReturnProductsPage(Pageable pageable, List<Product> productList){
            Page<Product> actualPage = new PageImpl<>(productList, pageable, productList.size());

            doReturn(new PageImpl<>(productList, pageable, productList.size())).when(productRepository).findAll(pageable);

            assertEquals(service.getAll(pageable), actualPage);
            verify(productRepository).findAll(pageable);
        }
    }

    @Nested
    class UpdateProductTest{

        @ParameterizedTest
        @MethodSource("ru.clevertec.clevertecTaskRest.service.IProductServiceImplTest#provideTwoProducts")
        public void whenGivenIdShouldUpdateIfFound(Product productToUpdate, Product updateDataProduct) {

            doReturn(Optional.of(productToUpdate)).when(productRepository).findById(productToUpdate.getId());
            doReturn(productToUpdate).when(productRepository).saveAndFlush(productToUpdate);

            assertThat(service.update(productToUpdate.getId(), updateDataProduct)).isEqualTo(updateDataProduct);

            verify(productRepository).findById(productToUpdate.getId());
            verify(productRepository).saveAndFlush(productToUpdate);
        }

        @Test
        public void shouldThrowExceptionWhenSaleCardDoesntExist() {
            Product productToUpdate = ProductBuilder.randomValues().build();
            Product updateDataProduct = ProductBuilder.randomValues().build();

            doReturn(Optional.empty()).when(productRepository).findById(productToUpdate.getId());

            assertThrows(EntityNotFoundException.class, () -> service.update(productToUpdate.getId(), updateDataProduct));
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


    static Stream<Arguments> provideProductWithPositiveCount(){
        return Stream.of(
                Arguments.of(ProductBuilder.randomValues().build()),
                Arguments.of(ProductBuilder.randomValues().build()),
                Arguments.of(ProductBuilder.randomValues().build()),
                Arguments.of(ProductBuilder.randomValues().build())
        );
    }

    static Stream<Arguments> provideTwoProducts(){
        return Stream.of(
                Arguments.of(ProductBuilder.randomValues().build(), ProductBuilder.randomValues().build()),
                Arguments.of(ProductBuilder.randomValues().build(), ProductBuilder.randomValues().build()),
                Arguments.of(ProductBuilder.randomValues().build(), ProductBuilder.randomValues().build()),
                Arguments.of(ProductBuilder.randomValues().build(), ProductBuilder.randomValues().build())
        );
    }

    static Stream<Arguments> provideProductWithNegativeCount(){
        return provideProductWithPositiveCount()
                .map(Arguments::get)
                .map(array -> (Product) array[0])
                .peek(product -> product.setCount(product.getCount() * -1))
                .map(Arguments::of);
    }

    static Stream<Arguments> provideProductsAndPageable() {
        List<Product> productList = provideProductWithPositiveCount()
                .map(Arguments::get)
                .map(array -> (Product) array[0])
                .toList();
        return Stream.of(
                Arguments.of(PageRequest.of(1,5), productList),
                Arguments.of(PageRequest.of(2, 2), productList),
                Arguments.of(PageRequest.of(3, 1), productList));
    }
}
