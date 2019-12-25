package cursospringboot.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import cursospringboot.model.SystemUser;

@Repository
@Transactional
public interface ISystemUser extends CrudRepository<SystemUser, Long> {
    
	@Query("select s from SystemUser s where s.username = ?1")
	public SystemUser findUserByLogin(String username);
}
