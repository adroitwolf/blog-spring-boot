package run.app.aop.annotation;

import java.lang.annotation.*;

/**
 * Created with IntelliJ IDEA.
 * User: WHOAMI
 * Time: 2019 2019/11/29 16:23
 * Description: 增加点击量注解
 */
@Target(ElementType.METHOD)
@Documented
@Inherited
@Retention(RetentionPolicy.RUNTIME)
public @interface IncrementClickCount {
}
