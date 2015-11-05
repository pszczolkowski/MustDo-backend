package pl.pszczolkowski.mustdo.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import pl.pszczolkowski.mustdo.domain.user.dto.UserSnapshot;
import pl.pszczolkowski.mustdo.domain.user.finder.UserSnapshotFinder;

@Component("userDetailsService")
public class UserDetailsService
   implements org.springframework.security.core.userdetails.UserDetailsService {

   private final Logger log = LoggerFactory.getLogger(UserDetailsService.class);

   private final UserSnapshotFinder userSnapshotFinder;

   @Autowired
   public UserDetailsService(UserSnapshotFinder userSnapshotFinder) {
      this.userSnapshotFinder = userSnapshotFinder;
   }

   @Override
   @Transactional
   public UserDetails loadUserByUsername(final String login) {
      log.debug("Authenticating {}", login);

      UserSnapshot userSnapshot = userSnapshotFinder.findByLogin(login);

      if (userSnapshot == null) {
         throw new UsernameNotFoundException("User with login " + login + " was not found in database");
      }

      return new CustomUserDetails(userSnapshot.getId(), userSnapshot.getLogin(), userSnapshot.getPassword());

   }
}
