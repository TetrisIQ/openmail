package de.openmail.model.server

import com.sun.mail.imap.IMAPFolder
import de.openmail.model.Credential
import de.openmail.model.Message
import java.util.*
import javax.mail.Folder
import javax.mail.Session
import javax.mail.Store
import javax.persistence.*

@Entity
abstract class AbstractServer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Int = 0

    var name: String = "" // shoud be the Classname

    var imapHost: String = ""
    var imapPort: Int = 0

    var smtpHost: String = ""
    var smtpPort: Int = 0

    @ManyToMany
    var credentials: MutableList<Credential> = mutableListOf()

    constructor()

    constructor(imapHost: String, smtpHost: String) {
        this.imapHost = imapHost
        this.imapPort = imapPort
        this.smtpHost = smtpHost
        this.smtpPort = smtpPort
    }

    abstract fun setImapProps(): Properties;

    abstract fun setSmtpProps(): Properties;

    fun addCredential(credential: Credential): Boolean {
        return credentials.add(credential)
    }

    fun getImapFolder(credential: Credential, folder: String): Optional<MutableList<Message>> {
        var imapFolder: IMAPFolder? = null
        var store: Store? = null
        try {
            val props = setImapProps()
            val session = Session.getDefaultInstance(props, null)
            store = session.getStore("imaps")
            store!!.connect(imapHost, credential.username, credential.password)

            // If we get the FolderName from RequestParameter, it's surround with "
            imapFolder = store.getFolder(folder.replace("\"","")) as IMAPFolder

            if (!imapFolder.isOpen)
                imapFolder.open(Folder.READ_WRITE)
            var returnValue: MutableList<Message> = mutableListOf()
            for (m in imapFolder.messages) {
                returnValue.add(Message(m, credential.label))
            }
            return Optional.of(returnValue)
        } finally {
            if (imapFolder != null && imapFolder.isOpen) {
                imapFolder.close(true)
            }
            store?.close()
        }
    }

    fun getFolder(): List<String> {
        var imapFolder: IMAPFolder? = null
        var store: Store? = null
        try {
            val props = setImapProps()

            val session = Session.getDefaultInstance(props, null)

            store = session.getStore("imaps")
            store!!.connect(imapHost, this.credentials[0].username, this.credentials[0].password)

            val root = store.defaultFolder

            var returnValue: MutableList<String> = mutableListOf()
            for (element in root.list()) {
                returnValue.add(element.name)
            }
            return returnValue
        } finally {
            if (imapFolder != null && imapFolder.isOpen) {
                imapFolder.close(true)
            }
            store?.close()
        }
    }

    fun getCombinedFolders(credential: Credential): Optional<MutableList<Message>> {
        return getMappingFolder(credential, credential.imapFolderMapping.combinedFolders)
    }

    fun getSendFolders(credential: Credential): Optional<MutableList<Message>> {
        return getMappingFolder(credential, credential.imapFolderMapping.sendFolders)
    }

    fun getTrashFolders(credential: Credential): Optional<MutableList<Message>> {
        return getMappingFolder(credential, credential.imapFolderMapping.trashFolders)
    }

    fun getArchivedFolders(credential: Credential): Optional<MutableList<Message>> {
        return getMappingFolder(credential, credential.imapFolderMapping.archivedFolders)
    }

    private fun getMappingFolder(credential: Credential, folders: MutableSet<String>): Optional<MutableList<Message>> {
        var returnValue: MutableList<Message> = mutableListOf()
        for (folder in folders) {
            returnValue.addAll(getImapFolder(credential, folder).get())
        }
        return Optional.of(returnValue)
    }


}
