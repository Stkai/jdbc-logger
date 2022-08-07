package com.github.stkai.jdbclogger.entity

import org.hibernate.Hibernate
import java.util.UUID
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Table

/**
 * @author St.kai
 * @version 1.0
 * @date 2022-07-28 20:30
 */
@Entity
@Table(name = "user_table")
data class User(

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: UUID? = null,

    var name: String? = null,

    var gender: Gender? = null,

) {
    enum class Gender {
        Male, Female
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as User

        return id != null && id == other.id
    }

    override fun hashCode(): Int = javaClass.hashCode()

    @Override
    override fun toString(): String {
        return this::class.simpleName + "(id = $id , name = $name )"
    }
}
