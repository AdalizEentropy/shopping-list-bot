--liquibase formatted sql
--changeset shoppinglist_sql:add_prod_category

ALTER TABLE products ADD category VARCHAR(100) NULL;
