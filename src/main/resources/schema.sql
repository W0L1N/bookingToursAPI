
        drop table if exists client_data cascade;

        drop table if exists destination cascade;

        drop table if exists guide_data cascade;

        drop table if exists reservation cascade;

        drop table if exists reservation_offer cascade;

        drop table if exists tour cascade;

        drop table if exists user_data cascade;

        drop table if exists user_data_roles cascade;

    create table user_data (
           id  bigserial not null,
            password varchar(255),
            username varchar(255),
            primary key (id)
        );

    create table client_data (
       id  bigserial not null,
        name varchar(255),
        passport_number varchar(255),
        surname varchar(255),
        user_data_id int8,
        primary key (id)
    );

    create table destination (
       id  bigserial not null,
        description varchar(255),
        name varchar(255),
        primary key (id)
    );

    create table guide_data (
       id  bigserial not null,
        name varchar(255),
        surname varchar(255),
        user_data_id int8,
        primary key (id)
    );

    create table reservation (
       id  bigserial not null,
        actual_price float8 not null,
        client_id int8,
        tour_id int8,
        primary key (id)
    );

    create table reservation_offer (
       id  bigserial not null,
        agreed_admin boolean not null,
        agreed_client boolean not null,
        price float8 not null,
        client_data_id int8,
        tour_id int8,
        primary key (id)
    );

    create table tour (
       id  bigserial not null,
        end_date date,
        initial_price float8 not null,
        start_date date,
        destination_id int8,
        guide_id int8,
        primary key (id)
    );



    create table user_data_roles (
       user_data_id int8 not null,
        roles varchar(255)
    );

    alter table client_data
       add constraint client_user_data
       foreign key (user_data_id)
       references user_data ;

    alter table guide_data
       add constraint guide_user_data
       foreign key (user_data_id)
       references user_data ;

    alter table reservation
       add constraint reservation_client_data
       foreign key (client_id)
       references client_data ;

    alter table reservation
       add constraint reservation_tour
       foreign key (tour_id)
       references tour;

    alter table reservation_offer
       add constraint offer_client_data
       foreign key (client_data_id)
       references client_data ;

    alter table reservation_offer
       add constraint offer_tour
       foreign key (tour_id)
       references tour;

    alter table tour
       add constraint tour_destination
       foreign key (destination_id)
       references destination;

    alter table tour
       add constraint tour_guide_data
       foreign key (guide_id)
       references guide_data;

    alter table user_data_roles
       add constraint roles_user_data
       foreign key (user_data_id)
       references user_data;

