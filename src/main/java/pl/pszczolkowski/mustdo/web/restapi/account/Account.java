package pl.pszczolkowski.mustdo.web.restapi.account;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import pl.pszczolkowski.mustdo.domain.user.dto.UserSnapshot;

@ApiModel
public class Account {

   private final Long id;
   private final String login;

   public Account(UserSnapshot userSnapshot) {
      this.id = userSnapshot.getId();
      this.login = userSnapshot.getLogin();
   }

   @ApiModelProperty(value = "Account unique identifier")
   public Long getId() {
      return id;
   }

   @ApiModelProperty(value = "Username for account")
   public String getLogin() {
      return login;
   }

}
