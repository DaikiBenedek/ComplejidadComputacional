public class Elemento {
    int id;
    int tamanio;

    public Elemento(int id, int tamanio) {
        this.id = id;
        this.tamanio = tamanio;
    }

    @Override
    public String toString() {
        return "\nElemento " + id + " con tamaño " + tamanio;
    }
}
