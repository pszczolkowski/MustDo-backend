package pl.pszczolkowski.mustdo.config.security;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import pl.pszczolkowski.mustdo.security.AjaxLogoutSuccessHandler;
import pl.pszczolkowski.mustdo.security.AuthoritiesConstants;
import pl.pszczolkowski.mustdo.security.Http401UnauthorizedEntryPoint;

@Configuration
public class OAuth2ServerConfiguration {

   @Configuration
   @EnableResourceServer
   protected static class ResourceServerConfiguration
      extends ResourceServerConfigurerAdapter {

      @Autowired
      private Http401UnauthorizedEntryPoint authenticationEntryPoint;

      @Autowired
      private AjaxLogoutSuccessHandler ajaxLogoutSuccessHandler;

      @Override
      public void configure(HttpSecurity http)
         throws Exception {
         http
            .exceptionHandling()
            .authenticationEntryPoint(authenticationEntryPoint)
            .and()
            .logout()
            .logoutUrl("/api/logout")
            .logoutSuccessHandler(ajaxLogoutSuccessHandler)
            .and()
            .csrf()
            .requireCsrfProtectionMatcher(new AntPathRequestMatcher("/oauth/authorize"))
            .disable()
            .headers()
            .frameOptions().disable()
            .and()
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()                
            .authorizeRequests()
            .antMatchers("/api/authenticate").permitAll()
            .antMatchers("/api/register").permitAll()
            .antMatchers("/api/**").authenticated();

      }
   }

   @Configuration
   @EnableAuthorizationServer
   protected static class AuthorizationServerConfiguration
      extends AuthorizationServerConfigurerAdapter {

      @Autowired
      private DataSource dataSource;

      @Autowired
      @Value("${oauth2.clientId}")
      private String clientId;

      @Autowired
      @Value("${oauth2.secret}")
      private String secret;

      @Autowired
      @Value("${oauth2.tokenValidityInSeconds}")
      private int tokenValidityInSeconds;

      @Bean
      public TokenStore tokenStore() {
         return new JdbcTokenStore(dataSource);
      }

      @Autowired
      @Qualifier("authenticationManagerBean")
      private AuthenticationManager authenticationManager;

      @Override
      public void configure(AuthorizationServerEndpointsConfigurer endpoints)
         throws Exception {

         endpoints
            .tokenStore(tokenStore())
            .authenticationManager(authenticationManager);
      }

      @Override
      public void configure(ClientDetailsServiceConfigurer clients)
         throws Exception {
         clients
            .inMemory()
            .withClient(clientId)
            .scopes("read", "write")
            .authorities(AuthoritiesConstants.USER)
            .authorizedGrantTypes("password", "refresh_token")
            .secret(secret)
            .accessTokenValiditySeconds(tokenValidityInSeconds);
      }
   }
}
