package de.openmail.repository

import de.openmail.model.server.ImapFolderMapping
import org.springframework.data.jpa.repository.JpaRepository

interface ImapFolderMappingRepository : JpaRepository<ImapFolderMapping, Int> {
}
