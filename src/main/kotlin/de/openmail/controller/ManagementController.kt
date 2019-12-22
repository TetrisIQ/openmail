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
class ManagementController(
    @Autowired val imapFolderMappingRepository: ImapFolderMappingRepository
) {

    @PutMapping("mapping/{imapFolderMappingId}")
    fun manageImapFolderMapping(@PathVariable imapFolderMappingId: Int, @RequestBody imapFolderMapping: ImapFolderMapping): ImapFolderMapping {
        var mapping = imapFolderMappingRepository.findById(imapFolderMappingId)
        if (mapping.isPresent) {
            mapping.get().merge(imapFolderMapping)
            imapFolderMappingRepository.save(mapping.get())
        }
        return mapping.get()
    }

}
