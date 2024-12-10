package ru.vsu.amm.java.Annotation;

import ru.vsu.amm.java.SizeType.Abstract.BaseSizeType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface SizeUnit {
    Class<? extends BaseSizeType> sizeType();
}
