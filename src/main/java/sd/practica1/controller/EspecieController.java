package sd.practica1.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import sd.practica1.model.Area;
import sd.practica1.model.Especie;
import sd.practica1.model.TipoEspecie;
import sd.practica1.repository.AreaRepository;
import sd.practica1.repository.EspecieRepository;

@Controller
public class EspecieController {

	@Autowired
	private EspecieRepository especieRepository;

	@Autowired
	private AreaRepository areaRepository;

	@RequestMapping(value = "/especies", method = RequestMethod.GET)
	public String listEspecies(Model model) {
		List<Especie> especies = especieRepository.findAll();
		model.addAttribute("especies", especies);
		return "gestionEspecies";
	}

	@RequestMapping(value = "/especies", method = RequestMethod.POST)
	public String addEspecie(@RequestParam("fichero") MultipartFile fichero, Especie especie, Model model) {
		if (especieRepository.findByNombreCientifico(especie.getNombreCientifico()) != null) {
			model.addAttribute("existe", true);
			List<Especie> especies = especieRepository.findAll();
			model.addAttribute("especies", especies);
			return "gestionEspecies";
		}

		try {
			BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(
					new File("target/classes/static/images/animales/" + especie.getNombreCientifico() + "."
							+ this.getFileExtension(fichero.getOriginalFilename()))));
			FileCopyUtils.copy(fichero.getInputStream(), stream);
			stream.close();
		} catch (Exception e) {
		}

		especie.setFoto(especie.getNombreCientifico() + "." + this.getFileExtension(fichero.getOriginalFilename()));
		especieRepository.save(especie);
		model.addAttribute("anadido", true);
		List<Especie> especies = especieRepository.findAll();
		model.addAttribute("especies", especies);
		return "gestionEspecies";
	}

	@RequestMapping(value = "/especies/anadir", method = RequestMethod.GET)
	public String addEspecieForm(Model model) {
		List<Area> areas = areaRepository.findAll();
		model.addAttribute("areas", areas);
		return "anadirEspecie";
	}

	@RequestMapping(value = "/especies/borrar/{id}", method = RequestMethod.GET)
	public String deleteEspecie(@PathVariable(value = "id") long id, Model model) {
		Especie especie = especieRepository.findOne(id);
		if (especie == null) { // La especie no existe
			model.addAttribute("noexiste", true);
			List<Especie> especies = especieRepository.findAll();
			model.addAttribute("especies", especies);
			return "gestionEspecies";
		}
		especieRepository.delete(especie);
		model.addAttribute("borrado", true);
		List<Especie> especies = especieRepository.findAll();
		model.addAttribute("especies", especies);
		return "gestionEspecies";
	}

	@RequestMapping(value = "/especies/modificar/{id}", method = RequestMethod.GET)
	public String modifyEspecie(@PathVariable(value = "id") long id, Model model) {
		Especie especie = especieRepository.findOne(id);
		if (especie == null) { // La especie no existe
			model.addAttribute("nomodificado", true);
			List<Especie> especies = especieRepository.findAll();
			model.addAttribute("especies", especies);
			return "gestionEspecies";
		}
		List<Area> areas = areaRepository.findAll();
		model.addAttribute("areas", areas);
		model.addAttribute("especie", especie);
		return "modificarEspecie";
	}

	@RequestMapping(value = "/especies/buscar", method = RequestMethod.GET)
	public String searchEspecie(Model model) {
		return "buscarEspecies";
	}

	@RequestMapping(value = "/especies/buscar/nombre", method = RequestMethod.POST)
	public String searchEspecieByNombreComun(String nombreComun, Model model) {
		List<Especie> especies = especieRepository.findByNombreComunContaining(nombreComun);
		model.addAttribute("especies", especies);
		model.addAttribute("search", true);
		return "gestionEspecies";
	}

	@RequestMapping(value = "/especies/buscar/tipo", method = RequestMethod.POST)
	public String searchEspecieByTipo(TipoEspecie tipo, Model model) {
		List<Especie> especies = especieRepository.findByTipo(tipo);
		model.addAttribute("especies", especies);
		model.addAttribute("search", true);
		return "gestionEspecies";
	}

	@RequestMapping(value = "/especies/modificar", method = RequestMethod.POST)
	public String addModifiedEspecie(@RequestParam("fichero") MultipartFile fichero, Especie especie, Model model) {
		Especie dbEspecie = especieRepository.findOne(especie.getIdEspecie());
		if (dbEspecie == null) { // No existe
			List<Especie> especies = especieRepository.findAll();
			model.addAttribute("noexiste", true);
			model.addAttribute("especies", especies);
			return "gestionEspecies";
		}
		if (!dbEspecie.getNombreCientifico().equals(especie.getNombreCientifico())) {
			if (especieRepository.findByNombreCientifico(especie.getNombreCientifico()) != null) {
				model.addAttribute("existe", true);
				List<Especie> especies = especieRepository.findAll();
				model.addAttribute("especies", especies);
				return "gestionEspecies";
			}
		}

		if (!fichero.isEmpty()) {
			try {
				BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(
						new File("target/classes/static/images/animales/" + especie.getNombreCientifico() + "."
								+ this.getFileExtension(fichero.getOriginalFilename()))));
				FileCopyUtils.copy(fichero.getInputStream(), stream);
				stream.close();
			} catch (Exception e) {
			}
			dbEspecie.setFoto(
					especie.getNombreCientifico() + "." + this.getFileExtension(fichero.getOriginalFilename()));
		}

		// Renombrado de imagen
		if (!dbEspecie.getNombreCientifico().equals(especie.getNombreCientifico())) {
			//File nombreViejo = new File("target/classes/static/images/animales/" + dbEspecie.getFoto());
			File nombreNuevo = new File("target/classes/static/images/animales/" + especie.getNombreCientifico() + "."
					+ this.getFileExtension(dbEspecie.getFoto()));
			dbEspecie.setFoto(nombreNuevo.getName());
		}

		dbEspecie.setNombreCientifico(especie.getNombreCientifico());
		dbEspecie.setNombreComun(especie.getNombreComun());
		dbEspecie.setTipo(especie.getTipo());
		dbEspecie.setAreas(especie.getAreas());
		especieRepository.save(dbEspecie);
		model.addAttribute("modificado", true);
		List<Especie> especies = especieRepository.findAll();
		model.addAttribute("especies", especies);
		return "gestionEspecies";
	}

	private String getFileExtension(String file) {
		String extension = "";

		int i = file.lastIndexOf('.');
		if (i > 0) {
			extension = file.substring(i + 1);
		}

		return extension;
	}

}