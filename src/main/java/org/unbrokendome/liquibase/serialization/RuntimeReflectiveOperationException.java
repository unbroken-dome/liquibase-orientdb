package org.unbrokendome.liquibase.serialization;

public class RuntimeReflectiveOperationException extends RuntimeException {

    public RuntimeReflectiveOperationException(ReflectiveOperationException cause) {
        super(cause.getMessage(), cause);
    }
}
