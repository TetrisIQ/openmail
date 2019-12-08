package de.openmail.model.message

import org.jsoup.Jsoup
import javax.mail.BodyPart
import javax.mail.internet.MimeMultipart

class Parts {

    var parts : MutableList<Part> = mutableListOf()

    constructor(bodyParts: MutableList<BodyPart>) {
        for (p in bodyParts) {
            parts.add(Part(p))
        }
    }

}

class Part {

    var type : String = ""

    var content : String =""

    constructor(part : BodyPart) {
        this.type = part.contentType
        if (part.isMimeType("TEXT/plain")) {
            //result = result + "\n" + part.getContent()
            //System.err.println(part.content)
            content = part.content.toString()
        }
        else if (part.isMimeType("TEXT/html")) {
            val html = part.getContent() as String
            content = Jsoup.parse(html).toString()
            //System.err.println(org.jsoup.Jsoup.parse(html))
            //result = result + "\n" + org.jsoup.Jsoup.parse(html).text()
        } else if (part.getContent() is MimeMultipart) {
            //result = result + getTextFromMimeMultipart(part.getContent() as MimeMultipart)
            print(part.contentType)
            throw Exception()
        }
    }
}
