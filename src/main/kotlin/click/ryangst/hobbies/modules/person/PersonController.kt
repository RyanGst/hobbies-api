package click.ryangst.hobbies.modules.person

import click.ryangst.hobbies.data.vo.v1.PersonVO
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.hateoas.EntityModel
import org.springframework.hateoas.PagedModel
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/person")
class PersonController {

    @Autowired
    private lateinit var personService: PersonService

    @GetMapping("/{id}")
    fun findById(
        @PathVariable id: Long
    ): PersonVO {
        return personService.findById(id)
    }


    @GetMapping("/", produces = [MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE])
    fun findAll(
        @RequestParam(value = "page", defaultValue = "0") page: Int,
        @RequestParam(value = "limit", defaultValue = "10") limit: Int,
        @RequestParam(value = "sort", defaultValue = "asc") sort: String,
    ): ResponseEntity<PagedModel<EntityModel<PersonVO>>> {
        val sortDirection = if (sort.equals("desc", ignoreCase = true)) Sort.Direction.DESC else Sort.Direction.ASC
        val pageable = PageRequest.of(page, limit, Sort.by(sortDirection, "firstName"))
        return ResponseEntity.ok(personService.findAll(pageable))
    }

    @PostMapping(
        "/",
        consumes = [MediaType.APPLICATION_JSON_VALUE],
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun save(
        @RequestBody person: PersonVO
    ): PersonVO {
        return personService.save(person)
    }

    @PutMapping(
        "/",
        consumes = [MediaType.APPLICATION_JSON_VALUE],
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun update(
        @RequestBody person: PersonVO
    ): PersonVO {
        return personService.update(person)
    }

    @DeleteMapping("/{id}")
    fun delete(
        @PathVariable id: Long
    ): ResponseEntity<Any> {
        personService.delete(id)
        return ResponseEntity.noContent().build()
    }

    @PatchMapping("/{id}")
    fun updateEnabled(
        @PathVariable id: Long,
    ): ResponseEntity<PersonVO> {
        return ResponseEntity.ok(personService.updateEnabled(id, false))
    }

}
