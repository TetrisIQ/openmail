package de.openmail.repository

import de.openmail.model.server.AbstractServer
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface ServerRepository : JpaRepository<AbstractServer, Int> {

    fun findByName(name: String): Optional<AbstractServer>


}
