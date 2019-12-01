package de.openmail.model

import org.jsoup.Jsoup
import javax.mail.*
import javax.mail.Message
import javax.persistence.*
import java.util.*
import javax.mail.BodyPart
import java.io.IOException
import javax.mail.internet.InternetAddress
import javax.mail.internet.MimeMultipart


//@Entity
class MessageObject {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Int = 0
    @OneToOne
    lateinit var account: Account

    //Frome javax.mail
    var messageNumber = 0
    var isExpunged = false
    //var folder: Folder? = null
    //var session: Session? = null

    @ElementCollection
    @CollectionTable
    var from: MutableList<String> = mutableListOf()
    @ElementCollection
    @CollectionTable
    var allRecipients: MutableList<String> = mutableListOf()
    @ElementCollection
    @CollectionTable
    var replyTo: MutableList<String> = mutableListOf()
    var subject: String? = null
    var sentDate: Date? = null
    var receivedDate: Date? = null
    @OneToOne
    var flags: Flag? = null
    @ManyToMany
    var content: MutableList<ContentType> = mutableListOf()
    var contentType: String? = null

    constructor(message: Message, account: Account) {
        messageNumber = message.messageNumber
        isExpunged = message.isExpunged
        //folder = message.folder
        //session = message.session
        var fromAdress = message.from.get(0) as InternetAddress
        from.add(fromAdress.address)
        from.add(fromAdress.personal)
        var recipientsAddress = message.allRecipients.get(0) as InternetAddress
        allRecipients.add(recipientsAddress.address)
        allRecipients.add(recipientsAddress.personal)
        var replyAddress = message.replyTo.get(0) as InternetAddress
        replyTo.add(replyAddress.address)
        replyTo.add(replyAddress.personal)
        subject = message.subject
        sentDate = message.sentDate
        receivedDate = message.receivedDate
        flags = Flag().convert(message.flags)
        content = message.getContents()
        contentType = message.contentType

    }

    /**
     * Converts the given JavaMail message to a String body.
     * Can return null.
     */
    @Throws(MessagingException::class, IOException::class)
    fun toString(message: Message): String? {
        var content: Any? = message.content
        if (content is MimeMultipart) {
            val multipart = content as MimeMultipart?
            if (multipart!!.getCount() > 0) {
                val part = multipart!!.getBodyPart(0)
                println(part.contentType)
                content = part.getContent()
            }
        }
        return content?.toString()
    }
}


@Entity
class Flag {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Int = 0
    var user_flags: Array<out String>? = null
    var ANSWERED_BIT = false
    var DELETED_BIT = false
    var DRAFT_BIT = false
    var FLAGGED_BIT = false
    var RECENT_BIT = false
    var SEEN_BIT = false
    var USER_BIT = false


    //All functions should return this (so we can chain functions)
    fun seen(): Flag {
        SEEN_BIT = true
        return this
    }

    fun umSeen(): Flag {
        SEEN_BIT = false
        return this
    }

    fun answerd(): Flag {
        ANSWERED_BIT = true
        return this
    }

    fun deleted(): Flag {
        DELETED_BIT = true
        return this
    }

    fun flagged(): Flag {
        FLAGGED_BIT = true
        return this
    }

    fun draft(): Flag {
        DRAFT_BIT = true
        return this
    }

    fun recent(): Flag {
        RECENT_BIT = true
        return this
    }

    fun user(): Flag {
        USER_BIT = true
        return this
    }

    fun convert(flags: Flags?): Flag {
        var flag: Flag = Flag()
        if (flags!!.contains(Flags.Flag.SEEN)) {
            flag.seen()
        }
        if (flags!!.contains(Flags.Flag.DELETED)) {
            flag.deleted()
        }
        if (flags!!.contains(Flags.Flag.ANSWERED)) {
            flag.answerd()
        }
        if (flags!!.contains(Flags.Flag.FLAGGED)) {
            flag.flagged()
        }
        if (flags!!.contains(Flags.Flag.DRAFT)) {
            flag.draft()
        }
        if (flags!!.contains(Flags.Flag.RECENT)) {
            flag.recent()
        }
        if (flags!!.contains(Flags.Flag.USER)) {
            flag.user()
        }
        if (flags!!.userFlags.isNotEmpty()) {
            flag.user_flags = flags!!.userFlags
        }
        return flag
    }


}

private fun Message.getContents(): MutableList<ContentType> {
    var result: MutableList<ContentType> = mutableListOf()
    var content: Any = this.content
    if (content is MimeMultipart) {
        val multipart: MimeMultipart = content
        var parts: MutableList<BodyPart> = multipart.getBodyParts()
        for (part: BodyPart in parts) {
            if (part.isMimeType("text/plain")) {
                result.add(ContentType(part.content.toString(), part.contentType))
            } else if (part.isMimeType("TEXT/html")) {
                val html: String = part.getContent() as String
                result.add(ContentType(Jsoup.parse(html).toString(), part.contentType))
            } else {
                // throw Exception(part.contentType)
                println("NASE")
            }
        }
    }
    return result
}

private fun Multipart.getBodyParts(): MutableList<BodyPart> {
    var ret: MutableList<BodyPart> = mutableListOf()
    var a = this.count
    for (i in 0..this.count - 1) {
        ret.add(getBodyPart(i))
    }
    return ret
}

@Entity
class ContentType(var content: String, var contentType: String) {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id : Int = 0
}
