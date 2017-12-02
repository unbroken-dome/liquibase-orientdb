package org.unbrokendome.liquibase.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;


public class OneOfEnumConstraintValidator implements ConstraintValidator<OneOfEnum, String> {

    private OneOfEnum constraintAnnotation;

    @Override
    public void initialize(OneOfEnum constraintAnnotation) {
        this.constraintAnnotation = constraintAnnotation;
    }


    @Override
    @SuppressWarnings("unchecked")
    public boolean isValid(String value, ConstraintValidatorContext context) {

        if (value == null) {
            return true;
        }

        Class<? extends Enum> enumClass = constraintAnnotation.value();

        String key = constraintAnnotation.ignoreCase() ? value.toUpperCase() : value;
        try {
            Enum.valueOf(enumClass, key);
            return true;

        } catch (IllegalArgumentException ex) {
            return false;
        }
    }
}
