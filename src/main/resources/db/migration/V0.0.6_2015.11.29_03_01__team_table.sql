create table team (
id BIGINT AUTO_INCREMENT PRIMARY KEY, 
`name` VARCHAR(20) not null);

create table team_team_members_ids (
team_id bigint not null,
team_members_ids bigint)