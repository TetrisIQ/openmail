package de.openmail.repository

import de.openmail.model.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.Optional

interface UserRepository : CrudRepository<User, Int> {

    fun findByUsername(username : String) : Optional<User>
}
