package de.openmail.model

import de.openmail.model.server.AbstractServer
import javax.persistence.*

@Entity
@DiscriminatorColumn(name = "role")
open class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int = 0

    var username: String = ""

    var password: String = ""

    @Column(insertable = false, updatable = false)
    var role: String = ""

    @ManyToMany
    var servers: MutableList<AbstractServer> = mutableListOf()


    fun addServer(server: AbstractServer) {
        servers.add(server)
    }
}
