package click.ryangst.hobbies.modules.person

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface PersonRepository: JpaRepository<Person, Long?> {

    @Modifying
    @Query("UPDATE Person p SET p.enabled = :enabled WHERE p.id = :id")
    fun updateEnabledById(@Param("id") id: Long, @Param("enabled") enabled: Boolean)
}
