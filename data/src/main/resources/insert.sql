INSERT into task
values (DEFAULT, 'Тестовая таска', 'Таска', '2019-05-23 12:00:00'::timestamp);

INSERT into task
values (DEFAULT, 'Тестовая таска2', 'Таска dsad', '2019-05-23 16:00:00'::timestamp);


INSERT into tag
values (DEFAULT, 'test');


INSERT into tag
values (DEFAULT, 'docker');

INSERT into task_tag
values (1, 1);

INSERT into task_tag
values (1, 2);
