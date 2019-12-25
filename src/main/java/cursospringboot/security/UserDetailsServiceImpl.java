package cursospringboot.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cursospringboot.model.SystemUser;
import cursospringboot.repository.ISystemUser;

@Service
@Transactional
public class UserDetailsServiceImpl implements UserDetailsService{

	@Autowired
	private ISystemUser iSystemUser;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		SystemUser systemUser = iSystemUser.findUserByLogin(username);
		
		if (systemUser == null) {
			throw new UsernameNotFoundException("Username not found!");
		}
		return new User(systemUser.getUsername(), 
				systemUser.getPassword(), 
				systemUser.isEnabled(),
				systemUser.isAccountNonExpired(),
				systemUser.isCredentialsNonExpired(),
				systemUser.isAccountNonLocked(),
				systemUser.getAuthorities());
	}

}
