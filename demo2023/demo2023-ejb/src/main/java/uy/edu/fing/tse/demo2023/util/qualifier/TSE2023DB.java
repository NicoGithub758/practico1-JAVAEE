package uy.edu.fing.tse.demo2023.util.qualifier;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import javax.inject.Qualifier;

@Qualifier
@Retention(RUNTIME)
@Target({ TYPE, FIELD, METHOD })
public @interface TSE2023DB {

}
