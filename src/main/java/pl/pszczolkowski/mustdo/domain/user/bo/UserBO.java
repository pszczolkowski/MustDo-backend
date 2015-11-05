package pl.pszczolkowski.mustdo.domain.user.bo;

import pl.pszczolkowski.mustdo.domain.user.dto.UserSnapshot;

public interface UserBO {

   UserSnapshot register(String login, String password);

   void changePassword(Long id, String password);

}
