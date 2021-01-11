package com.codecool.webroute;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface WebRoute {
    enum Methods {
        GET, POST;

        public boolean equals(String otherMethod) {
            return this.name().equalsIgnoreCase(otherMethod);
        }
    }

    String route();
    Methods method() default Methods.GET;
}
