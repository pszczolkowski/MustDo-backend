package pl.pszczolkowski.mustdo.domain.user.finder;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;

import pl.pszczolkowski.mustdo.domain.user.dto.UserSnapshot;
import pl.pszczolkowski.mustdo.domain.user.entity.User;
import pl.pszczolkowski.mustdo.domain.user.repo.UserRepository;
import pl.pszczolkowski.mustdo.sharedkernel.annotations.Finder;

@Finder
public class UserSnapshotFinderImpl
   implements UserSnapshotFinder {

   private final UserRepository userRepository;

   @Autowired
   public UserSnapshotFinderImpl(UserRepository userRepository) {
      this.userRepository = userRepository;
   }

   @Override
   public UserSnapshot findById(Long id) {
      User user = userRepository.findOne(id);

      return user == null ? null : user.toSnapshot();
   }

   @Override
   public UserSnapshot findByLogin(String login) {
      User user = userRepository.findOneByLoginIgnoreCase(login);

      return user == null ? null : user.toSnapshot();
   }

   @Override
   public Map<Long, UserSnapshot> findAllAsMap(Set<Long> ids) {
      List<User> employees = userRepository.findAll(ids);

      return employees
            .stream()
            .map(User::toSnapshot)
            .collect(Collectors.toMap(UserSnapshot::getId, s->s));

   }

}
