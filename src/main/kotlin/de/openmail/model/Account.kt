package de.openmail.model

import javax.persistence.*

@Entity
class Account(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id:Int,
    val name: String,
    val username: String,
    val password: String,

    @ElementCollection
    @CollectionTable
    val imapFolders : MutableList<String>
) {



}
