package pl.pszczolkowski.mustdo.config.security;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;

final class AuthenticationCommonUtil {

   private AuthenticationCommonUtil() {
   }

   final static void configure(HttpSecurity http)
      throws Exception {
      http.authorizeRequests()
         .anyRequest().permitAll();

      http.csrf().disable();

   }
}