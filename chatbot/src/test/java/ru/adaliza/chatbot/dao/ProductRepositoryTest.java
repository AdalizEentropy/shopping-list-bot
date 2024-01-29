package ru.adaliza.chatbot.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.transaction.annotation.Transactional;
import ru.adaliza.chatbot.model.Product;

@Transactional
@DataJdbcTest
class ProductRepositoryTest {
    @Autowired private ProductRepository productRepository;

    @Test
    void findAllByUserId() {
        List<Product> allByUserId = productRepository.findAllByUserId(1L);

        assertThat(allByUserId).isNotEmpty().hasSize(2);
    }

    @Test
    void removeAllByUserId() {
        List<Product> allProducts = productRepository.findAllByUserId(1L);
        assertThat(allProducts).isNotEmpty().hasSize(2);

        productRepository.removeAllByUserId(1L);

        Iterable<Product> allProductsAfterRemoving = productRepository.findAllByUserId(1L);
        assertThat(allProductsAfterRemoving).isEmpty();
    }

    @Test
    void addProductByUserId() {
        List<Product> allProducts = productRepository.findAllByUserId(1L);
        assertThat(allProducts).isNotEmpty().hasSize(2);

        productRepository.addProductByUserId(1L, "cucumber", "Cat2");

        List<Product> allProductsAfterAdding = productRepository.findAllByUserId(1L);
        assertThat(allProductsAfterAdding).isNotEmpty().hasSize(3);
    }

    @Test
    void countByUserId() {
        int count = productRepository.countByUserId(1L);

        assertEquals(2, count);
    }
}
