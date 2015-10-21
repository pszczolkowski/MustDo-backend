package pl.pszczolkowski.mustdo.sharedkernel.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Retention(RetentionPolicy.RUNTIME)
@Transactional
@Target(ElementType.TYPE)
public @interface BussinesObject {

   String value() default "";
   
}
