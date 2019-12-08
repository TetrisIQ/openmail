package de.openmail.model

import de.openmail.model.message.Parts
import java.util.*
import javax.mail.Address
import javax.mail.BodyPart
import javax.mail.Flags
import javax.mail.Multipart
import javax.mail.internet.MimeMultipart


class Message {

    private var receivedDate: Date? = null
    private var flags: Flags? = null
    private var subject: String = ""
    private var fromAddress: MutableList<Address> = mutableListOf()
    private var replyToAddress: MutableList<Address> = mutableListOf()
    private var content: Parts
    private var sendDate : Date? = null


    var credentialLable: String = "Label not set"


    constructor(message: javax.mail.Message, accountLabel: String) {
        this.credentialLable = accountLabel
        this.content = Parts((message.content as MimeMultipart).getBodyParts())
        this.fromAddress = message.from.toMutableList()
        this.replyToAddress = message.replyTo.toMutableList()
        this.subject = message.subject
        this.sendDate = message.sentDate
        this.flags = message.flags
        this.receivedDate = message.receivedDate
    }

}

private fun Multipart.getBodyParts(): MutableList<BodyPart> {
    var ret: MutableList<BodyPart> = mutableListOf()
    var a = this.count
    for (i in 0..this.count - 1) {
        ret.add(getBodyPart(i))
    }
    return ret
}
