import java.io.*;
import java.util.*;

public class Practica1Complejidad {

    public static void main(String[] args) throws IOException{
        Scanner scanner = new Scanner(System.in);
        System.out.println("Escriba una opcion: \nEscribe 2 para Bin-Packing \nEscribe 1 para la Gráfica Bipartita \nEscribe 0 para salir del programa");
        switch(scanner.nextInt()){
            case(0):
            break;
            case(1):
            try {
                BufferedReader br = new BufferedReader(new FileReader("grafica.txt"));
                String linea = br.readLine();
                String[] verticesStrings = linea.split(",");
                //Creamos un arreglo con la cantidad total de vertices y luego lo llenamos
                int[] vertices = new int[verticesStrings.length]; 
                for(int i = 0; i < verticesStrings.length; i++){
                    vertices[i] = Integer.parseInt(verticesStrings[i]);
                }
                Grafica grafica = new Grafica(vertices);
                // Leemos las aristas una a una de la grafica
                while ((linea = br.readLine()) != null) {
                    String[] arista = linea.split(",");
                    int a = Integer.parseInt(arista[0])-1;
                    int b = Integer.parseInt(arista[1])-1;
                    grafica.agregaArista(a, b);
                }
                br.close();
                grafica.verGrafica();
    
                // Fase adivinadora
                System.out.println("Fase adivinadora: \nNuestras particiones serán: ");
                ResVerificacion resultado = faseAdivinadora(grafica);

                //Imprimimos en pantalla los vértices con color 1
                System.out.print("Partición de vértices con el color 1: ");
                for (int i = 0; i < grafica.getnumVertices(); i++) {
                    if (grafica.getColor()[i] == 1) {
                        System.out.print(grafica.getVertices()[i] + " ");
                    }
                }
                //Imprimimos en pantalla los vértices con color 2
                System.out.print("\nPartición de vértices con el color 2: ");
                for (int i = 0; i < grafica.getnumVertices(); i++) {
                    if (grafica.getColor()[i] == 2) {
                        System.out.print(grafica.getVertices()[i] + " ");
                    }
                }
                // Fase verificadora
                System.out.println("\nFase verificadora: \n¿Nuestra gráfica es bipartita?");
                if (resultado.esBipartita) {
                    System.out.println("La gráfica es bipartita.");
                } else {
                    System.out.println("La gráfica no es bipartita");
                    System.out.println("Los vértices " + resultado.vertice1 + " y " + resultado.vertice2 + " comparten una arista y tienen el mismo color.");
                }
            } catch (IOException e) {   
            } 
            break;
            case(2):
            try {
                BufferedReader br2 = new BufferedReader(new FileReader("cajones.txt"));
                int numCajones = Integer.parseInt(br2.readLine());
                //Arreglo con la cantidad total de cajones
                int[] cajones = new int[numCajones];
                // Hacemos que cada cajon pueda tener un tamaño total de 10
                Arrays.fill(cajones, 10); 
                String linea;
                List<Elemento> elementos = new ArrayList<>();
                // Arreglo para almacenar los elementos dentro de cada cajón
                ArrayList<Elemento>[] elementosEnCajones = new ArrayList[numCajones];
                for (int i = 0; i < numCajones; i++) {
                    elementosEnCajones[i] = new ArrayList<>();
                }
                //Leemos cada linea y vamos agregando a nuestro arreglo los elementos que tendran que ir en cada cajon
                while ((linea = br2.readLine()) != null) {
                    String[] datos = linea.split(",");
                    int id = Integer.parseInt(datos[0]);
                    int tamanio = Integer.parseInt(datos[1]);
                    elementos.add(new Elemento(id, tamanio));
                }
                br2.close();
                binPacking(cajones, elementos.toArray(new Elemento[0]), elementosEnCajones);
    
            } catch (IOException e) {
            }
            break;
            default:
            System.out.println("Opcion invalida");
        }
    }

