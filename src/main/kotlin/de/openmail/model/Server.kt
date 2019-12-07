package de.openmail.model

import com.sun.mail.imap.IMAPFolder
import java.lang.Exception
import javax.mail.*
import javax.persistence.*

@Entity
open class Server() {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Int = 0
    var host: String = ""

    constructor(id : Int, host : String) : this() {
        this.id = id
        this.host = host
    }

    @OneToMany()
    var account: MutableList<Account> = mutableListOf()

    fun addAccount(a: Account) {
        account.add(a)
    }

    fun delUser(a: Account) {
        account.remove(a)
    }

    fun getMessages(a: Account): MutableList<MessageObject> {
        var store: Store? = imapConnect(a)
        var folders: MutableList<IMAPFolder> = mutableListOf()

        if (store == null) {
            throw Exception("store NULL Exeption")
        }
        for (folder in a.imapFolders) {
            folders.add(store.getFolder(folder) as IMAPFolder)
        }
        var messages: MutableList<MessageObject> = mutableListOf()
        for (imap in folders) {
            if (!imap.isOpen)
                imap.open(Folder.READ_WRITE)
            for (m in imap.messages) {
                messages.add(MessageObject(m, a))
                //messages.add(m)
            }
            //val messages = imap.messages
        }

        return messages
    }

    fun getAllMessages(): MutableList<MessageObject> {
        var list = mutableListOf<MessageObject>()
        for (a in account) {
            list.addAll(getMessages(a))
        }
        return list
    }

    fun getUnreadMessages(a: Account): List<MessageObject> {
        var list: MutableList<MessageObject> = getMessages(a)
        //var ret = list.filter { e -> e.flags!!.contains(Flags.Flag.SEEN).not() }
        var ret = list.filter { e -> e.flags!!.SEEN_BIT.not() }
        return ret
    }

    fun getFolders(a: Account): Array<out Folder>? {
        var store: Store = imapConnect(a)
        var ret = store.defaultFolder.list()
        store?.close()
        return ret
    }

    fun imapConnect(a: Account): Store {
        var store: Store? = null
        val props = System.getProperties()
        props.setProperty("mail.store.protocol", "imaps")
        props.put("mail.imaps.ssl.trust", host); // Exception with mailcow hosted Servers


        val session = Session.getDefaultInstance(props, null)

        store = session.getStore("imaps")
        //print("Connectiong to: " + host + " with user: " + a.username)
        store!!.connect(host, a.username, a.password)
        return store
    }

    override fun equals(other: Any?): Boolean {
        other as Server
        return host.equals(other.host)
    }

}

