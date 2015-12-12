package pl.pszczolkowski.mustdo.domain.board.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import pl.pszczolkowski.mustdo.domain.board.dto.BoardSnapshot;
import pl.pszczolkowski.mustdo.sharedkernel.exception.EntityInStateNewException;

@Entity
public class Board
   implements Serializable {

   private static final long serialVersionUID = -704332662201172877L;

   @Id
   @GeneratedValue(strategy = GenerationType.AUTO)
   private Long id;

   @NotNull
   @Size(min = 3, max = 100)
   @Column(unique = true)
   private String name;
   
   @NotNull
   private Long teamId;

   protected Board() {
   }

   public Board(String name, Long teamId) {
      this.name = name;
      this.teamId = teamId;
   }

   public void rename(String name) {
      this.name = name;
   }

   public BoardSnapshot toSnapshot() {
      if (id == null) {
         throw new EntityInStateNewException();
      }
      return new BoardSnapshot(id, name, teamId);
   }
}
