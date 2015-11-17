CREATE TABLE revinfo (
	rev INT AUTO_INCREMENT,
	revtstmp BIGINT,
	PRIMARY KEY (rev)
	);
 
CREATE TABLE task_aud (
	id BIGINT NOT NULL,
	rev INT NOT NULL,
	revtype TINYINT,
	created_at TIMESTAMP,
	created_by BIGINT,
	updated_at TIMESTAMP,
	updated_by BIGINT,
	deleted boolean,
	PRIMARY KEY (
		id,
		rev
		)
	)