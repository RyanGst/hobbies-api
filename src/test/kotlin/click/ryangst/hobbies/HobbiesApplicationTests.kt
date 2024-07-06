package click.ryangst.hobbies

import click.ryangst.hobbies.integrationtest.testcontainer.AbstractIntegrationTest
import io.restassured.RestAssured
import io.restassured.RestAssured.given
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class HobbiesApplicationTests() : AbstractIntegrationTest() {

    @Test
    fun `display Swagger Correctly`() {
        val content = given()
            .basePath("/swagger-ui/index.html")
            .port(ConfigsTest.SERVER_PORT)
            .`when`()
            .get()
            .then()
            .statusCode(200)
            .extract()
            .body()
            .asString()

        assert(content.contains("Swagger UI"))

    }

}
