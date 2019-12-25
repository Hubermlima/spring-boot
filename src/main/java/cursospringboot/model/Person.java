package cursospringboot.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;

@Entity
public class Person implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@NotBlank(message = "Sorry, name can not be blank!")
	private String name;
	@NotBlank(message = "Sorry, last name can not be blank!")
	private String lastname;
	@NotBlank(message = "Sorry, e-mail can not be blank!")
	private String email;
	private String gender;
	
	@OneToMany(mappedBy = "person", cascade = CascadeType.ALL)
	private List<Telephone> telephone;
	
	@ManyToOne
	private Occupation occupation;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	public List<Telephone> getTelephone() {
		return telephone;
	}
	public void setTelephone(List<Telephone> telephone) {
		this.telephone = telephone;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	
	public void setOccupation(Occupation occupation) {
		this.occupation = occupation;
	}
	
	public Occupation getOccupation() {
		return occupation;
	}
	

}
