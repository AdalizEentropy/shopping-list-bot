--liquibase formatted sql
--changeset shoppinglist_sql:fix_username_db

ALTER TABLE users ALTER COLUMN username DROP NOT NULL;
