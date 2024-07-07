package click.ryangst.hobbies.modules.auth

import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/auth")
class AuthController {

    @RequestMapping("/api/auth")
    fun auth(): String {
        return "Hello, World!"
    }
}
