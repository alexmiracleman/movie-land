create table if not exists tokens
(
    id                      integer                                            not null
    primary key,
    token                   varchar(255)                                       not null,
    user_id                 integer
    constraint fk_tokens_on_user_id
    references users,
    is_logged_out           boolean                                            not null
    );
