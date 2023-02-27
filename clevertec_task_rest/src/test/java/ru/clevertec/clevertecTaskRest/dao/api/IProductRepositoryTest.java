package ru.clevertec.clevertecTaskRest.dao.api;

import jakarta.persistence.GeneratedValue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import ru.clevertec.clevertecTaskRest.dao.entity.Product;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;


@DataJpaTest
public class IProductRepositoryTest {

  public static final int COUNT_OF_ENTITIES_PROVIDED_BY_LIQUIBASE = 10;

  @Autowired
  private TestEntityManager entityManager;

  @Autowired
  private IProductRepository repository;

  @Test
  public void shouldFindEntitiesProvidedByLiquibase() {
    List<Product> allProducts = repository.findAll();

    assertThat(allProducts).hasSize(COUNT_OF_ENTITIES_PROVIDED_BY_LIQUIBASE);
  }

  @Test
  public void shouldSaveProduct() throws InterruptedException {
    Product actualProduct = Product.Builder.create()
                                            .setId(COUNT_OF_ENTITIES_PROVIDED_BY_LIQUIBASE + 1L)
                                            .setName("TestProduct2")
                                            .setManufacturer("Porsche")
                                            .setCost(21.0)
                                            .setWeight(123)
                                            .setExpirationDate(LocalDateTime.MIN)
                                            .setCount(3L)
                                            .build();


    Product expected = repository.save(actualProduct);
    assertThat(expected).isEqualTo(actualProduct);

  }

  @ParameterizedTest
  @MethodSource("ru.clevertec.clevertecTaskRest.dao.api.IProductRepositoryTest#provideProductsWithPositiveCount")
  public void shouldFindAll(List<Product> products) {
    AtomicLong id = new AtomicLong(COUNT_OF_ENTITIES_PROVIDED_BY_LIQUIBASE);//IDs START POINT
    products.forEach(product -> {
      product.setId(id.incrementAndGet());
      entityManager.persist(product);
    });

    List<Product> all = repository.findAll();

    assertThat(all).hasSize(COUNT_OF_ENTITIES_PROVIDED_BY_LIQUIBASE + products.size()).contains(products.toArray(new Product[0]));
  }

  @ParameterizedTest
  @MethodSource("ru.clevertec.clevertecTaskRest.dao.api.IProductRepositoryTest#provideProductsWithPositiveCount")
  public void shouldFindById(List<Product> products) {
    AtomicLong id = new AtomicLong(COUNT_OF_ENTITIES_PROVIDED_BY_LIQUIBASE);//IDs START POINT
    products.forEach(product -> {
      product.setId(id.incrementAndGet());
      entityManager.persist(product);
    });

    Product expectedProduct = repository.findById(products.get(0).getId()).get();

    assertThat(expectedProduct).isEqualTo(products.get(0));
  }


  @ParameterizedTest
  @MethodSource("ru.clevertec.clevertecTaskRest.dao.api.IProductRepositoryTest#provideProductsWithPositiveCount")
  public void shouldUpdateById(List<Product> products) {
    AtomicLong id = new AtomicLong(COUNT_OF_ENTITIES_PROVIDED_BY_LIQUIBASE);//IDs START POINT
    products.forEach(product -> {//setId, Save
      product.setId(id.incrementAndGet());
      entityManager.persist(product);
    });

    Product updateDataproduct = products.get(1);//from method source

    Product productFromDb = repository.findById(products.get(2).getId()).get();//from db

    productFromDb.setName(updateDataproduct.getName());
    productFromDb.setManufacturer(updateDataproduct.getManufacturer());
    productFromDb.setCost(updateDataproduct.getCost());
    productFromDb.setWeight(updateDataproduct.getWeight());
    productFromDb.setExpirationDate(updateDataproduct.getExpirationDate());
    productFromDb.setCount(updateDataproduct.getCount());

    repository.save(productFromDb);//save updated


    Product checkUpdatedProduct = repository.findById(products.get(2).getId()).get();//updated from db

    assertAll(
            () -> assertThat(checkUpdatedProduct.getId()).isEqualTo(products.get(2).getId()),
            () -> assertThat(checkUpdatedProduct.getName()).isEqualTo(updateDataproduct.getName()),
            () -> assertThat(checkUpdatedProduct.getManufacturer()).isEqualTo(updateDataproduct.getManufacturer()),
            () -> assertThat(checkUpdatedProduct.getExpirationDate()).isEqualTo(updateDataproduct.getExpirationDate()),
            () -> assertThat(checkUpdatedProduct.getCount()).isEqualTo(updateDataproduct.getCount()),
            () -> assertThat(checkUpdatedProduct.getExpirationDate()).isEqualTo(updateDataproduct.getExpirationDate()),
            () -> assertThat(checkUpdatedProduct.getCost()).isEqualTo(updateDataproduct.getCost())
    );
  }

  @ParameterizedTest
  @MethodSource("ru.clevertec.clevertecTaskRest.dao.api.IProductRepositoryTest#provideProductsWithPositiveCount")
  public void shouldDeleteById(List<Product> products) {
    AtomicLong id = new AtomicLong(COUNT_OF_ENTITIES_PROVIDED_BY_LIQUIBASE);//IDs START POINT
    products.forEach(product -> {//setId, Save
      product.setId(id.incrementAndGet());
      entityManager.persist(product);
    });

    Long idToDelete = products.get(1).getId();//get 1 Entity from methodSource and get id from it

    repository.deleteById(idToDelete);

    assertThat(repository.findById(idToDelete)).isNotPresent();
  }

  @ParameterizedTest
  @MethodSource("ru.clevertec.clevertecTaskRest.dao.api.IProductRepositoryTest#provideProductsWithPositiveCount")
  public void shouldDeleteAll(List<Product> products) {
    AtomicLong id = new AtomicLong(COUNT_OF_ENTITIES_PROVIDED_BY_LIQUIBASE);//IDs START POINT
    products.forEach(product -> {//setId, Save
      product.setId(id.incrementAndGet());
      entityManager.persist(product);
    });

    repository.deleteAll();

    assertThat(repository.findAll()).isEmpty();
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
}