package click.ryangst.hobbies.integrationtest.testcontainer

import org.springframework.context.ApplicationContextInitializer
import org.springframework.context.ConfigurableApplicationContext
import org.springframework.core.env.MapPropertySource
import org.springframework.test.context.ContextConfiguration
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.lifecycle.Startables
import java.util.stream.Stream

@ContextConfiguration(initializers = [AbstractIntegrationTest.Initializer::class])
open class AbstractIntegrationTest {
    internal class Initializer : ApplicationContextInitializer<ConfigurableApplicationContext> {

        override fun initialize(applicationContext: ConfigurableApplicationContext) {
            startContainers()

            val environment = applicationContext.environment
            val testcontainers = MapPropertySource(
                "testcontainers", createConnectionConfiguration()
            )

            environment.propertySources.addFirst(testcontainers)
        }


        companion object {
            private var postgresql = PostgreSQLContainer<Nothing>("postgres:13.2")

            private fun createConnectionConfiguration(): Map<String, String> {
                return mapOf(
                    "spring.datasource.url" to postgresql.jdbcUrl,
                    "spring.datasource.username" to postgresql.username,
                    "spring.datasource.password" to postgresql.password
                )
            }

            fun startContainers() {
                Startables.deepStart(Stream.of(postgresql)).join()
            }
        }


    }
}
