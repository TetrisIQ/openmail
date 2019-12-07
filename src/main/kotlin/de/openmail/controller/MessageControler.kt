package de.openmail.controller

import com.google.gson.Gson
import de.openmail.config.UserService
import de.openmail.model.MessageObject
import de.openmail.repository.MessageObjectRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*
import javax.mail.Message

@RestController
@RequestMapping("/msg")
class MessageControler(
    @Autowired val userService: UserService,
    @Autowired val messageObjectRepository: MessageObjectRepository
) {

    @GetMapping
    fun getAllMessages(): MutableList<MessageObject> {
        var user = userService.currentUser()
        if (user != null) {
            return user.messages
        }
        return mutableListOf()
    }

    @PostMapping
    fun refreshAll() {
        var user = userService.currentUser()
        if (user != null) {
            var ls = user.getAllMessages()
            //messageObjectRepository.saveAll(ls)
            for(m in ls) {
                messageObjectRepository.save(m)
            }
        }
    }

    private fun messageResponse(allMessages: MutableList<MessageObject>): MutableList<MessageResponse> {
        return mutableListOf()
    }

}
