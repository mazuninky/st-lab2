CREATE OR REPLACE FUNCTION task_state_trigger() RETURNS TRIGGER AS
$task_state_trigger$
BEGIN
    INSERT INTO task_state VALUES (new.id, 1);
    RETURN NEW;
END;
$task_state_trigger$ LANGUAGE plpgsql;

CREATE TRIGGER task_state_trigger
    AFTER INSERT
    ON task
    FOR EACH ROW
EXECUTE PROCEDURE task_state_trigger();


CREATE OR REPLACE FUNCTION remove_task_tag_trigger() RETURNS TRIGGER AS
$task_state_trigger$
DECLARE
    exists BOOLEAN := false;
BEGIN
    exists := EXISTS(SELECT * FROM task_tag where tag_id = old.tag_id);
    if not exists then
        DELETE FROM tag WHERE id = old.tag_id;
    end if;
    RETURN old;
END;
$task_state_trigger$ LANGUAGE plpgsql;

CREATE TRIGGER remove_task_tag_trigger
    AFTER DELETE
    ON task_tag
    FOR EACH ROW
EXECUTE PROCEDURE remove_task_tag_trigger();
