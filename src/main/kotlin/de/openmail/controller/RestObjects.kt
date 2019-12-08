package de.openmail.controller

import java.util.*

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
