package pl.pszczolkowski.mustdo.service.userPasswordEncoder;

public interface PasswordEncodingService {

   String encode(String rawPassword);

   boolean isMatch(String passwordToCheck, String encodedPassword);

}
