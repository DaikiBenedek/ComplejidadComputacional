import java.util.Arrays;
//Clase de una gráfica
public class Grafica {
    int[] vertices;  //Arreglo donde guardaremos a los vertices.
    int numVertices;    //Numero total de vertices.
    int[][] aristas;    //Las posiciones representan si existe arista o no entre los vertices con los numeros de los indices.
    int[] colores;          // Color de nuestro vertica para la fase verificadora

    //Constructor de la grafica.
    public Grafica(int[] vertices) {
        this.vertices = vertices;
        this.numVertices = vertices.length;
        this.aristas = new int[numVertices][numVertices];
        this.colores = new int[numVertices];
    }
    //Getters
    public int[] getVertices(){
        return vertices;
    }

    public int getnumVertices(){
        return numVertices;
    }

    public int getArista(int a, int b){
        return aristas[a][b];
    }

    public int[] getColor(){
        return colores;
    }

    //Método para regresar a 0 todos los colores de la gráfica
    public void coloreaGrafica(){
        Arrays.fill(colores, 0);
    }

    //Método para agregar aristas.
    public void agregaArista(int a, int b) {
        aristas[a][b] = 1;  //Ponemos 1 si hay arista entre esos 2 vertices.
        aristas[b][a] = 1;  //Nuestra matriz es simetrica, asi que llenamos con 1 el otro lado.
    }

    //Método para que podamos ver los elementos de la grafica.
    public void verGrafica() {
        System.out.println("Nuestros vértices son: " + Arrays.toString(vertices));
        System.out.println("Nuestras aristas son: ");
        for (int i = 0; i < numVertices; i++) {
            for (int j = i + 1; j < numVertices; j++) {
                if (aristas[i][j] == 1) {
                    System.out.println("Existe la arista " + vertices[i] + " - " + vertices[j]);
                }
            }
        }
    }
}
