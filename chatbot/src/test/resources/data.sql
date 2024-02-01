INSERT INTO users (id, username, chat_phase, main_message_id) VALUES (1, 'user1', null, null);

INSERT INTO shopping_lists (id, user_id) VALUES (1, 1);

INSERT INTO products (product_name, shopping_list_id, category) VALUES ('prod1', 1, 'cat1');
INSERT INTO products (product_name, shopping_list_id, category) VALUES ('prod2', 1, 'cat2');