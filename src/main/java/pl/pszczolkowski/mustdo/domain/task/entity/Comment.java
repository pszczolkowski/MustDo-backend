package pl.pszczolkowski.mustdo.domain.task.entity;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import pl.pszczolkowski.mustdo.config.persistance.converter.LocalDateTimePersistenceConverter;
import pl.pszczolkowski.mustdo.domain.task.dto.CommentSnapshot;
import pl.pszczolkowski.mustdo.domain.task.dto.TaskSnapshot;
import pl.pszczolkowski.mustdo.sharedkernel.exception.EntityInStateNewException;

@Entity
public class Comment {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@NotNull
	@ManyToOne(fetch = FetchType.EAGER)
	private Task task;
	
	@NotNull
	private Timestamp createdAt;
	
	@NotEmpty
	private String text;
	
	public Comment(Task task, String text) {
		this.task = task;
		this.text = text;
		this.createdAt = LocalDateTimePersistenceConverter.convertToDatabaseColumnValue(LocalDateTime.now());
	}
	
	public CommentSnapshot toSnapshot(){
		if(this.id == null){
			throw new EntityInStateNewException();
		}
		TaskSnapshot taskSnapshot = task.toSnapshot();
		LocalDateTime createdAtExport = LocalDateTimePersistenceConverter.convertToEntityAttributeValue(this.createdAt);
		return new CommentSnapshot(id, taskSnapshot.getId(), text, createdAtExport);
	}
}
