package com.king.limit.anno;

import com.king.limit.auto.LimitAutoConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Import({LimitAutoConfiguration.class})
public @interface EnableLimit {
}
