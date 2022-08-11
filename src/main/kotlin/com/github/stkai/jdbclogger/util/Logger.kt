/*
 * Copyright (c) 2022 St.kai
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.stkai.jdbclogger.util

import com.github.stkai.jdbclogger.wrapper.StopWatch
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
    private var params = arrayOfNulls<String>(0)
    private var currentSql: String = String()

    @SuppressWarnings("NestedBlockDepth")
    fun <T> execute(sql: String?, exec: () -> T): T {
        logSql.trace(sql)
        this.setSql(sql)
        val stopWatch = StopWatch()
        val resultSet = exec()
        stopWatch.stop()
        val restoreSql = SqlUtils.parseSql(currentSql, params)
        logSql.info("Time: {} ms, SQL: {}", stopWatch.getTotalTimeMillis(), restoreSql)
        if (logTotal.isInfoEnabled) {
            if (resultSet is Int) {
                logTotal.info("Total: {}", resultSet)
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
        this.clearSql()
        return resultSet
    }

    fun setParam(index: Int, x: Any?) {
        ensureCapacity(index)
        when (x) {
            null -> params[index - 1] = "null"
            is Boolean -> params[index - 1] = x.toString()
            is Short -> params[index - 1] = x.toString()
            is Int -> params[index - 1] = x.toString()
            is Long -> params[index - 1] = x.toString()
            is Float -> params[index - 1] = x.toString()
            is Double -> params[index - 1] = x.toString()
            is BigDecimal -> params[index - 1] = x.toString()
            is ByteArray -> {
                try {
                    params[index - 1] = x.let { "'" + UUID.nameUUIDFromBytes(x).toString() + "'" }
                    return
                } catch (_: Exception) {
                    params[index - 1] = x.let { x.toString() }
                }
            }

            else -> params[index - 1] = "'$x'"
        }
    }

    fun setSql(sql: String?) {
        if (!sql.isNullOrEmpty()) {
            this.currentSql = sql
            this.params = arrayOfNulls(0)
        }
    }

    fun clearParameters() {
        params = arrayOfNulls(0)
    }

    fun clearSql() {
        this.currentSql = String()
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
