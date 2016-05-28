package sd.practica1.model;

import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

@Entity
public class Area {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private long id;
	private String nombre;
	private int extension;
	@ManyToMany(cascade = CascadeType.DETACH)
	@JoinTable(name = "area_especie", joinColumns = { @JoinColumn(name = "areaId") }, inverseJoinColumns = {
			@JoinColumn(name = "especieId")})
	private Collection<Especie> especies;

	public Area() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getExtension() {
		return this.extension;
	}

	public void setExtension(int extension) {
		this.extension = extension;
	}

	public Collection<Especie> getEspecies() {
		return this.especies;
	}

	public void setEspecies(Collection<Especie> especies) {
		this.especies = especies;
	}
}
