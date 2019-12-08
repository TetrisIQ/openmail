package de.openmail.controller

import com.google.gson.Gson
import de.openmail.config.UserService
import de.openmail.repository.CredentialRepository
import de.openmail.repository.ImapFolderMappingRepository
import de.openmail.repository.ServerRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
class MessageController(
    @Autowired val userService: UserService,
    @Autowired val credentialRepository: CredentialRepository,
    @Autowired val serverRepository: ServerRepository,
    @Autowired val imapFolderMappingRepository: ImapFolderMappingRepository
) {

    @GetMapping("/message/{serverId}")
    fun getFolder(@RequestParam folder: String, @PathVariable serverId: Int, @RequestParam credentialId: Int): String? {
        val credentialsOptional = credentialRepository.findById(credentialId)
        val serverOptional = serverRepository.findById(serverId)
        val returnValue = serverOptional.get().getImapFolder(credentialsOptional.get(), folder).get()
        return Gson().toJson(returnValue)
    }

    @GetMapping("/combined/message/{serverId}")
    fun getCombinedFolders(@PathVariable serverId: Int, @RequestParam credentialId: Int): String? {
        val credentialsOptional = credentialRepository.findById(credentialId)
        val serverOptional = serverRepository.findById(serverId)
        val returnValue = serverOptional.get().getCombinedFolders(credentialsOptional.get()).get()
        return Gson().toJson(returnValue)
    }

    @GetMapping("/send/message/{serverId}")
    fun getSendFolders(@RequestParam folder: String, @PathVariable serverId: Int, @RequestParam credentialId: Int): String? {
        val credentialsOptional = credentialRepository.findById(credentialId)
        val serverOptional = serverRepository.findById(serverId)
        val returnValue = serverOptional.get().getSendFolders(credentialsOptional.get())
        return Gson().toJson(returnValue)
    }





    }
