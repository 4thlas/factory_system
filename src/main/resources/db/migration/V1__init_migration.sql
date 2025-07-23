create table facilities
(
    id                     int auto_increment
        primary key,
    location               varchar(255) not null,
    manager_id             int          not null,
    product_type_id        int          not null
);

create table workers
(
    id          int auto_increment
        primary key,
    facility_id int            not null,
    role        varchar(255)   not null,
    paycheck    decimal(10, 2) not null
);

create table product_types
(
    id    int auto_increment
        primary key,
    name  varchar(255)   not null,
    value decimal(10, 2) not null
);

create table production_lines
(
    id                 int auto_increment
        primary key,
    facility_id        int    not null,
    produced_per_month bigint not null
);

create table batches
(
    id                 bigint auto_increment
        primary key,
    product_type_id    int  not null,
    production_date    DATE not null,
    production_line_id int  not null
);

create table manufactured_products
(
    id        bigint auto_increment
        primary key,
    batch_id  bigint  not null,
    defective boolean not null
);


alter table batches
    add constraint batches_product_types_id_fk
        foreign key (product_type_id) references product_types (id);

alter table facilities
    modify manager_id int null;

alter table facilities
    add constraint facilities_product_types_id_fk
        foreign key (product_type_id) references product_types (id)
            on update cascade;

alter table facilities
    add constraint facilities_workers_id_fk
        foreign key (manager_id) references workers (id)
            on update cascade on delete set null;

alter table manufactured_products
    add constraint manufactured_products_batches_id_fk
        foreign key (batch_id) references batches (id)
            on update cascade;

alter table production_lines
    add constraint production_lines_facilities_id_fk
        foreign key (facility_id) references facilities (id)
            on update cascade on delete cascade;

alter table workers
    modify facility_id int null;

alter table workers
    add constraint workers_facilities_id_fk
        foreign key (facility_id) references facilities (id)
            on update cascade on delete set null;




