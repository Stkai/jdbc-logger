package com.github.stkai.jdbclogger.dao

import com.github.stkai.jdbclogger.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.UUID

/**
 * @author St.kai
 * @version 1.0
 * @date 2022-07-28 20:52
 */
@Repository
interface UserRepository : JpaRepository<User?, UUID?>
