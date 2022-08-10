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

package com.github.stkai.jdbclogger.wrapper

import java.util.concurrent.TimeUnit

/**
 * @author St.kai
 * @version 1.0
 * @date 2022-08-10 21:53
 */
class StopWatch {
    private var startTimeNanos: Long = 0
    private var totalTimeNanos: Long = 0

    init {
        startTimeNanos = System.nanoTime()
    }

    fun stop() {
        val lastTime = System.nanoTime()
        this.totalTimeNanos = lastTime - startTimeNanos
    }

    fun getTotalTimeMillis(): Long {
        return TimeUnit.NANOSECONDS.toMillis(totalTimeNanos)
    }
}
