package click.ryangst.hobbies.config

import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.info.License
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class OpenAPIConfig {
    @Bean
    fun customOpenAPI(): OpenAPI {
        return OpenAPI()
            .info(
                Info().title("Hobbies API")
                    .description("API for managing hobbies")
                    .version("v1")
                    .termsOfService("http://ryangst.click/")
                    .license(
                        License()
                            .name("Apache 2.0")
                            .url("http://www.apache.org/licenses/LICENSE-2.0.html")
                    )
            )
    }
}
