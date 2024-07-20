package click.ryangst.hobbies.modules.auth

import click.ryangst.hobbies.data.vo.v1.AccountCredentialsVO
import io.swagger.v3.oas.annotations.Operation
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/auth")
class AuthController {

    @Autowired
    private lateinit var authService: AuthService

    @PostMapping(
        "/sign-in",
        consumes = [MediaType.APPLICATION_JSON_VALUE],
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    @Operation(summary = "Sign in")
    fun signIn(
        @RequestBody data: AccountCredentialsVO?
    ): ResponseEntity<*> {
        return if (data!!.username.isNullOrBlank() || data.password.isNullOrBlank())
            ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body("Invalid client request")
        else authService.signIn(data)
    }

    @Operation(summary = "Refresh token")
    @PutMapping(
        value = ["/refresh/{username}"],
        consumes = [MediaType.APPLICATION_JSON_VALUE],
    )
    fun refreshToken(
        @PathVariable username: String?,
        @RequestHeader("Authorization") refreshToken: String?
    ): ResponseEntity<*> {
        return if(refreshToken.isNullOrBlank() || username.isNullOrBlank())
            ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body("Invalid client request")

        else authService.refreshToken(username, refreshToken)
    }
}
