alter table prod.board
    alter column created_by drop not null;

alter table prod.persona
    alter column created_by drop not null;

alter table prod.story
    alter column created_by drop not null;

alter table prod.users
    alter column updated_by drop not null;

