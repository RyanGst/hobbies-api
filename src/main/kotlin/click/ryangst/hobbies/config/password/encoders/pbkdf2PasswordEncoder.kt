package click.ryangst.hobbies.config.password.encoders

import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder

fun pbkdf2PasswordEncoder(): Pbkdf2PasswordEncoder {
    val encoder = Pbkdf2PasswordEncoder(
        "secret",
        16,
        185000,
        Pbkdf2PasswordEncoder.SecretKeyFactoryAlgorithm.PBKDF2WithHmacSHA256
    )
    return encoder
}
