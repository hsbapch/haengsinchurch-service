INSERT INTO users (
    user_id,
    user_password,
    created_by,
    created_at,
    updated_by,
    updated_at,
    is_deleted
) VALUES (
             'hschurch',
             'hschurch',
             NULL,
             NOW(),
             0,
             NOW(),
             FALSE
         );
