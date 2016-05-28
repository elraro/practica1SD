package sd.practica1.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import sd.practica1.model.Personal;
import sd.practica1.model.TipoPersonal;
import sd.practica1.repository.PersonalRepository;

@Controller
public class PersonalController {

	@Autowired
	private PersonalRepository personalRepository;

	@RequestMapping(value = "/personal", method = RequestMethod.GET)
	public String listPersonal(Model model) {
		List<Personal> personales = personalRepository.findAll();
		model.addAttribute("personales", personales);
		return "gestionPersonal";
	}

	@RequestMapping(value = "/personal", method = RequestMethod.POST)
	public String addPersonal(Personal personal, Model model) {
		if (personalRepository.findByCorreo(personal.getCorreo()) != null) {
			model.addAttribute("existe", true);
			List<Personal> personales = personalRepository.findAll();
			model.addAttribute("personales", personales);
			return "gestionPersonal";
		}
		personalRepository.save(personal);
		model.addAttribute("anadido", true);
		List<Personal> personales = personalRepository.findAll();
		model.addAttribute("personales", personales);
		return "gestionPersonal";
	}

	@RequestMapping(value = "/personal/anadir", method = RequestMethod.GET)
	public String addPersonalForm(Model model) {
		List<Personal> personales = personalRepository.findAll();
		model.addAttribute("personales", personales);
		return "anadirPersonal";
	}

	@RequestMapping(value = "/personal/borrar/{id}", method = RequestMethod.GET)
	public String deletePersonal(@PathVariable(value = "id") long id, Model model) {
		Personal personal = personalRepository.findOne(id);
		if (personal == null) { // Ese personal no existe
			model.addAttribute("noexiste", true);
			List<Personal> personales = personalRepository.findAll();
			model.addAttribute("personales", personales);
			return "gestionPersonal";
		}
		personalRepository.delete(personal);
		model.addAttribute("borrado", true);
		List<Personal> personales = personalRepository.findAll();
		model.addAttribute("personales", personales);
		return "gestionPersonal";
	}

	@RequestMapping(value = "/personal/modificar/{id}", method = RequestMethod.GET)
	public String modifyEspecie(@PathVariable(value = "id") long id, Model model) {
		Personal personal = personalRepository.findOne(id);
		if (personal == null) { // El personal no existe
			model.addAttribute("nomodificado", true);
			List<Personal> personales = personalRepository.findAll();
			model.addAttribute("personales", personales);
			return "gestionPersonal";
		}
		model.addAttribute("personal", personal);
		return "modificarPersonal";
	}

	@RequestMapping(value = "/personal/buscar", method = RequestMethod.GET)
	public String buscarPersonal(Model model) {
		return "buscarPersonal";
	}

	@RequestMapping(value = "/personal/buscar/apellidos", method = RequestMethod.POST)
	public String buscarPersonalByApellidos(String apellidos, Model model) {
		List<Personal> personales = personalRepository.findByApellidos(apellidos);
		model.addAttribute("personales", personales);
		model.addAttribute("search", true);
		return "gestionPersonal";
	}
	
	@RequestMapping(value = "/personal/buscar/nombreyapellidos", method = RequestMethod.POST)
	public String buscarPersonalByNombreAndApellidos(String nombre, String apellidos, Model model) {
		List<Personal> personales = personalRepository.findByNombreAndApellidos(nombre, apellidos);
		model.addAttribute("personales", personales);
		model.addAttribute("search", true);
		return "gestionPersonal";
	}

	@RequestMapping(value = "/personal/buscar/tipo", method = RequestMethod.POST)
	public String buscarPersonalByTipo(TipoPersonal tipo, Model model) {
		List<Personal> personales = personalRepository.findByTipo(tipo);
		model.addAttribute("personales", personales);
		model.addAttribute("search", true);
		return "gestionPersonal";
	}

	@RequestMapping(value = "/personal/modificar", method = RequestMethod.POST)
	public String addModifiedPersonal(Personal personal, Model model) {
		Personal dbPersonal = personalRepository.findOne(personal.getId());
		if (dbPersonal == null) { // No existe
			List<Personal> personales = personalRepository.findAll();
			model.addAttribute("noexiste", true);
			model.addAttribute("personales", personales);
			return "gestionPersonal";
		}
		if (!dbPersonal.getCorreo().equals(personal.getCorreo())) {
			if (personalRepository.findByCorreo(personal.getCorreo()) != null) {
				model.addAttribute("existe", true);
				List<Personal> personales = personalRepository.findAll();
				model.addAttribute("personales", personales);
				return "gestionPersonal";
			}
		}
		dbPersonal.setNombre(personal.getNombre());
		dbPersonal.setApellidos(personal.getApellidos());
		dbPersonal.setCorreo(personal.getCorreo());
		dbPersonal.setTelefonoFijo(personal.getTelefonoFijo());
		dbPersonal.setTelefonoMovil(personal.getTelefonoMovil());
		dbPersonal.setTipo(personal.getTipo());
		personalRepository.save(dbPersonal);
		model.addAttribute("modificado", true);
		List<Personal> personales = personalRepository.findAll();
		model.addAttribute("personales", personales);
		return "gestionPersonal";
	}

}