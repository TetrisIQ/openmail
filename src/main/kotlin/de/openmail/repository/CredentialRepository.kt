package de.openmail.repository

import de.openmail.model.Credential
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface CredentialRepository : JpaRepository<Credential, Int> {
}
