package com.github.stkai.jdbclogger.test

import com.github.stkai.jdbclogger.dao.UserRepository
import com.github.stkai.jdbclogger.entity.User
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.MethodOrderer
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestMethodOrder
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.domain.PageRequest
import org.springframework.test.annotation.Rollback
import org.springframework.test.context.junit.jupiter.SpringExtension

/**
 * @author St.kai
 * @version 1.0
 * @date 2022-07-28 20:56
 */
@ExtendWith(SpringExtension::class)
@SpringBootTest
@Rollback(false)
@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
class LogTest {
    @Autowired
    private lateinit var userRepository: UserRepository

    val user = User(null, "name", User.Gender.Male)

    @Test
    @Order(1)
    fun save() {
        val user = userRepository.save(user)
        Assertions.assertEquals(user.name, this.user.name)
    }

    @Test
    @Order(2)
    fun select() {
        val page = PageRequest.of(0, 1)
        val users = userRepository.findAll(page)
        Assertions.assertEquals(this.user.name, users.content[0]?.name ?: "")
    }

    @Test
    @Order(3)
    fun update() {
        val page = PageRequest.of(0, 1)
        val userPage = userRepository.findAll(page)
        val user = userPage.content[0]
        user!!.name = "new name"
        val updateUser = userRepository.save(user)
        Assertions.assertEquals(user.name, updateUser.name)
    }

    @Test
    @Order(4)
    fun delete() {
        val users = userRepository.findAll()
        userRepository.deleteAll(users)
    }
}
