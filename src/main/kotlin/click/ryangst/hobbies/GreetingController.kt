package click.ryangst.hobbies

import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.util.concurrent.atomic.AtomicLong

@RestController
class GreetingController {
    val counter = AtomicLong()

    @RequestMapping("/greeting")
    fun greeting(@RequestParam(value = "name", defaultValue = "Spring Boot") name: String): Greeting {
        return Greeting(counter.incrementAndGet(), "Hello, ${name}!")
    }
}
