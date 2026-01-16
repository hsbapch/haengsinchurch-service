CREATE TABLE board
(
    id           BIGSERIAL PRIMARY KEY,

    article_type VARCHAR     NOT NULL,
    title        VARCHAR     NOT NULL,
    content      TEXT NULL,

    youtube_url  VARCHAR NULL,

    created_at   TIMESTAMPTZ NOT NULL DEFAULT now(),
    created_by   VARCHAR     NOT NULL,

    updated_at   TIMESTAMPTZ,
    updated_by   VARCHAR,

    is_deleted   BOOLEAN     NOT NULL DEFAULT FALSE
);

comment on column board.article_type is '게시글 타입';
comment on column board.title is '제목';
comment on column board.content is '내용';
comment on column board.youtube_url is 'Youtube URL';
comment on column board.created_at is '생성 일시';
comment on column board.created_by is '생성자';
comment on column board.updated_at is '수정 일시';
comment on column board.updated_by is '수정자';
comment on column board.is_deleted is '삭제 여부';
