package pl.pszczolkowski.mustdo.domain;

import java.sql.Timestamp;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;

import org.hibernate.envers.Audited;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@MappedSuperclass
@Audited
@EntityListeners(AuditingEntityListener.class)
public abstract class AbstractAuditingEntity {
	
	@CreatedBy
	@NotNull
	protected Long createdBy;
	
	@CreatedDate
	@NotNull
	protected Timestamp createdAt;
	
	@LastModifiedBy
	@NotNull
	protected Long updatedBy;
	
	@LastModifiedDate
	@NotNull
	protected Timestamp updatedAt;
	
}
