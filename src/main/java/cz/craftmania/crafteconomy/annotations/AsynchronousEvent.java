package cz.craftmania.crafteconomy.annotations;

import java.lang.annotation.*;

/**
 * Event, který běží jako asynchronní.
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD, ElementType.FIELD, ElementType.CONSTRUCTOR})
public @interface AsynchronousEvent {
}
