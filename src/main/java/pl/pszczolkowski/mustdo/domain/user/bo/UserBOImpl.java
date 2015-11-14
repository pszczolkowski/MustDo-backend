package pl.pszczolkowski.mustdo.domain.user.bo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import pl.pszczolkowski.mustdo.domain.user.dto.UserSnapshot;
import pl.pszczolkowski.mustdo.domain.user.entity.User;
import pl.pszczolkowski.mustdo.domain.user.repo.UserRepository;
import pl.pszczolkowski.mustdo.service.userPasswordEncoder.PasswordEncodingService;
import pl.pszczolkowski.mustdo.sharedkernel.annotations.BussinesObject;

@BussinesObject
public class UserBOImpl
   implements UserBO {

   private static final Logger LOGGER = LoggerFactory.getLogger(UserBOImpl.class);

   private final UserRepository userRepository;
   private final PasswordEncodingService passwordEncodingService;

   @Autowired
   public UserBOImpl(UserRepository userRepository, PasswordEncodingService passwordEncodingService) {
      this.userRepository = userRepository;
      this.passwordEncodingService = passwordEncodingService;
   }

   @Override
   public UserSnapshot register(String login, String password) {
      User user = new User(login, passwordEncodingService.encode(password));
      user = userRepository.save(user);

      LOGGER.info("Registered new User with login <{}>", login);

      return user.toSnapshot();
   }

   @Override
   public void changePassword(Long id, String password) {
      User user = userRepository.findOne(id);
      if (user == null) {
         return;
      }
      UserSnapshot userSnapshot = user.toSnapshot();
      if (!passwordEncodingService.isMatch(password, userSnapshot.getPassword())) {
         user.changePassword(passwordEncodingService.encode(password));
         userRepository.save(user);
      }
      LOGGER.info("Changed password for user with login <{}>", user.toSnapshot().getLogin());
   }

}
