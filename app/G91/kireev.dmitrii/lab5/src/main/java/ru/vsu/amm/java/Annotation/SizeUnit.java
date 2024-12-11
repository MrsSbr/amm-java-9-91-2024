package ru.vsu.amm.java.Annotation;

import ru.vsu.amm.java.SizeType.SizeType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface SizeUnit {
    SizeType sizeType();
}
