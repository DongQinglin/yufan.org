package me.dongqinglin.flina.rest.middleware.role;

import me.dongqinglin.flina.rest.data.schema.entity.User;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface HasRole {
    User.RoleEnum value() default User.RoleEnum.VISITOR;
}
