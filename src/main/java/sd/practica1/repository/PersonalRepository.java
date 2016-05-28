package sd.practica1.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import sd.practica1.model.Personal;
import sd.practica1.model.TipoPersonal;

public interface PersonalRepository extends JpaRepository<Personal, Long> {
//	List<Personal> findByNombre(String nombre);
	List<Personal> findByApellidos(String apellidos);
	List<Personal> findByNombreAndApellidos(String nombre, String apellidos);
	Personal findByCorreo(String correo);
	List<Personal> findByTipo(TipoPersonal tipo);
}
