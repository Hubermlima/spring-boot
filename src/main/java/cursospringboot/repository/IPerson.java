package cursospringboot.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import cursospringboot.model.Person;

@Repository
@Transactional
public interface IPerson extends CrudRepository<Person, Long>{
   
	@Query("select p from Person p where p.name like %?1%")
	public List<Person> searchPersonByName(String name);
	
	@Query("select p from Person p where p.name like %?1% and p.gender = ?2")
	public List<Person> searchPersonByNameAndTelephone(String name, String gender);
}
