package sd.practica1.model;

public enum TipoPersonal {
	GESTION("Gestión"), VIGILANCIA("Vigilancia"), CONSERVACION("Conservación"), INVESTIGACION("Investigación");
	 
    private String value;
 
    private TipoPersonal(String value) {
        this.value = value;
    }
 
    public String getValue() {
        return this.value;
    }
}
