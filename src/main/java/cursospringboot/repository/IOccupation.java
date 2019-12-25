package cursospringboot.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import cursospringboot.model.Occupation;

@Repository
@Transactional
public interface IOccupation extends CrudRepository<Occupation, Long> {

}
