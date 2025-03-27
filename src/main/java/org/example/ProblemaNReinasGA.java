package org.example;

import javax.swing.*;
import java.util.Arrays;
import java.util.Random;

public class ProblemaNReinasGA {
    private static final int TAM_POBLACION = 10;
    private static final int GENERACIONES = 100;
    private static final double PROB_MUTACION = 0.3;

    public static void main(String[] args) {
        int n = Integer.parseInt(JOptionPane.showInputDialog("Ingresa el número de reinas: "));
        System.out.println("Número de reinas: " + n);

        IndividuosReinas[] individuos = new IndividuosReinas[TAM_POBLACION];
        IndividuosReinas[] hijos = new IndividuosReinas[TAM_POBLACION];
        Random random = new Random();

        // Inicialización de la población
        for (int i = 0; i < TAM_POBLACION; i++) {
            int[] valores = generarSolucionAleatoria(n);
            double aptitud = calcularAptitud(valores);
            individuos[i] = new IndividuosReinas(valores, aptitud);
        }

        for (int g = 0; g < GENERACIONES; g++) {
            // Recalcular aptitud
            for (IndividuosReinas individuo : individuos) {
                individuo.setAptitud(calcularAptitud(individuo.getValores()));
            }

            System.out.println("Generación " + (g + 1) + " - Individuos con aptitud:");
            for (IndividuosReinas individuo : individuos) {
                System.out.println(individuo);
            }

            // Selección del mejor individuo (elitismo)
            IndividuosReinas mejorIndividuo = individuos[0];
            for (IndividuosReinas individuo : individuos) {
                if (individuo.getAptitud() > mejorIndividuo.getAptitud()) {
                    mejorIndividuo = individuo;
                }
            }

            // Selección aleatoria para cruza
            int[] seleccion = new int[TAM_POBLACION];
            for (int i = 0; i < TAM_POBLACION; i++) {
                seleccion[i] = random.nextInt(TAM_POBLACION);
            }

            // Cruza por punto de corte
            for (int i = 0; i < TAM_POBLACION; i += 2) {
                IndividuosReinas padre1 = individuos[seleccion[i]];
                IndividuosReinas padre2 = individuos[seleccion[i + 1]];
                int[] hijo1 = cruzar(padre1.getValores(), padre2.getValores());
                int[] hijo2 = cruzar(padre2.getValores(), padre1.getValores());
                hijos[i] = new IndividuosReinas(hijo1, calcularAptitud(hijo1));
                hijos[i + 1] = new IndividuosReinas(hijo2, calcularAptitud(hijo2));
            }

            // Aplicar mutación
            for (int i = 0; i < TAM_POBLACION; i++) {
                if (random.nextDouble() < PROB_MUTACION) {
                    mutar(hijos[i].getValores(), n);
                    hijos[i].setAptitud(calcularAptitud(hijos[i].getValores()));
                }
            }

            // Sustituir población conservando al mejor individuo (elitismo)
            individuos[0] = mejorIndividuo;
            for (int i = 1; i < TAM_POBLACION; i++) {
                individuos[i] = hijos[i];
            }
        }

        System.out.println("Mejor solución encontrada:");
        System.out.println(Arrays.toString(individuos[0].getValores()) + " - Aptitud: " + individuos[0].getAptitud());
        imprimirTablero(individuos[0].getValores());
    }

    private static int[] generarSolucionAleatoria(int n) {
        Random random = new Random();
        int[] solucion = new int[n];
        for (int i = 0; i < n; i++) {
            solucion[i] = random.nextInt(n);
        }
        return solucion;
    }

    private static double calcularAptitud(int[] valores) {
        int n = valores.length;
        int colisiones = 0;

        // Calcular colisiones en las filas y diagonales
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                if (valores[i] == valores[j] || Math.abs(valores[i] - valores[j]) == Math.abs(i - j)) {
                    colisiones++;
                }
            }
        }

        return 1.0 / (1 + colisiones); // Entre menos colisiones, mejor la aptitud
    }

    private static int[] cruzar(int[] padre1, int[] padre2) {
        Random random = new Random();
        int n = padre1.length;
        int[] hijo = new int[n];
        int puntoCorte = random.nextInt(n);

        for (int i = 0; i < n; i++) {
            hijo[i] = (i < puntoCorte) ? padre1[i] : padre2[i];
        }

        return hijo;
    }

    private static void mutar(int[] valores, int n) {
        Random random = new Random();
        int pos = random.nextInt(n);
        valores[pos] = random.nextInt(n);
    }

    private static void imprimirTablero(int[] valores) {
        int n = valores.length;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                System.out.print((valores[i] == j) ? "Q " : ". ");
            }
            System.out.println();
        }
    }
}

