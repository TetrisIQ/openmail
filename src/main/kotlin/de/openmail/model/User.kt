package de.openmail.model

import org.hibernate.annotations.Cascade
import java.util.*
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

    @ManyToMany(cascade = arrayOf(CascadeType.ALL))
    var server: MutableList<Server> = mutableListOf()

    @ManyToMany
    var messages: MutableList<MessageObject> = mutableListOf()


    fun getServerByHost(host: String): Optional<Server> {
        for (s in server) {
            if (s.host == host) {
                return Optional.of(s)
            }
        }
        return Optional.empty<Server>()
    }

    fun getMessage(server: Server, account: Account): MutableList<MessageObject> {
        return mutableListOf<MessageObject>()
    }

    fun getAllMessages(): MutableList<MessageObject> {
        var ls: MutableList<MessageObject> = mutableListOf()
        for (s in server) {
            ls.addAll(s.getAllMessages())
        }
        return ls
    }

    fun getServerById(serverId: Int): Optional<Server> {
        for (s in server) {
            if (s.id == serverId) {
                return Optional.of(s)
            }
        }
        return Optional.empty<Server>()
    }

    fun addServer(s: Server) {
        if (server.contains(s)) {
            throw Exception("Server exists")
        }
        server.add(s)
    }

    fun delServer(s: Server) {
        server.remove(s)
    }
}
