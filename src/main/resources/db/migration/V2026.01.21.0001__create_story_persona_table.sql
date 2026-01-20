CREATE TABLE persona
(
    id           BIGSERIAL PRIMARY KEY,
    title        VARCHAR     NOT NULL,
    image_url     VARCHAR     NOT NULL,

    created_at   TIMESTAMPTZ NOT NULL DEFAULT now(),
    created_by   VARCHAR     NOT NULL,

    updated_at   TIMESTAMPTZ,
    updated_by   VARCHAR,

    is_deleted   BOOLEAN     NOT NULL DEFAULT FALSE
);

comment on column persona.title is '페르소나 제목';
comment on column persona.image_url is '페르소나 이미지 URL';
comment on column persona.created_at is '생성 일시';
comment on column persona.created_by is '생성자';
comment on column persona.updated_at is '수정 일시';
comment on column persona.updated_by is '수정자';
comment on column persona.is_deleted is '삭제 여부';


CREATE TABLE story
(
    id           BIGSERIAL PRIMARY KEY,
    persona_id   BIGINT      NOT NULL REFERENCES persona(id),
    title        VARCHAR     NOT NULL,
    content      TEXT        NULL,

    created_at   TIMESTAMPTZ NOT NULL DEFAULT now(),
    created_by   VARCHAR     NOT NULL,

    updated_at   TIMESTAMPTZ,
    updated_by   VARCHAR,

    is_deleted   BOOLEAN     NOT NULL DEFAULT FALSE
);

comment on column story.persona_id is '페르소나 ID';
comment on column story.title is '스토리 제목';
comment on column story.content is '스토리 내용';
comment on column story.created_at is '생성 일시';
comment on column story.created_by is '생성자';
comment on column story.updated_at is '수정 일시';
comment on column story.updated_by is '수정자';
comment on column story.is_deleted is '삭제 여부';
