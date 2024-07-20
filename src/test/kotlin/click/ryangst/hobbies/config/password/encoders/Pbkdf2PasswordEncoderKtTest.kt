package click.ryangst.hobbies.config.password.encoders

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import kotlin.math.log

class Pbkdf2PasswordEncoderKtTest {

    @Test
    fun createEncoder() {
        val encoder = pbkdf2PasswordEncoder()
        assertNotNull(encoder)
    }

    @Test
    fun encodePassword() {
        val encoder = pbkdf2PasswordEncoder()
        val password = "admin1234"
        val encodedPassword = encoder.encode(password)
        println("MY ENCODED PASSWORD: $encodedPassword")
        assertTrue(encoder.matches(password, encodedPassword))
    }
}
