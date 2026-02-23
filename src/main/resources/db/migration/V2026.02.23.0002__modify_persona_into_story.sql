alter table prod.story
drop constraint story_persona_id_fkey;

alter table prod.story
    add persona_image_url varchar;

comment on column prod.story.persona_image_url is '페르소나 이미지 url';

-- 데이터 마이그레이션
update story
set persona_image_url = (select image_url from persona where persona.id = story.persona_id)
where persona_image_url is null;


alter table prod.story
drop column id;