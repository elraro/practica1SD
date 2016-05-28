package sd.practica1.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import sd.practica1.model.Especie;
import sd.practica1.model.TipoEspecie;

public interface EspecieRepository extends JpaRepository<Especie, Long> {
	List<Especie> findByNombreComunContaining(String nombreComun);
	List<Especie> findByTipo(TipoEspecie tipo);
	Especie findByNombreCientifico(String nombreCientifico);
}
