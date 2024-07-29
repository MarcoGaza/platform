package cn.econets.blossom.framework.security.core.annotations;

import java.lang.annotation.*;

/**
 * Declare that users need to log in
 *
 * Why not use it {@link org.springframework.security.access.prepost.PreAuthorize} Annotation，The reason is failure，The output is authentication failure，Instead of not logged in
 *
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface PreAuthenticated {
}
