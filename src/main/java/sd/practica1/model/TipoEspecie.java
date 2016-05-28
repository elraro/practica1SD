package sd.practica1.model;

public enum TipoEspecie {
	VEGETAL("Vegetal"), ANIMAL("Animal"), HONGO("Hongo");
 
    private String value;
 
    private TipoEspecie(String value) {
        this.value = value;
    }
 
    public String getValue() {
        return this.value;
    }
}