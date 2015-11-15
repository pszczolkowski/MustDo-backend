CREATE TABLE task (
	id BIGINT AUTO_INCREMENT PRIMARY KEY, 
	board_id BIGINT NOT NULL, 
	description VARCHAR(1000), 
	position INTEGER NOT NULL CHECK (position>=0), 
	title VARCHAR(100) NOT NULL, 
	tasks_list_id BIGINT NOT NULL
	);

ALTER TABLE task ADD CONSTRAINT FK_task FOREIGN KEY (tasks_list_id) REFERENCES tasks_list(id);