CREATE FUNCTION add_tag(tag_value varchar)
    RETURNS INT AS
$$
DECLARE
    tag_insert_id INT     := null;
    exists        BOOLEAN := false;
BEGIN
    exists := EXISTS(SELECT id FROM tag where text = tag_value);
    IF exists THEN
        tag_insert_id = (SELECT id from tag where text = tag_value);
    else
        insert into tag VALUES (DEFAULT, tag_value) RETURNING id into tag_insert_id;
    END if;
    return tag_insert_id;
END
$$
    LANGUAGE plpgsql;

CREATE FUNCTION add_task(task_name varchar, task_description text, task_due_date timestamp, tags varchar[])
    RETURNS BOOLEAN AS
$$
DECLARE
    task_insert_id INT     := NULL;
    tag_insert_id  INT     := NULL;
    tag_item       varchar := null;
BEGIN
    INSERT INTO task values (DEFAULT, task_name, task_description, task_due_date) RETURNING id into task_insert_id;
    FOREACH tag_item IN ARRAY tags
        loop
            tag_insert_id := add_tag(tag_item);
            INSERT into task_tag VALUES (task_insert_id, tag_insert_id);
        end loop;
    return true;
END
$$
    LANGUAGE plpgsql;
