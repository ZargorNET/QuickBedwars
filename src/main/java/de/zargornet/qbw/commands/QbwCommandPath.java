package de.zargornet.qbw.commands;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Defines a method with handles a command in class
 * Handler class: {@link QbwCommandHandler}
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface QbwCommandPath {
    String path();

    String permission();

    boolean masterPermission() default true;

    boolean async() default true;
}
