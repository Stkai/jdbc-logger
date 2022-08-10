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

/**
 * @author St.kai
 * @version 1.0
 * @date 2022-08-10 20:08
 */
object SqlUtils {
    fun parseSql(sql: String, params: Array<String?>): String {
        if (params.isEmpty()) {
            return sql
        }
        var resultSql = ""
        var i = 0
        var count = 0
        sql.forEach {
            if (it == '\'') {
                count++
            }
            if (it == '?' && count % 2 == 0) {
                resultSql += params[i].orEmpty()
                i++
                return@forEach
            }
            resultSql += it
        }
        return resultSql
    }
}
