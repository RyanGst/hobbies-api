package click.ryangst.hobbies.modules.person

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController

@RestController
class PersonController {

    @Autowired
    private lateinit var personService: PersonService

    @RequestMapping("/person/{id}", method = [RequestMethod.GET], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun findById(
        @PathVariable id: Long
    ): Person {
        return personService.findById(id)
    }


    @RequestMapping("/person", method = [RequestMethod.GET])
    fun findAll(): List<Person> {
        return personService.findAll()
    }

    @RequestMapping(
        "/person",
        method = [RequestMethod.POST],
        consumes = [MediaType.APPLICATION_JSON_VALUE],
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun save(
        @RequestBody person: Person
    ): Person {
        return personService.save(person)
    }

    @RequestMapping(
        "/person",
        method = [RequestMethod.PUT],
        consumes = [MediaType.APPLICATION_JSON_VALUE],
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun update(
        @RequestBody person: Person
    ): Person {
        return personService.update(person)
    }


}
