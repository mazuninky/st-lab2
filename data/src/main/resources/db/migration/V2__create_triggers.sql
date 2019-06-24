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
