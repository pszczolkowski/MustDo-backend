package pl.pszczolkowski.mustdo.domain.file.entity;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import pl.pszczolkowski.mustdo.config.persistance.converter.LocalDateTimePersistenceConverter;
import pl.pszczolkowski.mustdo.domain.file.dto.FileSnapshot;
import pl.pszczolkowski.mustdo.sharedkernel.exception.EntityInStateNewException;

@Entity
public class File{

   @Id
   @GeneratedValue(strategy = GenerationType.AUTO)
   private Long id;

   @NotEmpty
   private String name;

   @NotEmpty
   private String extension;

   @NotNull
   private Timestamp createdAt;
   
   @NotNull
   private Long taskId;

   @Lob
   private byte[] content;

   protected File() {
   }

   public File(String fileName, String fileExtension, byte[] fileByte, Long taskId) {
      this.name = fileName;
      this.extension = fileExtension;
      this.taskId = taskId;
      this.content = fileByte.clone();
      this.createdAt = LocalDateTimePersistenceConverter.convertToDatabaseColumnValue(LocalDateTime.now());
   }

   public FileSnapshot toSnapshot() {
      if (id == null) {
         throw new EntityInStateNewException();
      }
      
      LocalDateTime createdAtExport = LocalDateTimePersistenceConverter.convertToEntityAttributeValue(this.createdAt);
      
      return new FileSnapshot(id, taskId, name, extension, content, createdAtExport);
   }

}
