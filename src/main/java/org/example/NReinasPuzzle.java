package org.example;

import java.util.Scanner;

public class NReinasPuzzle {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Ingresa el número de reinas (n): ");
        int n = scanner.nextInt();
        int[] tablero = new int[n];
        System.out.println("Una solución para el problema de las " + n + " reinas:");
        if (!resolver(tablero, 0)) {
            System.out.println("No hay soluciones para el valor de n = " + n);
        }
    }

    public static boolean esValida(int[] tablero, int col) {
        for (int i = 0; i < col; i++) {
            if (tablero[i] == tablero[col] || Math.abs(tablero[i] - tablero[col]) == col - i) {
                return false;
            }
        }
        return true;
    }

    public static boolean resolver(int[] tablero, int col) {
        int n = tablero.length;
        if (col == n) {
            imprimirTablero(tablero);
            return true;
        }
        for (int i = 0; i < n; i++) {
            tablero[col] = i;
            if (esValida(tablero, col)) {
                if (resolver(tablero, col + 1)) {
                    return true; // Detenemos al encontrar la primera solución
                }
            }
        }
        return false; // No se encontró solución en esta rama
    }

    public static void imprimirTablero(int[] tablero) {
        int n = tablero.length;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                System.out.print((tablero[i] == j ? "Q " : "* "));
            }
            System.out.println();
        }
        System.out.println();
    }
}
