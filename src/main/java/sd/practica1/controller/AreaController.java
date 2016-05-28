package sd.practica1.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import sd.practica1.model.Area;
import sd.practica1.repository.AreaRepository;

@Controller
public class AreaController {

	@Autowired
	private AreaRepository areaRepository;

	@RequestMapping(value = "/areas", method = RequestMethod.GET)
	public String listAreas(Model model) {
		List<Area> areas = areaRepository.findAll();
		model.addAttribute("areas", areas);
		return "gestionAreas";
	}

	@RequestMapping(value = "/areas", method = RequestMethod.POST)
	public String addArea(Area area, Model model) {
		if (areaRepository.findByNombre(area.getNombre()) != null) {
			model.addAttribute("existe", true);
			List<Area> areas = areaRepository.findAll();
			model.addAttribute("areas", areas);
			return "gestionAreas";
		}
		areaRepository.save(area);
		model.addAttribute("anadido", true);
		List<Area> areas = areaRepository.findAll();
		model.addAttribute("areas", areas);
		return "gestionAreas";
	}

	@RequestMapping(value = "/areas/anadir", method = RequestMethod.GET)
	public String addAreaForm(Model model) {
		return "anadirArea";
	}

	@RequestMapping(value = "/areas/borrar/{id}", method = RequestMethod.GET)
	public String deleteArea(@PathVariable(value = "id") long id, Model model) {
		Area area = areaRepository.findOne(id);
		if (area == null) { // El area no existe
			model.addAttribute("noexiste", true);
			List<Area> areas = areaRepository.findAll();
			model.addAttribute("areas", areas);
			return "gestionAreas";
		}
		areaRepository.delete(area);
		model.addAttribute("borrado", true);
		List<Area> areas = areaRepository.findAll();
		model.addAttribute("areas", areas);
		return "gestionAreas";
	}

	@RequestMapping(value = "/areas/modificar/{id}", method = RequestMethod.GET)
	public String modifyArea(@PathVariable(value = "id") long id, Model model) {
		Area area = areaRepository.findOne(id);
		if (area == null) { // El area no existe
			model.addAttribute("noexiste", true);
			List<Area> areas = areaRepository.findAll();
			model.addAttribute("areas", areas);
			return "gestionAreas";
		}
		model.addAttribute("area", area);
		return "modificarArea";
	}

	@RequestMapping(value = "/areas/modificar", method = RequestMethod.POST)
	public String addModifiedArea(Area area, Model model) {
		Area dbArea = areaRepository.findOne(area.getId());
		if (dbArea == null) { // No existe
			List<Area> areas = areaRepository.findAll();
			model.addAttribute("noexiste", true);
			model.addAttribute("areas", areas);
			return "gestionAreas";
		}
		if (!dbArea.getNombre().equals(area.getNombre())) {
			if (areaRepository.findByNombre(area.getNombre()) != null) {
				model.addAttribute("existe", true);
				List<Area> areas = areaRepository.findAll();
				model.addAttribute("areas", areas);
				return "gestionAreas";
			}
		}
		dbArea.setNombre(area.getNombre());
		dbArea.setExtension(area.getExtension());
		areaRepository.save(dbArea);
		model.addAttribute("modificado", true);
		List<Area> areas = areaRepository.findAll();
		model.addAttribute("areas", areas);
		return "gestionAreas";
	}

}