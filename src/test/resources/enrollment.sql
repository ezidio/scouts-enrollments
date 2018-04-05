insert into enrollment (id, intent_id, created_at) values
    (RANDOM_UUID(), (select id from intent where email = 'r.nonato@teste.com'), '2018-03-23 12:00:00');