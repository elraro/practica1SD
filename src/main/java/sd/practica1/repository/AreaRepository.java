package sd.practica1.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import sd.practica1.model.Area;

public interface AreaRepository extends JpaRepository<Area, Long> {
	Area findByNombre(String nombre);
}
