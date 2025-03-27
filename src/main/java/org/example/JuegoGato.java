package org.example;

import java.util.Random;
import java.util.Scanner;

public class JuegoGato {
    private static final char JUGADOR = 'X';
    private static final char CPU = 'O';
    private static final char VACIO = '-';
    private static final int TAMANO = 3;
    private static char[][] tablero = new char[TAMANO][TAMANO];
    private static Scanner scanner = new Scanner(System.in);
    private static Random random = new Random();

    public static void main(String[] args) {
        inicializarTablero();
        System.out.println("Selecciona el modo de juego:");
        System.out.println("1. Modo Fácil (CPU aleatoria)");
        System.out.println("2. Modo Difícil (CPU con heurística)");
        int opcion = scanner.nextInt();

        mostrarTablero();

        while (true) {
            turnoJugador();
            if (verificarGanador(JUGADOR)) {
                System.out.println("¡Ganaste!");
                break;
            }
            if (tableroLleno()) {
                System.out.println("¡Empate!");
                break;
            }

            if (opcion == 1) {
                turnoCpuFacil();
            } else {
                turnoCpuDificil();
            }

            mostrarTablero();

            if (verificarGanador(CPU)) {
                System.out.println("La CPU ganó.");
                break;
            }
            if (tableroLleno()) {
                System.out.println("¡Empate!");
                break;
            }
        }
    }

    private static void inicializarTablero() {
        for (int i = 0; i < TAMANO; i++) {
            for (int j = 0; j < TAMANO; j++) {
                tablero[i][j] = VACIO;
            }
        }
    }

    private static void mostrarTablero() {
        for (int i = 0; i < TAMANO; i++) {
            for (int j = 0; j < TAMANO; j++) {
                System.out.print(tablero[i][j] + " ");
            }
            System.out.println();
        }
    }

    private static void turnoJugador() {
        int fila, columna;
        do {
            System.out.print("Ingresa fila (0-2): ");
            fila = scanner.nextInt();
            System.out.print("Ingresa columna (0-2): ");
            columna = scanner.nextInt();
        } while (!esMovimientoValido(fila, columna));
        tablero[fila][columna] = JUGADOR;
    }

    private static boolean esMovimientoValido(int fila, int columna) {
        return fila >= 0 && fila < TAMANO && columna >= 0 && columna < TAMANO && tablero[fila][columna] == VACIO;
    }

    private static void turnoCpuFacil() {
        int fila, columna;
        do {
            fila = random.nextInt(TAMANO);
            columna = random.nextInt(TAMANO);
        } while (!esMovimientoValido(fila, columna));
        tablero[fila][columna] = CPU;
        System.out.println("CPU jugó en: (" + fila + ", " + columna + ")");
    }

    private static boolean verificarGanador(char jugador) {
        for (int i = 0; i < TAMANO; i++) {
            if (tablero[i][0] == jugador && tablero[i][1] == jugador && tablero[i][2] == jugador) return true;
            if (tablero[0][i] == jugador && tablero[1][i] == jugador && tablero[2][i] == jugador) return true;
        }
        if (tablero[0][0] == jugador && tablero[1][1] == jugador && tablero[2][2] == jugador) return true;
        if (tablero[0][2] == jugador && tablero[1][1] == jugador && tablero[2][0] == jugador) return true;
        return false;
    }

    private static boolean tableroLleno() {
        for (int i = 0; i < TAMANO; i++) {
            for (int j = 0; j < TAMANO; j++) {
                if (tablero[i][j] == VACIO) return false;
            }
        }
        return true;
    }

    private static void turnoCpuDificil() {
        int mejorPuntaje = Integer.MIN_VALUE;
        int mejorFila = -1, mejorColumna = -1;

        for (int i = 0; i < TAMANO; i++) {
            for (int j = 0; j < TAMANO; j++) {
                if (tablero[i][j] == VACIO) {
                    tablero[i][j] = CPU;
                    int puntaje = minimax(false);
                    tablero[i][j] = VACIO;
                    if (puntaje > mejorPuntaje) {
                        mejorPuntaje = puntaje;
                        mejorFila = i;
                        mejorColumna = j;
                    }
                }
            }
        }
        tablero[mejorFila][mejorColumna] = CPU;
        System.out.println("CPU jugó en: (" + mejorFila + ", " + mejorColumna + ")");
    }

    private static int minimax(boolean esMax) {
        if (verificarGanador(CPU)) return 10;
        if (verificarGanador(JUGADOR)) return -10;
        if (tableroLleno()) return 0;

        int mejorPuntaje = esMax ? Integer.MIN_VALUE : Integer.MAX_VALUE;

        for (int i = 0; i < TAMANO; i++) {
            for (int j = 0; j < TAMANO; j++) {
                if (tablero[i][j] == VACIO) {
                    tablero[i][j] = esMax ? CPU : JUGADOR;
                    int puntaje = minimax(!esMax);
                    tablero[i][j] = VACIO;
                    mejorPuntaje = esMax ? Math.max(mejorPuntaje, puntaje) : Math.min(mejorPuntaje, puntaje);
                }
            }
        }

        return mejorPuntaje;
    }
}
