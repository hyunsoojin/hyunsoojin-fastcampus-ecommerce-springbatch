create table products
(
    product_id       varchar(255) primary key,
    seller_id        bigint       not null,
    category         varchar(255) not null,
    product_name     varchar(255) not null,
    sales_start_date date,
    sales_end_date   date,
    product_status   varchar(50),
    brand            varchar(255),
    manufacturer     varchar(255),
    sales_price      integer      not null,
    stock_quantity   integer   default 0,
    created_at       timestamp default current_timestamp,
    updated_at       timestamp default current_timestamp
);

create index idx_product_product_status on products (product_status);
create index idx_products_category on products (category);
create index idx_products_brand on products (brand);
create index idx_products_manufacturer on products (manufacturer);
create index idx_products_seller_id on products (seller_id);

create table payment
(
    payment_id     bigserial primary key,
    payment_method varchar(50) not null,
    payment_status varchar(50) not null,
    payment_date   timestamp   not null,
    amount         integer     not null,
    order_id       bigint      not null unique
);


create index idx_payment_order_id on payment (order_id);
create table orders
(
    order_id     bigserial primary key,
    order_date   timestamp   not null,
    order_status varchar(50) not null,
    customer_id  bigint
);

create index idx_orders_customer_id on orders (customer_id);
create table order_items
(
    order_item_id bigserial primary key,
    quantity      integer      not null,
    unit_price    integer      not null,
    product_id    varchar(255) not null,
    order_id      bigint       not null
);

create index idx_order_items_order_id on order_items (order_id);
create index idx_order_items_product_id on order_items (product_id);
