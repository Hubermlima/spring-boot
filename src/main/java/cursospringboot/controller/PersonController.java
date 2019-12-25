package cursospringboot.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import cursospringboot.model.Person;
import cursospringboot.model.Telephone;
import cursospringboot.report.ReportUtil;
import cursospringboot.repository.IOccupation;
import cursospringboot.repository.IPerson;
import cursospringboot.repository.ITelephone;

@Controller
public class PersonController {

	@Autowired
	private IPerson iPerson;
	
	@Autowired
	private ITelephone iTelephone;
	
	@Autowired
	private ReportUtil<Person> reportUtil;
	
	@Autowired
	private IOccupation iOccupation;
	
	ModelAndView modelView;

	@RequestMapping(method = RequestMethod.GET, value = "/cadastropessoa")
	public ModelAndView init() {
		render(new Person());
		return modelView;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/newPerson")
	public ModelAndView newPerson(Person person, List<String> msgError) {
		render(person);
		modelView.addObject("persons", iPerson.findAll());
		modelView.addObject("msg", msgError);
		return modelView;
	}
	@RequestMapping(method = RequestMethod.POST, value = "**/saveperson")
	public ModelAndView savePerson(@Valid Person person, BindingResult bindingResult) {
		
		if (bindingResult.hasErrors()) {
			
			List<String> msgError = new ArrayList<>();
			for(ObjectError objError : bindingResult.getAllErrors()) {
				msgError.add(objError.getDefaultMessage());
			}
			
			return newPerson(person, msgError);
		}
		
		person.setTelephone(iTelephone.searchTelephoneByPerson(person));
		iPerson.save(person);

		render(new Person());
		return modelView;
	}

	@GetMapping("/editperson/{idperson}")
	public ModelAndView editPerson(@PathVariable("idperson") Long idPerson) {
		Optional<Person> person = iPerson.findById(idPerson);
		render(person.get());
		return modelView;

	}

	@GetMapping("/telephonesPerson/{idperson}")
	public ModelAndView telephonesPerson(@PathVariable("idperson") Long idPerson) {
		Optional<Person> person = iPerson.findById(idPerson);
		ModelAndView modelView = new ModelAndView("cadastro/telephoneregister");
		modelView.addObject("personObj", person.get());
		modelView.addObject("telephones", iTelephone.searchTelephoneByPerson(person.get()));
		modelView.addObject("telephoneObj", new Telephone());
		
		return modelView;

	}
	
	@GetMapping("/removeperson/{idperson}")
	public ModelAndView removePerson(@PathVariable("idperson") Long idPerson) {
		
		iPerson.deleteById(idPerson);
	    render(new Person());
	    return modelView;
	}
	
	@PostMapping("**/searchPersonByNameAndTelephone")
	public ModelAndView searchPersonByNameAndTelephone(@RequestParam("searchName") String searchName, 
			                                           @RequestParam("gender") String gender) {
		
		render(new Person());
		modelView.addObject("persons", efetiveQuery(searchName, gender));
		return modelView;

	}
	
	public List<Person> efetiveQuery(String name, String gender) {
		return gender.equalsIgnoreCase("EVERYBODY") ?  
				             iPerson.searchPersonByName(name) : 
				             iPerson.searchPersonByNameAndTelephone(name, gender);
		
	}
	
	@GetMapping("**/searchPersonByNameAndTelephone")
	public void PDFPersonByNameAndTelephone(@RequestParam("searchName") String searchName, 
			                                @RequestParam("gender") String gender, 
			                                HttpServletRequest request,
			                                HttpServletResponse response) throws Exception{
		
	    List<Person> listPerson = efetiveQuery(searchName, gender); 
		
		byte[] pdf =reportUtil.generateReport(listPerson, "pessoa", request.getServletContext());
		
		// Preparação para o DOWNLOAD
		
		//Tamanho da response
		response.setContentLength(pdf.length);
		// Definir na response o tipo de arquivo
		response.setContentType("application/octet-stream");
		// Definir cabeçalho da response
		String headerKey = "Content-Disposition";
		String headerValue = String.format("attachment; filename=\"%s\"", "PersonReport.pdf");
		response.setHeader(headerKey, headerValue);
		response.getOutputStream().write(pdf);
		
	}
	
	public void render(Person person) {
		
		modelView = new ModelAndView("cadastro/cadastropessoa");
		modelView.addObject("persons", iPerson.findAll());
		modelView.addObject("personObj", person);
		modelView.addObject("occupations", iOccupation.findAll());
	}
}
