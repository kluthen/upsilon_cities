-- init database

create table if not exists cities(
    id bigint generated always as identity primary key,
    name varchar(100),
    level integer default 1,
    size integer default 10
);

create table if not exists factories(
    id bigint generated always as identity primary key,
    name varchar(100),
    level integer default 1,
    user_id bigint default null, -- Note: external link to user needed.
    city_id bigint not null references cities(id) on delete cascade
);

create table if not exists operations(
    id bigint generated always as identity primary key,
    name varchar(100),
    factory_id bigint not null references factories(id) on delete cascade,
    inputs varchar(1000),
    outputs varchar(1000),
    productionDelay integer, -- Note: in ms
    status varchar(10),
    lastStart timestamp without time zone,
    nextEnd timestamp without time zone
);
