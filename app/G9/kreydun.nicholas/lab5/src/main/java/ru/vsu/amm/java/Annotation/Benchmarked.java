package ru.vsu.amm.java.Annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)  // Сохраняем аннотацию во время выполнения
@Target(ElementType.METHOD)
public @interface Benchmarked {
}
