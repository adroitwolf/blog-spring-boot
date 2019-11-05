package run.app.security.annotation;

import java.lang.annotation.*;

/**
 * Created with IntelliJ IDEA.
 * User: WHOAMI
 * Time: 2019 2019/7/30 9:26
 * Description: 日志操作
 */

@Documented
@Target({ElementType.METHOD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface MethodLog {


}
