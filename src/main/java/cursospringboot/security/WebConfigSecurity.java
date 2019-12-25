package cursospringboot.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class WebConfigSecurity extends WebSecurityConfigurerAdapter{
	
	@Autowired
	private UserDetailsServiceImpl userDetailsServiceImpl;
	
	@Override  // Configura as solicitações de acesso por HTTP
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf()
		.disable() // Desativa as configuraçoes padroes de memoria do spring
	    .authorizeRequests()  // Permitir restringir acessos
	   
	    //.antMatchers(HttpMethod.GET, "/").permitAll() // Qualquer usuario acessa a pagina inicial
	    
	 // Qualquer que tenha role ADMIN terá acesso a esta pagina
	    .antMatchers(HttpMethod.GET, "/cadastropessoa").hasAnyRole("ADMIN")  
	                                                         
	    
	    .anyRequest().authenticated()
	    .and().formLogin().permitAll() // permite qualquer usuario
	    .loginPage("/login")
	    .defaultSuccessUrl("/cadastropessoa")
	    .failureUrl("/login?error=true")
 		.and().logout().logoutSuccessUrl("/login") // Mapeia URL de logout e invalida usuario de logout
 		.logoutRequestMatcher(new AntPathRequestMatcher("/logout"));
	}
	
	@Override // Cria autenticação do usuario com banco de dados  ou em memoria
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		
		auth.userDetailsService(userDetailsServiceImpl).passwordEncoder(new BCryptPasswordEncoder());
		
	}

	@Override // Ignora URL especificas
	public void configure(WebSecurity web) throws Exception {
         web.ignoring().antMatchers("/materialize/**");
	}
}
