package com.fmt.github.aspectj

import android.util.Log
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect

/**
 * 使用AspectJ Aop实现方法耗时的统计
 * //拓展：权限校验、重复点击、埋点统计、权限校验、日志记录等
 */
@Aspect
class AspectJHandle {

    private val TAG = "AspectJHandle"

    @Around("execution(@com.fmt.github.aspectj.MethodTrace * *(..))")
    @Throws(Throwable::class)
    fun methodTrace(joinPoint: ProceedingJoinPoint) {
        val signature = joinPoint.signature
        //获取所在的类名
        val className = signature.declaringType.canonicalName
        //获取注解的方法名称
        val methodName = signature.name
        //计算方法的耗时
        val startTime = System.currentTimeMillis()
        joinPoint.proceed()
        Log.e(TAG, "$className:$methodName cost=${(System.currentTimeMillis() - startTime)}")
    }
}