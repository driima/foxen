package com.driima.foxen;

import lombok.Builder;
import lombok.Getter;
import lombok.Singular;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.List;

@Getter
@Builder
class MethodProfile {
    private final Method method;
    private final Require require;
    @Singular private final List<ParameterProfile> parameterProfiles;
    @Singular private final List<Class<?>> parameterTypes;
    @Singular private final List<Annotation> annotations;
}
