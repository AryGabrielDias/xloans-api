package br.com.srm.xloansapi.repository;

import br.com.srm.xloansapi.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {

    @Query(value = "SELECT p.* FROM pessoa p " +
                    "WHERE p.id = :personId",
        nativeQuery = true)
    Person findByPersonId(@Param("personId") Long personId);
}
