package de.openmail.model.server

import javax.persistence.*

@Entity
class ImapFolderMapping() {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Int = 0

    @ElementCollection
    @CollectionTable
    var combinedFolders: MutableSet<String> = mutableSetOf()
    @ElementCollection
    @CollectionTable
    var sendFolders: MutableSet<String> = mutableSetOf()
    @ElementCollection
    @CollectionTable
    var trashFolders: MutableSet<String> = mutableSetOf()
    @ElementCollection
    @CollectionTable
    var archivedFolders: MutableSet<String> = mutableSetOf()

    fun merge(imapFolderMapping: ImapFolderMapping) {
        this.combinedFolders.addAll(imapFolderMapping.combinedFolders)
        this.sendFolders.addAll(imapFolderMapping.sendFolders)
        this.trashFolders.addAll(imapFolderMapping.trashFolders)
        this.archivedFolders.addAll(imapFolderMapping.archivedFolders)
    }
}
