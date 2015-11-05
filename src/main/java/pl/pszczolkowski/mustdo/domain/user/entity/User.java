package pl.pszczolkowski.mustdo.domain.user.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

import pl.pszczolkowski.mustdo.domain.user.dto.UserSnapshot;
import pl.pszczolkowski.mustdo.sharedkernel.exception.EntityInStateNewException;

@Entity
@Table(name = "user")
public class User
   implements Serializable {

   private static final long serialVersionUID = -7910624447570146282L;

   @Id
   @GeneratedValue(strategy = GenerationType.AUTO)
   private Long id;

   @NotNull
   @Pattern(regexp = "^[a-z0-9]*$|(anonymousUser)")
   @Size(min = 3, max = 20)
   @Column(length = 50, unique = true, nullable = false)
   private String login;

   @NotNull
   @Size(min = 60, max = 60)
   @Column(length = 60)
   private String password;

   protected User() {
   }

   public User(String login, String password) {
      this.login = login;
      this.password = password;
   }

   public void changePassword(String password) {
      this.password = password;
   }

   @Override
   public boolean equals(Object o) {
      if (this == o) {
         return true;
      }
      if (o == null || getClass() != o.getClass()) {
         return false;
      }

      User user = (User) o;

      if (!login.equals(user.login)) {
         return false;
      }

      return true;
   }

   @Override
   public int hashCode() {
      return login.hashCode();
   }

   public UserSnapshot toSnapshot() {
      if (id == null) {
         throw new EntityInStateNewException();
      }
      return new UserSnapshot(id, login, password);
   }
}
