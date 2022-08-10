package com.github.stkai.jdbclogger

import org.slf4j.LoggerFactory
import java.math.BigDecimal
import java.sql.ResultSet
import java.util.UUID

/**
 * @author St.kai
 * @version 1.0
 * @date 2022-08-03 20:54
 */
object Logger {
    private val logSql = LoggerFactory.getLogger("jdbc.logger.sql")
    private val logTotal = LoggerFactory.getLogger("jdbc.logger.total")
    private const val DEFAULT_SIZE = 10
    private var params = arrayOfNulls<String>(DEFAULT_SIZE)
    var currentSql: String = ""

    @SuppressWarnings("NestedBlockDepth")
    fun <T> execute(sql: String?, exec: () -> T): T {
        logSql.trace(sql)
        sql?.let { currentSql = sql }
        val startTimeMillis = System.currentTimeMillis()
        val resultSet = exec()
        val stopTimeMillis = System.currentTimeMillis()
        val restoreSql = restoreSql(currentSql)
        val time = stopTimeMillis - startTimeMillis
        logSql.info("Time: {} ms, SQL: {}", time, restoreSql)
        if (logTotal.isInfoEnabled) {
            if (resultSet is Int) {
                logTotal.info("Total {}", resultSet)
            }
            if (resultSet is ResultSet) {
                if (resultSet.statement.updateCount > -1) {
                    logTotal.info("Total {}", resultSet.statement.updateCount)
                } else if (resultSet.type != ResultSet.TYPE_FORWARD_ONLY) {
                    resultSet.last()
                    val count = resultSet.row
                    resultSet.beforeFirst()
                    logTotal.info("Total {}", count)
                }
            }
        }
        return resultSet
    }

    fun setParam(index: Int, x: Any?) {
        ensureCapacity(index)
        when (x) {
            is Boolean -> params[index - 1] = x.toString()
            is Short -> params[index - 1] = x.toString()
            is Int -> params[index - 1] = x.toString()
            is Long -> params[index - 1] = x.toString()
            is Float -> params[index - 1] = x.toString()
            is Double -> params[index - 1] = x.toString()
            is BigDecimal -> params[index - 1] = x.toString()
            is String -> params[index - 1] = x
            is ByteArray -> {
                try {
                    params[index - 1] = x.let { "'" + UUID.nameUUIDFromBytes(x).toString() + "'" }
                    return
                } catch (_: Exception) {
                    params[index - 1] = x.let { x.toString() }
                }
            }

            else -> params[index - 1] = "'" + x.toString() + "'"
        }
    }

    fun clearParameters() {
        this.params = arrayOfNulls(DEFAULT_SIZE)
    }

    private fun restoreSql(sql: String?): String {
        if (sql == null) {
            return ""
        }
        var resultSql = sql
        var i = 0
        resultSql.forEach {
            if (it == '?') {
                resultSql = resultSql!!.replaceFirst("?", params[i].orEmpty())
                i++
                return@forEach
            }
        }
        return resultSql as String
    }

    /**
     * 数组扩容
     */
    private fun ensureCapacity(size: Int) {
        if (params.size - 1 < size) {
            params = params.copyOf(2 * size + 1)
        }
    }
}
