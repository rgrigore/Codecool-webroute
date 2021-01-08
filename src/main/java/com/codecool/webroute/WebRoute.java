package com.codecool.webroute;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface WebRoute {
    enum Methods {
        GET, SET;

        public boolean equals(String otherMethod) {
            return this.name().equalsIgnoreCase(otherMethod);
        }
    }

    String route();
    Methods method() default Methods.GET;
}
