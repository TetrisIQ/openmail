package de.openmail.controller

import de.openmail.config.UserService
import de.openmail.model.Credential
import de.openmail.model.User
import de.openmail.model.server.AbstractServer
import de.openmail.model.server.CustomMailCow
import de.openmail.model.server.ImapFolderMapping
import de.openmail.repository.CredentialRepository
import de.openmail.repository.ImapFolderMappingRepository
import de.openmail.repository.ServerRepository
import de.openmail.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.web.bind.annotation.*

@RestController
class Management(
    @Autowired val userService: UserService,
    @Autowired val userRepository: UserRepository,
    @Autowired val credentialRepository: CredentialRepository,
    @Autowired val serverRepository: ServerRepository,
    @Autowired val imapFolderMappingRepository: ImapFolderMappingRepository
) {

    @PostMapping("/server/{serverName}")
    fun addServer(@PathVariable serverName: String) {
        when (serverName) {
            "CustomMailCow" -> saveServerAndUser(CustomMailCow())
            "" -> print("...")

        }

    }

    private fun saveServerAndUser(server: AbstractServer) {
        serverRepository.save(server)
        var user: User = userService.currentUser().get()
        user.addServer(server)
        userRepository.save(user)
    }

    @GetMapping("/server")
    fun getAllServer(): MutableList<AbstractServer> {
        return serverRepository.findAll()
    }

    @PutMapping("/server/{serverId}")
    fun addCredential(@PathVariable serverId: Int, @RequestBody credential: Credential) {
        credentialRepository.save(credential)
        var server = serverRepository.findById(serverId).get()
        server.addCredential(credential)
        serverRepository.save(server)
    }


    @PutMapping("mapping/{imapFolderMappingId}")
    fun manageImapFolderMapping(@PathVariable imapFolderMappingId: Int, @RequestBody imapFolderMapping: ImapFolderMapping): ImapFolderMapping {
        var mapping = imapFolderMappingRepository.findById(imapFolderMappingId)
        if (mapping.isPresent) {
            mapping.get().merge(imapFolderMapping)
            imapFolderMappingRepository.save(mapping.get())
        }
        return mapping.get()
    }

    @PostMapping("/startup/server")
    fun writeServersToDatabase() {
        serverRepository.save(CustomMailCow())
    }

}
