package ru.adaliza.chatbot.dao;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;

import ru.adaliza.chatbot.model.Product;

import java.util.List;

public interface ProductRepository extends CrudRepository<Product, Long> {

    @Query(
            "select * from products pr "
                    + "join shopping_lists sl on pr.shopping_list_id = sl.id "
                    + "where sl.user_id = :userId "
                    + "order by pr.product_name")
    List<Product> findAllByUserId(long userId);
}
