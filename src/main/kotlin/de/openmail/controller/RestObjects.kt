package de.openmail.controller

import de.openmail.model.Server
import java.util.*

data class UserResponse(
    var id: Int,
    var username: String,
    var role: String,
    var server: MutableList<ServerResponse>
    //var account : MutableList<AccountResponse>
)

data class ServerResponse(
    var id: Int,
    var host: String,
    var accounts: MutableList<AccountResponse>
)

data class AccountResponse(
    var id: Int,
    var name: String,
    var imapFolders: MutableList<String>
)

data class MessageResponse(
    var subject: String,
    var from: String,
    var allRecipients: List<String>,
    var receivedDate: Date,
    var size: Long,
    var Flags: List<String>,
    var content: String,
    var contentType: String
)
