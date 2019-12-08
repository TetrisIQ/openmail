package de.openmail.model.server

import java.util.*
import javax.persistence.Entity

@Entity
class CustomMailCow : AbstractServer("tetrisiq.de","tetrisiq.de") {

    init {
        this.name = "CustomMailCow"
    }


    override fun setImapProps(): Properties {
        val props = System.getProperties()
        props.setProperty("mail.store.protocol", "imaps")
        props.put("mail.imaps.ssl.trust", "tetrisiq.de");
        return props
    }

    override fun setSmtpProps(): Properties {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


}
