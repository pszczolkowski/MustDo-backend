
CREATE TABLE file (
	id BIGINT NOT NULL auto_increment,
	content LONGBLOB,
	created_at TIMESTAMP NOT NULL,
	extension VARCHAR(255) NOT NULL,
	NAME VARCHAR(255) NOT NULL,
    task_id BIGINT NOT NULL,
	PRIMARY KEY (id)
	);