package pl.pszczolkowski.mustdo.config.web;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.servlet.config.annotation.CorsConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import pl.pszczolkowski.mustdo.sharedkernel.constant.Profiles;

@Profile(Profiles.DEVELOPMENT)
@Configuration
@EnableWebMvc
public class DevelopmentWebMvcConfigurator
   extends WebMvcConfigurerAdapter {

	@Override
	public void configureCors(CorsConfigurer configurer) {
		configurer.enableCors("/**");
	}
   
}
