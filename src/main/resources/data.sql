INSERT INTO project (id, name) VALUES 
(NEXTVAL('project_id_seq'), 'First project'), 
(NEXTVAL('project_id_seq'), 'Second project');

INSERT INTO task (id, description, project_id, status, priority) VALUES 
(NEXTVAL('task_id_seq'), 'First task', 1, 'NEW', 'LOW'), 
(NEXTVAL('task_id_seq'), 'Second task', 1, 'IN_PROGRESS', 'MEDIUM');