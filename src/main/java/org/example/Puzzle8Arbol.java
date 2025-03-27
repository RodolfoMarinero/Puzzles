package org.example;

import java.util.*;

class Nodo implements Comparable<Nodo> {
    int[][] estado;
    Nodo padre;
    int g, h, f;

    public Nodo(int[][] estado, Nodo padre, int g, int h) {
        this.estado = copiarEstado(estado);
        this.padre = padre;
        this.g = g;
        this.h = h;
        this.f = g + h;
    }

    private int[][] copiarEstado(int[][] estado) {
        int[][] copia = new int[3][3];
        for (int i = 0; i < 3; i++)
            System.arraycopy(estado[i], 0, copia[i], 0, 3);
        return copia;
    }

    @Override
    public int compareTo(Nodo otro) {
        return Integer.compare(this.f, otro.f);
    }
}

public class Puzzle8Arbol {
    private static int[][] OBJETIVO;

    public static List<int[][]> resolver(int[][] estadoInicial) {
        PriorityQueue<Nodo> colaPrioridad = new PriorityQueue<>();
        Set<String> visitados = new HashSet<>();

        Nodo raiz = new Nodo(estadoInicial, null, 0, calcularHeuristica(estadoInicial));
        colaPrioridad.add(raiz);

        while (!colaPrioridad.isEmpty()) {
            Nodo actual = colaPrioridad.poll();
            if (Arrays.deepEquals(actual.estado, OBJETIVO)) {
                return reconstruirCamino(actual);
            }

            visitados.add(convertirAString(actual.estado));

            for (Nodo vecino : generarVecinos(actual)) {
                if (!visitados.contains(convertirAString(vecino.estado))) {
                    colaPrioridad.add(vecino);
                }
            }
        }
        return null;
    }

    private static int calcularHeuristica(int[][] estado) {
        int malColocados = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (estado[i][j] != 0 && estado[i][j] != OBJETIVO[i][j]) {
                    malColocados++;
                }
            }
        }
        return malColocados;
    }

    private static List<Nodo> generarVecinos(Nodo nodo) {
        List<Nodo> vecinos = new ArrayList<>();
        int[] dx = {-1, 1, 0, 0};
        int[] dy = {0, 0, -1, 1};

        int x = 0, y = 0;
        buscarCero:
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (nodo.estado[i][j] == 0) {
                    x = i;
                    y = j;
                    break buscarCero;
                }
            }
        }

        for (int i = 0; i < 4; i++) {
            int nx = x + dx[i];
            int ny = y + dy[i];

            if (nx >= 0 && nx < 3 && ny >= 0 && ny < 3) {
                int[][] nuevoEstado = copiarEstado(nodo.estado);
                nuevoEstado[x][y] = nuevoEstado[nx][ny];
                nuevoEstado[nx][ny] = 0;

                Nodo nuevoNodo = new Nodo(nuevoEstado, nodo, nodo.g + 1, calcularHeuristica(nuevoEstado));
                vecinos.add(nuevoNodo);
            }
        }
        return vecinos;
    }

    private static List<int[][]> reconstruirCamino(Nodo nodo) {
        List<int[][]> camino = new ArrayList<>();
        while (nodo != null) {
            camino.add(0, nodo.estado);
            nodo = nodo.padre;
        }
        return camino;
    }

    private static String convertirAString(int[][] estado) {
        StringBuilder sb = new StringBuilder();
        for (int[] fila : estado) {
            for (int num : fila) {
                sb.append(num);
            }
        }
        return sb.toString();
    }

    private static int[][] copiarEstado(int[][] estado) {
        int[][] copia = new int[3][3];
        for (int i = 0; i < 3; i++)
            System.arraycopy(estado[i], 0, copia[i], 0, 3);
        return copia;
    }

    public static void imprimirCamino(List<int[][]> camino) {
        for (int[][] estado : camino) {
            for (int[] fila : estado) {
                System.out.println(Arrays.toString(fila));
            }
            System.out.println();
        }
    }

    private static int[][] leerMatriz(Scanner scanner, String mensaje) {
        System.out.println(mensaje);
        int[][] matriz = new int[3][3];
        for (int i = 0; i < 3; i++) {
            System.out.print("Fila " + (i + 1) + " (3 números separados por espacio): ");
            for (int j = 0; j < 3; j++) {
                matriz[i][j] = scanner.nextInt();
            }
        }
        return matriz;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int[][] estadoInicial = leerMatriz(scanner, "Introduce la matriz inicial:");
        OBJETIVO = leerMatriz(scanner, "Introduce la matriz final (objetivo):");

        List<int[][]> solucion = resolver(estadoInicial);

        if (solucion != null) {
            System.out.println("\nSolución encontrada:");
            imprimirCamino(solucion);
        } else {
            System.out.println("\nNo se encontró solución.");
        }

        scanner.close();
    }
}
