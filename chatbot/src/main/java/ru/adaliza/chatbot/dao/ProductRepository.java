package ru.adaliza.chatbot.dao;

import java.util.List;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import ru.adaliza.chatbot.model.Product;

public interface ProductRepository extends CrudRepository<Product, Long> {

    @Query(
            "select pr.* from products pr "
                    + "join shopping_lists sl on pr.shopping_list_id = sl.id "
                    + "where sl.user_id = :userId "
                    + "order by pr.category, pr.product_name")
    List<Product> findAllByUserId(long userId);

    @Modifying
    @Query(
            "delete from products "
                    + "where shopping_list_id = "
                    + "(select id from shopping_lists where user_id = :userId)")
    void removeAllByUserId(long userId);

    @Modifying
    @Query(
            "insert into products (product_name, shopping_list_id, category) "
                    + "select :productName, id, :category from shopping_lists where user_id = :userId")
    void addProductByUserId(Long userId, String productName, String category);

    @Query(
            "select count(pr.id) from products pr "
                    + "join shopping_lists sl on pr.shopping_list_id = sl.id "
                    + "where sl.user_id = :userId")
    int countByUserId(long userId);
}
