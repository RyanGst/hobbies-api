package click.ryangst.hobbies.modules.auth

import click.ryangst.hobbies.data.vo.v1.AccountCredentialsVO
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/auth")
class AuthController {

    @Autowired
    private lateinit var authService: AuthService

    @PostMapping(
        "/",
        consumes = [MediaType.APPLICATION_JSON_VALUE],
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun signIn(
        @RequestBody data: AccountCredentialsVO
    ): ResponseEntity<*> {
        return if (data.username.isNullOrBlank() || data.password.isNullOrBlank()) {
            ResponseEntity.badRequest().body("Username and password are required")
        } else {
            authService.signIn(data)
        }
    }
}
