package de.fesere.hypermedia.cj.annotations;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Inherited
@Retention(RetentionPolicy.RUNTIME)
public @interface ItemConfig {

    NullWriteStrategy writeNull() default NullWriteStrategy.AS_NULL;
}
