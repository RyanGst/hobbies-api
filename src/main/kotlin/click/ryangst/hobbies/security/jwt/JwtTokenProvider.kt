package click.ryangst.hobbies.security.jwt

import click.ryangst.hobbies.data.vo.v1.TokenVO
import click.ryangst.hobbies.exceptions.InvalidJwtAuthenticationException
import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.interfaces.DecodedJWT
import jakarta.annotation.PostConstruct
import jakarta.servlet.http.HttpServletRequest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service
import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import java.util.*

// do not remove the blank space after Bearer
private const val BEARER = "Bearer "

@Service
class JwtTokenProvider {

    @Value("\${security.jwt.token.secret}:secret-key")
    private lateinit var secretKey: String

    @Value("\${security.jwt.token.expiration}")
    private var validityInMilliseconds: Long = 3600000

    @Autowired
    private lateinit var userDetailsService: UserDetailsService

    private lateinit var algorithm: Algorithm

    @PostConstruct
    protected fun init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.toByteArray())
        algorithm = Algorithm.HMAC256(secretKey.toByteArray())
    }

    fun createAccessToken(username: String, roles: List<String>): TokenVO {
        val now = Date()

        return TokenVO(
            username = username,
            authenticated = true,
            created = now,
            expiration = Date(now.time + validityInMilliseconds),
            accessToken = createToken(username, roles, now),
            refreshToken = getRefreshToken(username, roles, now)
        )
    }

    private fun getRefreshToken(username: String, roles: List<String>, now: Date): String? {
        val validityRefreshToken = Date(now.time + 2 * validityInMilliseconds)

        return JWT.create().withClaim("roles", roles).withIssuedAt(now).withSubject(username)
            .withExpiresAt(validityRefreshToken).sign(algorithm)
    }

    private fun createToken(username: String, roles: List<String>, now: Date): String? {
        val issuerUrl = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString()

        return JWT.create().withClaim("roles", roles).withIssuedAt(now).withSubject(username)
            .withExpiresAt(Date(now.time + validityInMilliseconds)).withIssuer(issuerUrl).sign(algorithm)
    }

    fun getAuthentication(token: String?): Authentication {
        val decoded = decodedToken(token)
        val userDetails = userDetailsService.loadUserByUsername(decoded.subject)
        return UsernamePasswordAuthenticationToken(userDetails, "", userDetails.authorities)
    }

    private fun decodedToken(token: String?): DecodedJWT {
        val algorithm = Algorithm.HMAC256(secretKey.toByteArray())
        val verifier = JWT.require(algorithm).build()
        return verifier.verify(token)
    }

    fun resolveToken(req: HttpServletRequest): String? {
        val bearerToken = req.getHeader("Authorization")
        return if (!bearerToken.isNullOrBlank() && bearerToken.startsWith(BEARER)) {
            bearerToken.substring(BEARER.length).trim()
        } else null
    }

    fun validateToken(token: String?): Boolean {
        val decodedToken = decodedToken(token)
        try {
            return !decodedToken.expiresAt.before(Date())
        } catch (e: Exception) {
            throw InvalidJwtAuthenticationException("Expired or invalid JWT token")
        }
    }
}
