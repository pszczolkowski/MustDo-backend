package pl.pszczolkowski.mustdo.domain.user.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import pl.pszczolkowski.mustdo.domain.user.entity.User;

public interface UserRepository
   extends JpaRepository<User, Long> {

   User findOneByLoginIgnoreCase(String login);

}
