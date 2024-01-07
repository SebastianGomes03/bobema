import java.io.Serializable;

public class personaje implements Serializable {

    public int id;
    public String nombre;

    public personaje(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }
}
