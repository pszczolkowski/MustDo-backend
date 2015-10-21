package pl.pszczolkowski.mustdo.config.security;

import javax.servlet.Filter;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import pl.pszczolkowski.mustdo.sharedkernel.constant.Profiles;
import pl.pszczolkowski.mustdo.web.filter.RequestResponseLogFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true,
   prePostEnabled = true)
public class SecurityConfigurer {

   @Bean
   public Filter requestResponseLogFilter() {
      return new RequestResponseLogFilter();
   }

   @Bean
   @Profile(Profiles.BASIC_AUTHENTICATION)
   public AuthenticationBasic authenticationBasic() {
      return new AuthenticationBasic();
   }

}