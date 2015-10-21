package pl.pszczolkowski.mustdo.config.web;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import pl.pszczolkowski.mustdo.web.converter.LocalDateTimeDeserializer;
import pl.pszczolkowski.mustdo.web.converter.LocalDateTimeSerializer;

@Configuration
@EnableWebMvc
public class WebMvcConfigurator
   extends WebMvcConfigurerAdapter {


   @Value("${server.contextPath}")
   private String contextPath;

   private static final int maxUploadSize = 5000000;

   /**
    * Supports FileUploads.
    */
   @Bean
   public MultipartResolver multipartResolver() {
      CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
      multipartResolver.setMaxUploadSize(maxUploadSize);
      return multipartResolver;
   }

   @Override
   public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
      Jackson2ObjectMapperBuilder builder = new Jackson2ObjectMapperBuilder();

      builder.serializerByType(LocalDateTime.class, new LocalDateTimeSerializer());
      builder.deserializerByType(LocalDateTime.class, new LocalDateTimeDeserializer());

      converters.add(new MappingJackson2HttpMessageConverter(builder.build()));
      converters.add(new ByteArrayHttpMessageConverter());
      
   }
     
}
