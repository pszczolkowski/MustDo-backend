package pl.pszczolkowski.mustdo.domain.user.dto;

public class UserSnapshot {

   private final Long id;
   private final String login;
   private final String password;

   public UserSnapshot(Long id, String login, String password) {
      this.id = id;
      this.login = login;
      this.password = password;
   }

   public Long getId() {
      return id;
   }

   public String getLogin() {
      return login;
   }

   public String getPassword() {
      return password;
   }

}
