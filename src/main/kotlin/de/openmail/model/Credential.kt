package de.openmail.model

import de.openmail.model.server.ImapFolderMapping
import javax.persistence.*

@Entity
class Credential {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id : Int = 0

    var label : String = ""
    var username : String = ""
    var password : String = ""

    @OneToOne(cascade = arrayOf(CascadeType.ALL))
    var imapFolderMapping : ImapFolderMapping = ImapFolderMapping()

    constructor(label : String, username : String, password : String) {
        this.label = label
        this.username = username
        this.password = password
    }

    constructor()

}
