package click.ryangst.hobbies

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class HobbiesApplication

fun main(args: Array<String>) {
	runApplication<HobbiesApplication>(*args)
}
