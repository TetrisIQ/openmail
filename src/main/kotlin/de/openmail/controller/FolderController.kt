package de.openmail.controller

import de.openmail.config.UserService
import de.openmail.repository.CredentialRepository
import de.openmail.repository.ServerRepository
import de.openmail.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("folder")
class FolderController(
    @Autowired val userService: UserService,
    @Autowired val userRepository: UserRepository,
    @Autowired val credentialRepository: CredentialRepository,
    @Autowired val serverRepository: ServerRepository
) {

    @GetMapping
    fun getFolders(@RequestParam serverId: Int, @RequestParam credentialId: Int): MutableMap<String, MutableList<String>> {
        var server = serverRepository.findById(serverId).get()
        var credential = credentialRepository.findById(credentialId).get()
        var returnValue = mutableMapOf<String, MutableList<String>>()
        returnValue.put(credential.label, server.getFolder(credential.username, credential.password))
        return returnValue
    }

    @GetMapping("/all")
    fun getAllFolders(@RequestParam serverId: Int): MutableMap<String, MutableList<String>> {
        var server = serverRepository.findById(serverId).get()
        var creds = server.credentials
        var returnValue = mutableMapOf<String, MutableList<String>>()
        for (c in creds) {
            returnValue.put(c.label, server.getFolder(c.username, c.password))

        }
        return returnValue
    }
}
