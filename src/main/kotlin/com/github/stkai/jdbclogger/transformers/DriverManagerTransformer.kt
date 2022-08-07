package com.github.stkai.jdbclogger.transformers

import com.github.stkai.jdbclogger.jdbc.DatabaseDriver
import com.github.stkai.jdbclogger.wrapper.ConnectionWrapper
import javassist.CannotCompileException
import javassist.ClassPool
import javassist.CtClass
import javassist.CtMethod
import javassist.CtNewMethod
import javassist.NotFoundException
import org.slf4j.LoggerFactory
import java.io.ByteArrayInputStream
import java.lang.instrument.ClassFileTransformer
import java.security.ProtectionDomain
import java.util.Properties

/**
 * @author St.kai
 * @version 1.0
 * @date 2022-07-16 16:30
 */
class DriverManagerTransformer : ClassFileTransformer {

    private val log = LoggerFactory.getLogger(this::class.java)

    companion object {
        private var driverSet = mutableSetOf<String>()

        /**
         * 获取驱动名称
         */
        fun getAllDriverName() {
            enumValues<DatabaseDriver>().forEach { driverSet.add(it.driverClassName) }
        }
    }

    @SuppressWarnings("NestedBlockDepth")
    override fun transform(
        loader: ClassLoader?,
        className: String?,
        classBeingRedefined: Class<*>?,
        protectionDomain: ProtectionDomain?,
        classfileBuffer: ByteArray?
    ): ByteArray? {
        val classNameWithDot = className?.replace("/", ".")
        if (driverSet.contains(classNameWithDot)) {
            try {
                log.debug("proxy Driver: {}", classNameWithDot)
                val classPool = ClassPool.getDefault()
                val newCtClass = classPool.makeClass(ByteArrayInputStream(classfileBuffer))
                val ctClassString = classPool.get(String::class.java.canonicalName)
                val ctClassProperties = classPool.get(Properties::class.java.canonicalName)
                val originMethod = getDeclaredMethods(newCtClass, arrayOf(ctClassString, ctClassProperties))
                val newCtMethod = CtNewMethod.copy(originMethod, "connect", newCtClass, null)
                val wrapperClass = ConnectionWrapper::class.java.canonicalName
                if (originMethod != null) {
                    if (newCtClass.name == originMethod.declaringClass.name) {
                        log.debug("proxy connect method, Class name: {}", newCtClass.name)
                        originMethod.name = "connect\$Impl"
                        newCtMethod.setBody(
                            """
                            {
                                return new $wrapperClass(connect${'$'}Impl($1, $2));
                            }
                            """.trimMargin()
                        )
                    } else {
                        log.debug("super class proxy:{}", newCtClass.name)
                        newCtMethod.setBody(
                            """
                            {
                                return new $wrapperClass(super.connect($1, $2));
                            }
                            """.trimIndent()
                        )
                    }
                }
                newCtClass.addMethod(newCtMethod)
                return newCtClass.toBytecode()
            } catch (e: CannotCompileException) {
                log.error(e.message, e)
            }
        }
        return classfileBuffer
    }

    /**
     * 在当前类和父类中搜索方法
     * @param ctClass
     * @param params 参数类型数组
     */
    private fun getDeclaredMethods(ctClass: CtClass, params: Array<CtClass>): CtMethod? {
        var ctClass1 = ctClass
        while (true) {
            try {
                return ctClass1.getDeclaredMethod("connect", params)
            } catch (_: NotFoundException) {
                if (Object::class.java.name != ctClass1.name) {
                    ctClass1 = ctClass.superclass
                } else {
                    return null
                }
            }
        }
    }
}
