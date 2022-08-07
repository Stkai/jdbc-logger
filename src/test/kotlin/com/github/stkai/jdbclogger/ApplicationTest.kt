package com.github.stkai.jdbclogger

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.PropertySource

/**
 * @author St.kai
 * @version 1.0
 * @date 2022-07-28 19:14
 */
@SpringBootApplication
@PropertySource("classpath:application.yml")
class ApplicationTest
fun main(args: Array<String>) {
    runApplication<ApplicationTest>(*args)
}
