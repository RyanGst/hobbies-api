package click.ryangst.hobbies.modules.person

import org.springframework.stereotype.Service
import java.util.logging.Logger
import java.util.concurrent.atomic.AtomicLong

@Service
class PersonService {
    private val count = AtomicLong()

    private val logger = Logger.getLogger(PersonService::class.java.name)

    fun findById(id: Long): Person {
        logger.info("Finding person with id $id")
        return Person(id, "John", "Doe", "123 Main", "M")
    }

    fun save(person: Person): Person {
        logger.info("Saving person $person")
        return person.copy(id = count.incrementAndGet())
    }

    fun update(person: Person): Person {
        logger.info("Updating person $person")
        return person
    }

    fun delete(id: Long) {
        logger.info("Deleting person with id $id")
    }

    fun findAll(): List<Person> {
        logger.info("Finding all persons")
        return listOf(
            Person(1, "John", "Doe", "123 Main", "M"),
            Person(2, "Jane", "Doe", "123 Main", "F")
        )
    }
}
