package de.openmail.controller

import de.openmail.config.UserService
import de.openmail.model.Credential
import de.openmail.model.User
import de.openmail.model.server.AbstractServer
import de.openmail.model.server.CustomMailCow
import de.openmail.repository.CredentialRepository
import de.openmail.repository.ImapFolderMappingRepository
import de.openmail.repository.ServerRepository
import de.openmail.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import java.lang.Exception

@RestController
@RequestMapping("server")
class ServerController(
    @Autowired val userService: UserService,
    @Autowired val userRepository: UserRepository,
    @Autowired val credentialRepository: CredentialRepository,
    @Autowired val serverRepository: ServerRepository

) {
    @PostMapping("/{serverName}")
    fun addServer(@PathVariable serverName: String) {
        when (serverName) {
            "CustomMailCow" -> saveServerAndUser(CustomMailCow())
            "" -> print("...")
        }
    }

    @GetMapping
    fun getAllServer(): MutableList<AbstractServer> {
        return serverRepository.findAll()
    }

    @PutMapping("/{serverId}")
    fun addCredential(@PathVariable serverId: Int, @RequestBody credential: Credential) {
        var server = serverRepository.findById(serverId).get()
        for (cred in server.credentials) {
            if (cred.label == credential.label) {
                throw Exception("Label taken")
            }
        }
        credentialRepository.save(credential)
        server.addCredential(credential)
        serverRepository.save(server)
    }

    private fun saveServerAndUser(server: AbstractServer) {
        serverRepository.save(server)
        var user: User = userService.currentUser().get()
        user.addServer(server)
        userRepository.save(user)
    }
}
