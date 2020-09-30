package com.sinosoft.sinoep.handlerInterceptor;

import java.lang.annotation.*;

/**
 * TODO 审计日志自定义注解
 * @author 李利广
 * @Date 2019年03月18日 19:56:47
 */
@Documented
@Target(ElementType.METHOD)
@Inherited
@Retention(RetentionPolicy.RUNTIME)
public @interface LogAnnotation {

    /**
     * 操作类型（旧，以后不再使用，改用opType）
     */
    @Deprecated
    String value() default "";

    /**
     * 操作说明
     */
    String opName();

    /**
     * 操作类型（新，以后value改用opType）
     */
    OpType opType() default OpType.QUERY;
}
