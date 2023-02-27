package ru.clevertec.clevertecTaskRest.dao.api;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import ru.clevertec.clevertecTaskRest.dao.entity.SaleCard;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;


@DataJpaTest
public class ISaleCardRepositoryTest {

  public static final int COUNT_OF_ENTITIES_PROVIDED_BY_LIQUIBASE = 9;

  @Autowired
  private TestEntityManager entityManager;

  @Autowired
  private ISaleCardRepository repository;

  @Test
  public void shouldFindEntitiesProvidedByLiquibase() {
    List<SaleCard> allSaleCards = repository.findAll();

    assertThat(allSaleCards).hasSize(COUNT_OF_ENTITIES_PROVIDED_BY_LIQUIBASE);
  }

  @Test
  public void shouldSaveEntity() throws InterruptedException {
    SaleCard actualSaleCard = SaleCard.Builder.create()
            .setId(COUNT_OF_ENTITIES_PROVIDED_BY_LIQUIBASE + 1L)
            .setSalePercentage(10)
            .setYear(2021L)
            .build();


    SaleCard expected = repository.save(actualSaleCard);
    assertThat(expected).isEqualTo(actualSaleCard);

  }

  @ParameterizedTest
  @MethodSource("ru.clevertec.clevertecTaskRest.dao.api.ISaleCardRepositoryTest#provideSaleCards")
  public void shouldFindAll(List<SaleCard> saleCards) {
    AtomicLong id = new AtomicLong(COUNT_OF_ENTITIES_PROVIDED_BY_LIQUIBASE);//IDs START POINT
    saleCards.forEach(product -> {
      product.setId(id.incrementAndGet());
      entityManager.persist(product);
    });

    List<SaleCard> allSaleCards = repository.findAll();

    int expectedSize = COUNT_OF_ENTITIES_PROVIDED_BY_LIQUIBASE + saleCards.size();

    assertThat(allSaleCards).hasSize(expectedSize).contains(allSaleCards.toArray(new SaleCard[0]));
  }

  @ParameterizedTest
  @MethodSource("ru.clevertec.clevertecTaskRest.dao.api.ISaleCardRepositoryTest#provideSaleCards")
  public void shouldFindById(List<SaleCard> saleCards) {
    AtomicLong id = new AtomicLong(COUNT_OF_ENTITIES_PROVIDED_BY_LIQUIBASE);//IDs START POINT
    saleCards.forEach(product -> {
      product.setId(id.incrementAndGet());
      entityManager.persist(product);
    });

    SaleCard expected = repository.findById(saleCards.get(0).getId()).get();

    assertThat(expected).isEqualTo(saleCards.get(0));
  }


  @ParameterizedTest
  @MethodSource("ru.clevertec.clevertecTaskRest.dao.api.ISaleCardRepositoryTest#provideSaleCards")
  public void shouldUpdateById(List<SaleCard> saleCards) {
    AtomicLong id = new AtomicLong(COUNT_OF_ENTITIES_PROVIDED_BY_LIQUIBASE);//IDs START POINT
    saleCards.forEach(product -> {
      product.setId(id.incrementAndGet());
      entityManager.persist(product);
    });

    SaleCard updatedDataProduct = saleCards.get(1);

    SaleCard saleCardFromDb = repository.findById(saleCards.get(2).getId()).get();

    saleCardFromDb.setSalePercentage(updatedDataProduct.getSalePercentage());
    saleCardFromDb.setYear(updatedDataProduct.getYear());

    repository.save(saleCardFromDb);

    SaleCard checkUpdatedSaleCard = repository.findById(saleCards.get(2).getId()).get();

    assertAll(
            () -> assertThat(checkUpdatedSaleCard.getId()).isEqualTo(saleCards.get(2).getId()),
            () -> assertThat(checkUpdatedSaleCard.getSalePercentage()).isEqualTo(updatedDataProduct.getSalePercentage()),
            () -> assertThat(checkUpdatedSaleCard.getYear()).isEqualTo(updatedDataProduct.getYear())
    );
  }

  @ParameterizedTest
  @MethodSource("ru.clevertec.clevertecTaskRest.dao.api.ISaleCardRepositoryTest#provideSaleCards")
  public void shouldDeleteById(List<SaleCard> saleCards) {
    AtomicLong id = new AtomicLong(COUNT_OF_ENTITIES_PROVIDED_BY_LIQUIBASE);//IDs START POINT
    saleCards.forEach(product -> {
      product.setId(id.incrementAndGet());
      entityManager.persist(product);
    });

    Long idToDelete = saleCards.get(1).getId();

    repository.deleteById(idToDelete);

    assertThat(repository.findById(idToDelete)).isNotPresent();
  }

  @ParameterizedTest
  @MethodSource("ru.clevertec.clevertecTaskRest.dao.api.ISaleCardRepositoryTest#provideSaleCards")
  public void shouldDeleteAll(List<SaleCard> saleCards) {
    AtomicLong id = new AtomicLong(COUNT_OF_ENTITIES_PROVIDED_BY_LIQUIBASE);//IDs START POINT
    saleCards.forEach(product -> {
      product.setId(id.incrementAndGet());
      entityManager.persist(product);
    });

    repository.deleteAll();

    assertThat(repository.findAll()).isEmpty();
  }

  static Stream<Arguments> provideSaleCards(){
    return Stream.of(
            Arguments.of(Arrays.asList(
                    SaleCard.Builder.create()
                            .setId(10L)
                            .setSalePercentage(10)
                            .setYear(2021L)
                            .build(),
                    SaleCard.Builder.create()
                            .setId(11L)
                            .setSalePercentage(10)
                            .setYear(2021L)
                            .build(),
                    SaleCard.Builder.create()
                            .setId(12L)
                            .setSalePercentage(10)
                            .setYear(2021L)
                            .build(),
                    SaleCard.Builder.create()
                            .setId(13L)
                            .setSalePercentage(10)
                            .setYear(2021L)
                            .build()
            )));
  }
}