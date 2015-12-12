create table comment (
id BIGINT AUTO_INCREMENT PRIMARY KEY, 
task_id BIGINT not null,
created_at TIMESTAMP NOT NULL,
`text` TEXT not null);

ALTER TABLE comment ADD CONSTRAINT FK_comment FOREIGN KEY (task_id) REFERENCES task(id);
