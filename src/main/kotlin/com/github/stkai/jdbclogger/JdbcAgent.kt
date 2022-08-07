package com.github.stkai.jdbclogger

import com.github.stkai.jdbclogger.transformers.DriverManagerTransformer
import org.slf4j.LoggerFactory
import java.lang.instrument.Instrumentation

/**
 * @author St.kai
 * @version 1.0
 * @date 2022-07-16 16:39
 */
object JdbcAgent {
    private val log = LoggerFactory.getLogger(this::class.java)

    init {
        DriverManagerTransformer.getAllDriverName()
    }

    @JvmStatic
    fun agentmain(agentArgs: String?, inst: Instrumentation) {
        log.debug("JDBC Logger Agent Starting (agentmain)")
        agentArgs?.let { log.debug("agentArgs:{}", agentArgs) }
        inst.addTransformer(DriverManagerTransformer())
    }

    @JvmStatic
    fun premain(agentArgs: String?, inst: Instrumentation) {
        log.debug("JDBC Logger Agent  Starting (premain)")
        agentArgs?.let { log.debug("agentArgs:{}", agentArgs) }
        inst.addTransformer(DriverManagerTransformer())
    }
}
