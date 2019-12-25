package cursospringboot.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import cursospringboot.model.Person;
import cursospringboot.model.Telephone;

@Repository
@Transactional
public interface ITelephone extends CrudRepository<Telephone, Long>{
   
	@Query("select t from Telephone t where t.person = ?1")
	public List<Telephone> searchTelephoneByPerson(Person person);
}