    // Este método simulará nuestra fase adivinadora
    // Le pasamos la gráfica y nos devuelve true si es bipartita, y false si no lo es
    public static ResVerificacion faseAdivinadora(Grafica grafica) {
        boolean esBipartita = true;
        int verticeConflicto1 = 0;
        int verticeConflicto2 = 0;
        grafica.coloreaGrafica();
        for (int i = 0; i < grafica.getnumVertices(); i++) {
            if (grafica.getColor()[i] == 0) {
                ResVerificacion resultado = faseVerificadora(grafica, i, 1);
                if (!resultado.esBipartita) {
                    esBipartita = false;
                    verticeConflicto1 = resultado.vertice1;
                    verticeConflicto2 = resultado.vertice2;
                }
            }
        }
        //En nuestra fase adivinadora imprimiremos en pantalla las particiones que se crearon de acuerdo al color
        return new ResVerificacion(esBipartita, verticeConflicto1, verticeConflicto2);
    }


    // Este método simulará nuestra fase verificadora
    // Utilizando DFS verificaremos que nuestra grafica es bipartita
    public static ResVerificacion faseVerificadora(Grafica grafica, int vertice, int color) {
        boolean esBipartita = true;
        int verticeConflicto1 = 0;
        int verticeConflicto2 = 0;
        grafica.getColor()[vertice] = color;
        for (int i = 0; i < grafica.getnumVertices(); i++) {
            if (grafica.getArista(vertice, i) == 1) {
                if (grafica.getColor()[i] == 0) {
                    ResVerificacion resultado = faseVerificadora(grafica, i, 3 - color);
                    if (!resultado.esBipartita) {
                        esBipartita = false;
                        verticeConflicto1 = resultado.vertice1;
                        verticeConflicto2 = resultado.vertice2;
                    }
                } else if (grafica.getColor()[i] == color) {
                    esBipartita = false;
                    verticeConflicto1 = grafica.getVertices()[vertice];
                    verticeConflicto2 = grafica.getVertices()[i];}
            }
        }
        return new ResVerificacion(esBipartita, verticeConflicto1, verticeConflicto2);
    }

    // Clase auxiliar para almacenar el resultado de la verificación
    public static class ResVerificacion {
        boolean esBipartita;
        int vertice1;
        int vertice2;

        public ResVerificacion(boolean esBipartita, int vertice1, int vertice2) {
            this.esBipartita = esBipartita;
            this.vertice1 = vertice1;
            this.vertice2 = vertice2;
        }
    }

    // Algoritmo de Bin Packing (First Fit)
    public static void binPacking(int[] cajones, Elemento[] elementos, ArrayList<Elemento>[] elementosEnCajones) {
        System.out.println("Fase adivinadora \nEmpezaremos metiendo elementos a los cajones.");
        for (Elemento elem : elementos) {
            boolean objetoDentro = false;
            //Por cada elemento veremos si ya está dentro de un cajon, si no lo meteremos en alguno y lo marcaremos.
            for (int i = 0; i < cajones.length; i++) {
                if (cajones[i] >= elem.tamanio) {
                    cajones[i] -= elem.tamanio;
                    elementosEnCajones[i].add(elem);  // Agregamos el elemento al cajón
                    System.out.println("Elemento " + elem.id + " con tamaño " + elem.tamanio + " colocado en el cajón " + (i + 1));
                    objetoDentro = true;
                break;
                }
            }
            if (!objetoDentro) {
                System.out.println("No se pudo colocar el elemento " + elem.id + " con tamaño " + elem.tamanio);
            }
        }

        // Imprimir el estado final de los cajones
        System.out.println("\nFase verificadora \nVeremos el contenido de cada cajon.");
        for (int i = 0; i < cajones.length; i++) {
            System.out.print("Cajón " + (i + 1) + " (capacidad restante: " + cajones[i] + "): ");
            int sumaPesos = 0;
            for (Elemento elem : elementosEnCajones[i]) {
                System.out.print(elem);
                sumaPesos += elem.tamanio;
            }
            System.out.println("\nSuma total de los pesos en el cajon: " + sumaPesos + "\n");
        }
    }
}
