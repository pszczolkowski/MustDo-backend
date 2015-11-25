package pl.pszczolkowski.mustdo.domain.task.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.envers.Audited;

import pl.pszczolkowski.mustdo.config.persistance.converter.LocalDateTimePersistenceConverter;
import pl.pszczolkowski.mustdo.domain.AbstractAuditingEntity;
import pl.pszczolkowski.mustdo.domain.task.dto.CommentSnapshot;
import pl.pszczolkowski.mustdo.domain.task.dto.TaskSnapshot;
import pl.pszczolkowski.mustdo.sharedkernel.enversrevision.AuditComparable;
import pl.pszczolkowski.mustdo.sharedkernel.exception.EntityInStateNewException;

import static java.time.LocalDateTime.now;

@Entity
public class Task extends AbstractAuditingEntity
   implements Serializable {

   private static final long serialVersionUID = -7145279649392624281L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@NotNull
	@ManyToOne(fetch = FetchType.EAGER)
	private TasksList tasksList;

	@NotNull
	private Long boardId;

	@NotNull
	@Size(min = 2, max = 100)
	private String title;

	@Size(min = 3, max = 1000)
	private String description;

	@NotNull
	@Min(0)
	protected int position;

	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "task", orphanRemoval = true)
	@Fetch(FetchMode.SUBSELECT)
	private List<Comment> comments = new ArrayList<>();

	@NotNull
	@Audited
	@AuditComparable
	private Boolean deleted;
   
   private Long assignedTo;

   protected Task() {
   }

   public Task(TasksList tasksList, Long boardId, String title, String description, Long createdBy) {
      this.tasksList = tasksList;
      this.boardId = boardId;
      this.title = title;
      this.description = description;
      this.createdBy = createdBy;
      this.updatedBy = this.createdBy;
      this.createdAt = LocalDateTimePersistenceConverter.convertToDatabaseColumnValue(now());
      this.updatedAt = this.createdAt;
      this.position = tasksList.taskCount();
      this.deleted = false;
   }

   public void edit(String title, String description, Long updatedBy) {
      this.title = title;
      this.description = description;
      this.updatedBy = updatedBy;
      this.updatedAt = LocalDateTimePersistenceConverter.convertToDatabaseColumnValue(now());
   }
   
   public void markAsDeleted(Long updatedBy){
	   this.deleted = true;
	   this.updatedBy = updatedBy;
	   this.updatedAt = LocalDateTimePersistenceConverter.convertToDatabaseColumnValue(now());
   }
   
   void changeTasksList(TasksList tasksList, int position){
	   this.position = position;
	   this.tasksList = tasksList;
   }

   public TaskSnapshot toSnapshot() {
      if (id == null) {
         throw new EntityInStateNewException();
      }
      
      LocalDateTime createdAtExport = LocalDateTimePersistenceConverter.convertToEntityAttributeValue(this.createdAt);
      LocalDateTime updatedAtExport = LocalDateTimePersistenceConverter.convertToEntityAttributeValue(this.updatedAt);
      List<CommentSnapshot> commentsExport = Collections.unmodifiableList(this.comments.stream().map(Comment::toSnapshot).collect(Collectors.toList()));
      
      return new TaskSnapshot(id, tasksList.getId(), boardId, title, description, createdAtExport, updatedAtExport, createdBy, updatedBy, commentsExport, assignedTo);
   }
   
	public Long getId() {
		return id;
	}

   protected Long getId(){
	   return this.id;
   }
   
   void setPosition(int position){
	   this.position = position;
   }

	public void addComment(Comment comment) {
		comments.add(comment);
	}
   
   public void assignTask(Long userId){
      this.assignedTo = userId;
   }
}
