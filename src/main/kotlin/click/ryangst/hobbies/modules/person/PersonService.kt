package click.ryangst.hobbies.modules.person

import click.ryangst.hobbies.data.vo.v1.PersonVO
import click.ryangst.hobbies.exceptions.RequiredObjectIsNullException
import click.ryangst.hobbies.exceptions.ResourceNotFoundException
import click.ryangst.hobbies.mapper.DozerMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PagedResourcesAssembler
import org.springframework.hateoas.EntityModel
import org.springframework.hateoas.PagedModel
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.logging.Logger

@Service
class PersonService {

    @Autowired
    private lateinit var repository: PersonRepository


    @Autowired
    private lateinit var assembler: PagedResourcesAssembler<PersonVO>

    private val logger = Logger.getLogger(PersonService::class.java.name)

    fun findById(id: Long): PersonVO {
        val person = repository.findById(id).orElseThrow({
            ResourceNotFoundException("No record found for id $id")
        })
        val parseObject = DozerMapper.parseObject(person, PersonVO::class.java)
        val withSelfLink = linkTo(PersonController::class.java).slash(person.id).withSelfRel()
        parseObject.add(withSelfLink)
        return parseObject
    }

    fun findAll(pageable: Pageable): PagedModel<EntityModel<PersonVO>> {
        val persons = repository.findAll(pageable)
        val vos = persons.map { p -> DozerMapper.parseObject(p, PersonVO::class.java) }
        vos.map { p -> p.add(linkTo(PersonController::class.java).slash(p.key).withSelfRel()) }
        return assembler.toModel(vos)
    }

    fun save(person: PersonVO?): PersonVO {
        if (person == null) throw RequiredObjectIsNullException()
        logger.info("Creating person $person")
        val saved = repository.save(DozerMapper.parseObject(person, Person::class.java))
        return DozerMapper.parseObject(saved, PersonVO::class.java)
    }

    fun update(person: PersonVO): PersonVO {
        logger.info("Updating person $person")
        val entity = repository.findById(person.key).orElseThrow({
            ResourceNotFoundException("No record found for id ${person.key}")
        })

        entity.firstName = person.firstName
        entity.lastName = person.lastName
        entity.address = person.address
        entity.gender = person.gender

        val updated = repository.save(entity)
        return DozerMapper.parseObject(updated, PersonVO::class.java)
    }

    fun delete(id: Long) {
        logger.info("Deleting person with id $id")
        val entity = repository.findById(id).orElseThrow({
            ResourceNotFoundException("No record found for id ${id}")
        })
        repository.delete(entity)
    }

    @Transactional
    fun updateEnabled(id: Long, enabled: Boolean): PersonVO {
        logger.info("Updating person with id $id to enabled $enabled")
        repository.updateEnabledById(id, enabled)
        val person = repository.findById(id).orElseThrow({
            ResourceNotFoundException("No record found for id $id")
        })
        return DozerMapper.parseObject(person, PersonVO::class.java)
    }

}
