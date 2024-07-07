package click.ryangst.hobbies

import click.ryangst.hobbies.integrationtest.testcontainer.AbstractIntegrationTest
import io.restassured.RestAssured.given
import io.restassured.builder.RequestSpecBuilder
import io.restassured.filter.log.LogDetail
import io.restassured.filter.log.ResponseLoggingFilter
import io.restassured.specification.RequestSpecification
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.boot.test.context.SpringBootTest
import org.testcontainers.shaded.com.fasterxml.jackson.databind.DeserializationFeature
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class HobbiesApplicationTests() : AbstractIntegrationTest() {

    private lateinit var specification: RequestSpecification
    private lateinit var objectMapper: ObjectMapper

    @BeforeEach
    fun setup() {
        specification = given().port(TestConfigs.SERVER_PORT)
        objectMapper = ObjectMapper()
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
    }

    @Test
    fun `display Swagger Correctly`() {
        val content = given()
            .basePath("/swagger-ui/index.html")
            .port(TestConfigs.SERVER_PORT)
            .`when`()
            .get()
            .then()
            .statusCode(200)
            .extract()
            .body()
            .asString()

        assert(content.contains("Swagger UI"))

    }

    @Test
    fun `block disallowed CORS requests`() {
        specification = RequestSpecBuilder()
            .addHeader(
                TestConfigs.HEADER_PARAM_ORIGIN,
                TestConfigs.ORIGIN_EVIL
            )
            .setBasePath("/api/person")
            .setPort(TestConfigs.SERVER_PORT)
            .addFilter(ResponseLoggingFilter(LogDetail.ALL))
            .build()

        val content = given(specification)
            .`when`()
            .get()
            .then()
            .statusCode(403)
            .extract()
            .body()
            .asString()

        assert(content.contains("Invalid CORS request"))
    }

    @Test
    fun `allow CORS requests for allowed domains`() {
        specification = RequestSpecBuilder()
            .addHeader(
                TestConfigs.HEADER_PARAM_ORIGIN,
                TestConfigs.ORIGIN_GOOD
            )
            .setBasePath("/swagger-ui/index.html")
            .setPort(TestConfigs.SERVER_PORT)
            .addFilter(ResponseLoggingFilter(LogDetail.ALL))
            .build()


        val content = given(specification)
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
