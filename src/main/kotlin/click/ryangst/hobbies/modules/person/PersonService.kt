package click.ryangst.hobbies.modules.person

import click.ryangst.hobbies.exceptions.ResourceNotFoundException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.logging.Logger

@Service
class PersonService {

    @Autowired
    private lateinit var repository: PersonRepository

    private val logger = Logger.getLogger(PersonService::class.java.name)

    fun findById(id: Long): Person {
        return repository.findById(id).orElseThrow({
            ResourceNotFoundException("No record found for id $id")
        })
    }

    fun findAll(): List<Person> {
        return repository.findAll()
    }

    fun save(person: Person): Person {
        logger.info("Creating person $person")
        return repository.save(person)
    }

    fun update(person: Person): Person {
        logger.info("Updating person $person")
        val entity = repository.findById(person.id).orElseThrow({
            ResourceNotFoundException("No record found for id ${person.id}")
        })

        entity.firstName = person.firstName
        entity.lastName = person.lastName
        entity.address = person.address
        entity.gender = person.gender

        return repository.save(person)
    }

    fun delete(id: Long) {
        logger.info("Deleting person with id $id")
        val entity = repository.findById(id).orElseThrow({
            ResourceNotFoundException("No record found for id ${id}")
        })
        repository.delete(entity)
    }

}
