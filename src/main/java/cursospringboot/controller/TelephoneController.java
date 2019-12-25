package cursospringboot.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import cursospringboot.model.Person;
import cursospringboot.model.Telephone;
import cursospringboot.repository.IPerson;
import cursospringboot.repository.ITelephone;

@Controller
public class TelephoneController {

	@Autowired
	private ITelephone iTelephone;
	
	@Autowired
	private IPerson iPerson;
	
	Person person;

	@RequestMapping(method = RequestMethod.GET, value = "/telephoneregister")
	public String init() {
		
		return "";
	}

	@PostMapping("**/saveTelephone/{personId}")
	public ModelAndView saveTelephone(@PathVariable("personId") Long idPerson, Telephone telephone) {
		person = iPerson.findById(idPerson).get();
		telephone.setPerson(person);
		iTelephone.save(telephone);
		return render();
	}

	@GetMapping("/edittelephone/{idtelephone}")
	public ModelAndView editTelephone(@PathVariable("idtelephone") Long idTelephone) {
		Optional<Telephone> telephone = iTelephone.findById(idTelephone);
		ModelAndView modelView = new ModelAndView("cadastro/telephoneregister");
		modelView.addObject("telephoneObj", telephone.get());
		modelView.addObject("personObj", person);
		return modelView;

	}

	@GetMapping("/removetelephone/{idtelephone}")
	public ModelAndView removeTelephone(@PathVariable("idtelephone") Long idTelephone) {

		iTelephone.deleteById(idTelephone);
		return render();
	}

	public ModelAndView render() {

		ModelAndView modelView = new ModelAndView("cadastro/telephoneregister");
		modelView.addObject("telephoneObj", new Telephone());
		modelView.addObject("personObj", person);
		modelView.addObject("telephones", iTelephone.searchTelephoneByPerson(person));
		
		return modelView;
	}
}
