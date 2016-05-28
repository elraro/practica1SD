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
public class Especie {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private long idEspecie;
	private String nombreCientifico;
	private String nombreComun;
	private TipoEspecie tipo;
	private String foto;
	@ManyToMany(cascade = CascadeType.DETACH)
	@JoinTable(name = "area_especie", joinColumns = { @JoinColumn(name = "especieId") }, inverseJoinColumns = {
			@JoinColumn(name = "areaId")})
	private Collection<Area> areas;

	public Especie() { }

	public long getIdEspecie() {
		return this.idEspecie;
	}

	public void setIdEspecie(long id) {
		this.idEspecie = id;
	}

	public String getNombreCientifico() {
		return this.nombreCientifico;
	}

	public void setNombreCientifico(String nombreCientifico) {
		this.nombreCientifico = nombreCientifico;
	}

	public String getNombreComun() {
		return this.nombreComun;
	}

	public void setNombreComun(String nombreComun) {
		this.nombreComun = nombreComun;
	}

	public TipoEspecie getTipo() {
		return this.tipo;
	}

	public void setTipo(TipoEspecie tipo) {
		this.tipo = tipo;
	}
	
	public String getFoto() {
		return this.foto;
	}

	public void setFoto(String foto) {
		this.foto = foto;
	}

	public Collection<Area> getAreas() {
		return this.areas;
	}

	public void setAreas(Collection<Area> areas) {
		this.areas = areas;
	}
}
