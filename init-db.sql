drop table instr_offer if exists;
drop table instrument if exists;
drop table offering if exists;
drop table customer if exists;
drop table transaction if exists;

create table customer (
    id varchar(80) not null,
    name varchar(80) not null,
    family varchar(80) not null,
    balance bigint not null,
    primary key (id)
);

create table instrument (
    customer_id varchar(80) not null,
    symbol varchar(100) not null,
    quantity bigint not null,
    primary key (customer_id,symbol),
    constraint customer_id_fk foreign key(customer_id) references customer(id) on delete cascade
);

create table offering(
    db_id bigint IDENTITY PRIMARY KEY,
    customer_id varchar(80) not null,
    price bigint not null,
    quantity bigint not null,
    type varchar(30) not null,
    kind integer not null,
    constraint custome_id_fk foreign key(customer_id) references customer(id) on delete cascade
);

create table instr_offer(
    instr_cust_id varchar(80) not null,
    instr_symbol varchar(100) not null,
    offer_id bigint not null,
    primary key (instr_cust_id, instr_symbol, offer_id ),
    constraint instr_fk foreign key(instr_cust_id, instr_symbol) references instrument(customer_id,symbol) on delete cascade,
    constraint offer_id_fk foreign key(offer_id) references offering(db_id) on delete cascade
);

create table transaction(
    tr_id bigint IDENTITY PRIMARY KEY,
    buyer varchar(80) not null,
    seller varchar(80) not null,
    instrument varchar(80) not null,
    typeOfTrade varchar(80) not null,
    quantity varchar(80) not null,
    price varchar(80) not null,
    time timestamp default now
);

insert into customer values ('1', 'admin','password',0);
insert into instrument values ('1', 'RANA', 200);
insert into offering (customer_id, price, quantity, type, kind) values ('1', 100, 20, 'GTC', 0);
insert into instr_offer values ('1', 'RANA', 0);
insert into transaction (buyer, seller, instrument, typeOfTrade, quantity, price) values ('11', '1', 'BENZ', 'GTC', '22', '200');
