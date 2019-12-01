package de.openmail.controller

import com.google.gson.Gson
import de.openmail.config.UserService
import de.openmail.model.MessageObject
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody
import javax.mail.Message

@Controller
@RequestMapping("/msg")
class MessageControler(
    @Autowired val userService: UserService
) {

    @GetMapping
    @ResponseBody
    fun getAllMessages(): String? {
        var user = userService.currentUser()
        if (user == null) {
            throw Exception("User not found")
        }
        //return messageResponse(user.server.get(0).getAllMessages())
        return Gson().toJson(user.server.get(0).getAllMessages())
    }

    private fun messageResponse(allMessages: MutableList<MessageObject>): MutableList<MessageResponse> {
        return mutableListOf()
    }

}
