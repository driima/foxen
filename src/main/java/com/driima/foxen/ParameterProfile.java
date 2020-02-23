package com.driima.foxen;

import lombok.Builder;
import lombok.Getter;
import lombok.Singular;

import java.lang.annotation.Annotation;
import java.lang.reflect.Parameter;
import java.util.List;

@Getter
@Builder
public class ParameterProfile {
    private final Parameter parameter;
    private final Class<?> type;
    @Singular
    private final List<Annotation> annotations;
    @Singular
    private final List<Class<?>> parameterGenericTypes;
    private final boolean optional;
}