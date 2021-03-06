drop table if exists users_roles;
drop table if exists products_orders;
drop table if exists cart;
drop table if exists favourite;
drop table if exists orders;
drop table if exists users;
drop table if exists roles;
drop table if exists images;
drop table if exists products;
drop table if exists categories;

-- Auth
create table users
(
    id       bigint GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    login    varchar(255) unique not null,
    name     varchar(255)        not null,
    email    varchar(255)        not null,
    password varchar(255)        not null
);

create table roles
(
    id   bigint GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    name varchar(255) unique not null
);

create table users_roles
(
    user_id bigint,
    role_id bigint,
    constraint users_roles_pk primary key (user_id, role_id),
    constraint users_roles_user_fk foreign key (user_id) references users (id) on delete cascade,
    constraint users_roles_role_fk foreign key (role_id) references roles (id) on delete cascade
);

-- Images
create table images
(
    id   varchar(255) primary key,
    data bytea not null
);

-- Products
create table categories
(
    id   bigint GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    name varchar(255) unique not null
);

create table products
(
    id          bigint GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    category_id bigint       not null,
    name        varchar(255) not null,
    description text         not null,
    price       real         not null,
    count       int          not null,
    material    varchar(255),
    color       varchar(6),
    width       varchar(255),
    height      varchar(255),
    lamp_count  int,
    image       varchar(255),
    constraint products_categories_fk foreign key (category_id) references categories (id) on delete cascade
);

-- Cart
create table cart
(
    user_id    bigint,
    product_id bigint,
    count      bigint,
    constraint cart_pk primary key (user_id, product_id),
    constraint cart_users_fk foreign key (user_id) references users (id) on delete cascade,
    constraint cart_products_fk foreign key (product_id) references products (id) on delete cascade
);

-- Favourite
create table favourite
(
    user_id    bigint,
    product_id bigint,
    constraint favourite_pk primary key (user_id, product_id),
    constraint favourite_users_fk foreign key (user_id) references users (id) on delete cascade,
    constraint favourite_products_fk foreign key (product_id) references products (id) on delete cascade
);

-- Orders
create table orders
(
    id          bigint GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    status      varchar(20) not null,
    user_id     bigint      not null,
    create_date bigint      not null
);

create table products_orders
(
    product_id bigint,
    order_id   bigint,
    count      bigint,
    constraint products_orders_pk primary key (product_id, order_id),
    constraint products_orders_products_fk foreign key (product_id) references products (id) on delete cascade,
    constraint products_orders_orders_fk foreign key (order_id) references orders (id) on delete cascade
);