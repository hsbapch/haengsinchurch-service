CREATE TABLE users (
                       id             BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,

                       user_id        VARCHAR NOT NULL,
                       user_password  VARCHAR NOT NULL,

                       created_by     BIGINT,
                       created_at     TIMESTAMPTZ(6) NOT NULL,

                       updated_by     BIGINT NOT NULL,
                       updated_at     TIMESTAMPTZ(6) NOT NULL,

                       is_deleted     BOOLEAN NOT NULL DEFAULT FALSE,

                       CONSTRAINT user_id_uk UNIQUE (user_id)
);

comment on column users.user_id is '로그인 아이디';
comment on column users.user_password is '로그인 아이디';
