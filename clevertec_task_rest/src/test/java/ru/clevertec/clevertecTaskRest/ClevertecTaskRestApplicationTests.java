package ru.clevertec.clevertecTaskRest;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ImportResource;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootTest
@ImportResource({"classpath*:application-context.xml"})
@EnableJpaAuditing
class ClevertecTaskRestApplicationTests {
}
