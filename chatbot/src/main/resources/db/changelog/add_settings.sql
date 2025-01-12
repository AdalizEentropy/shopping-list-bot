--liquibase formatted sql
--changeset shoppinglist_sql:add_settings

CREATE TABLE IF NOT EXISTS user_settings
(
    user_id bigint UNIQUE NOT NULL,
    app_language varchar(10) NOT NULL,
    use_category boolean NOT NULL,
    CONSTRAINT fk_settings_to_user FOREIGN KEY(user_id) REFERENCES users(id)
);