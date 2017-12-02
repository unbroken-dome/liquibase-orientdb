package org.unbrokendome.liquibase.serialization;


import liquibase.serializer.LiquibaseSerializable;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface SerializedField {

    String name() default "";

    String namespace() default "";

    String description() default "";

    LiquibaseSerializable.SerializationType type() default LiquibaseSerializable.SerializationType.NAMED_FIELD;

    boolean embedded() default false;
}
