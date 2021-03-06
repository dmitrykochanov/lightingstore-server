insert into users(login, name, email, password)
values ('user1', 'user', 'user1@test.com', '$2a$10$GZy/3PC0HhY2a3AByoYs1uxYpJ7K59R6tzgpix/VQSOvQQChMrdO.');
insert into users(login, name, email, password)
values ('admin1', 'admin', 'admin1@test.com', '$2a$10$maLtWx5w0qollVsT7HANV.dffyvjdAodXJH.AuQAp.92VdyURdhQi');

insert into roles (id, name)
values (1, 'USER');
insert into roles (id, name)
values (2, 'ADMIN');

insert into users_roles (user_id, role_id)
values (1, 1);
insert into users_roles (user_id, role_id)
values (2, 1);
insert into users_roles (user_id, role_id)
values (2, 2);

insert into categories (name)
values ('category1');
insert into categories (name)
values ('category2');
insert into categories (name)
values ('category3');

insert into products (category_id, name, description, price, count, material, color, width, height, lamp_count)
values (1, 'product1', 'description1', 5400, 100, 'material1', 'ffffff', 100, 100, 10);
insert into products (category_id, name, description, price, count)
values (1, 'product2', 'description2', 5400, 100);
insert into products (category_id, name, description, price, count, material, color, width, height, lamp_count)
values (2, 'product3', 'description3', 5400, 100, 'material3', 'ffffff', 100, 100, 10);
insert into products (category_id, name, description, price, count, material, color, width, height, lamp_count)
values (3, 'product4', 'description4', 5400, 100, 'material4', 'ffffff', 100, 100, 10);

insert into cart(user_id, product_id)
values (1, 2);
insert into cart(user_id, product_id)
values (1, 3);
insert into cart(user_id, product_id)
values (2, 1);
insert into cart(user_id, product_id)
values (2, 2);

insert into favourite(user_id, product_id)
values (1, 2);
insert into favourite(user_id, product_id)
values (1, 3);
insert into favourite(user_id, product_id)
values (2, 1);
insert into favourite(user_id, product_id)
values (2, 2);

insert into orders(status, user_id, create_date)
VALUES ('CREATED', 1, 0);
insert into orders(status, user_id, create_date)
VALUES ('COMPLETED', 1, 0);
insert into orders(status, user_id, create_date)
VALUES ('CREATED', 2, 0);
insert into orders(status, user_id, create_date)
VALUES ('COMPLETED', 2, 0);

insert into products_orders(product_id, order_id)
VALUES (1, 1);
insert into products_orders(product_id, order_id)
VALUES (1, 2);
insert into products_orders(product_id, order_id)
VALUES (1, 3);
insert into products_orders(product_id, order_id)
VALUES (1, 4);
insert into products_orders(product_id, order_id)
VALUES (2, 2);
insert into products_orders(product_id, order_id)
VALUES (2, 3);