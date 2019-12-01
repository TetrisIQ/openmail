package de.openmail

import org.simplejavamail.mailer.MailerBuilder
import org.simplejavamail.mailer.config.TransportStrategy
import org.simplejavamail.mailer.Mailer
import javax.mail.Flags.Flag.SEEN
import com.sun.mail.imap.protocol.ENVELOPE
import com.sun.mail.imap.protocol.FLAGS
import com.sun.mail.imap.IMAPFolder
import de.openmail.model.Account
import de.openmail.model.Server
import javax.mail.*
import jdk.nashorn.internal.runtime.linker.NashornCallSiteDescriptor.getFlags
import org.jsoup.Jsoup
import java.io.IOException
import javax.mail.Folder
import javax.mail.internet.MimeMultipart
import org.simplejavamail.converter.internal.mimemessage.MimeMessageParser.isMimeType
import java.lang.IndexOutOfBoundsException


open class Test

fun main(args: Array<String>) {
    var folder: IMAPFolder? = null
    var store: Store? = null
    var subject: String? = null
    val flag: Flags.Flag? = null
    try {
        val props = System.getProperties()
        props.setProperty("mail.store.protocol", "imaps")
        props.put("mail.imaps.ssl.trust", "tetrisiq.de");

        val session = Session.getDefaultInstance(props, null)

        store = session.getStore("imaps")
        store!!.connect("tetrisiq.de", "openmail@tetrisiq.de", "8WArQu2Egvw@kUb3A5u")

        val root = store.defaultFolder
        for (element in root.list()) {
            System.out.println("\t" + element.getName())
        }

        folder = store.getFolder("INBOX") as IMAPFolder // This doesn't work for other email account
        //folder = store.getFolder("inbox"); //This works for both email account


        if (!folder.isOpen)
            folder.open(Folder.READ_WRITE)
        val messages = folder.messages
        println("No of Messages : " + folder.messageCount)
        println("No of Unread Messages : " + folder.unreadMessageCount)
        println(messages.size)
        for (i in messages.indices) {

            println("*****************************************************************************")
            println("MESSAGE " + (i + 1) + ":")
            val msg = messages[i]
            //System.out.println(msg.getMessageNumber());
            //Object String;
            //System.out.println(folder.getUID(msg)

            subject = msg.subject

            println("Subject: " + subject!!)
            System.out.println("From: " + msg.from[0])
            System.out.println("To: " + msg.allRecipients[0])
            System.out.println("Date: " + msg.receivedDate)
            System.out.println("Size: " + msg.size)
            //System.out.println(msg.flags)
            var body = toString(msg)
            System.out.println("Body: \n" + body)
            System.out.println(msg.contentType)

        }
    } finally {
        if (folder != null && folder.isOpen) {
            folder.close(true)
        }
        store?.close()
    }
}

@Throws(MessagingException::class, IOException::class)
fun toString(message: Message): MutableList<String> {
    var ret = mutableListOf<String>()
    var content: Any? = message.content
    var result = ""
    if (content is MimeMultipart) {
        val multipart = content as MimeMultipart?
        if (multipart!!.getCount() > 0) {
            var parts = multipart.getBodyParts()
            //val part = multipart!!.getBodyPart(0)
            for(part in parts) {
                if (part.isMimeType("TEXT/plain")) {
                    //result = result + "\n" + part.getContent()
                    //System.err.println(part.content)
                    ret.add(part.content.toString())
                }
                if (part.isMimeType("TEXT/html")) {
                    val html = part.getContent() as String
                    ret.add(Jsoup.parse(html).toString())
                    //System.err.println(org.jsoup.Jsoup.parse(html))
                    //result = result + "\n" + org.jsoup.Jsoup.parse(html).text()
                } else if (part.getContent() is MimeMultipart) {
                    //result = result + getTextFromMimeMultipart(part.getContent() as MimeMultipart)
                    throw Exception()
                }

            }
            // content = part.getContent()
        }
    }
    return ret
}

private fun Multipart.getBodyParts(): MutableList<BodyPart> {
    var ret : MutableList<BodyPart> = mutableListOf()
    var a = this.count
    for (i in 0..this.count-1) {
        ret.add(getBodyPart(i))
    }
    return ret
}

