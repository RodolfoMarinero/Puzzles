package org.example;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import java.util.Arrays;

public class ClaseAlelos {
    public static void main(String[] args) {
        Individuo[] individuos = new Individuo[10];
        Individuo[] hijos = new Individuo[10];
        Double[] aptitudesElitistas = new Double[10];

        for (int i = 0; i < 10; i++) {
            int[] binario = new int[7];
            for (int j = 0; j < 7; j++) {
                binario[j] = (int) (Math.random() * 2);
            }

            double valorDecimal = calcularValorDecimal(binario);

            double aptitud = calcularAptitud(valorDecimal);

            individuos[i] = new Individuo(binario, valorDecimal, aptitud);
        }



        for (int g = 0; g <10; g++) {
            for (int i = 0; i < 10; i++) {
                individuos[i].setDecimal(calcularValorDecimal(individuos[i].getBinario()));
                individuos[i].setAptitud(calcularAptitud(individuos[i].getDecimal()));
            }

            System.out.println("Individuos con binario, decimal y aptitud:");
            for (Individuo individuo : individuos) {
                System.out.println(individuo);
            }

            Individuo elitismo = individuos[0];
            int indiceElitismo = 0;
            for (int i = 1; i < 10; i++) {
                if (individuos[i].getAptitud() > elitismo.getAptitud()) {
                    elitismo = individuos[i];
                    indiceElitismo = i;
                }
            }

            int[] seleccion = new int[10];
            for (int i = 0; i < 10; i++) {
                seleccion[i] = (int) (Math.random() * 10);
            }
            System.out.println("Seleccion:");
            for (int i = 0; i < 10; i++) {
                System.out.println(seleccion[i]);
            }


            for (int i = 0; i < 10; i += 2) {
                Individuo padre1 = individuos[seleccion[i]];
                Individuo padre2 = individuos[seleccion[i + 1]];
                int[] hijo1 = new int[7];
                int[] hijo2 = new int[7];
                int posicionCorte = (int) (Math.random() * 7);
                for (int j = 0; j < 7; j++) {
                    if (j < posicionCorte) {
                        hijo1[j] = padre1.getBinario()[j];
                        hijo2[j] = padre2.getBinario()[j];
                    } else {
                        hijo1[j] = padre2.getBinario()[j];
                        hijo2[j] = padre1.getBinario()[j];
                    }
                }
                hijos[i] = new Individuo(hijo1, 0, 0);
                hijos[i + 1] = new Individuo(hijo2, 0, 0);
            }

            System.out.println("Hijos:");
            for (int i = 0; i < 10; i++) {
                System.out.println(hijos[i]);
            }


            int seleccionHijos[] = new int[3];
            for (int i = 0; i < 3; i++) {
                seleccionHijos[i] = (int) (Math.random() * 10);
            }

            System.out.println("Seleccion de hijos:");
            for (int i = 0; i < 3; i++) {
                System.out.println(seleccionHijos[i]);
            }


            for (int i = 0; i < 3; i++) {
                int[] hijo = hijos[seleccionHijos[i]].getBinario();
                int posicionMutacion = (int) (Math.random() * 7);
                hijo[posicionMutacion] = hijo[posicionMutacion] == 0 ? 1 : 0;
            }

            System.out.println("Hijos mutados:");
            for (int i = 0; i < 10; i++) {
                System.out.println(hijos[i]);
            }

            individuos[0] = elitismo;
            for (int i = 1; i < 10; i++) {
                individuos[i] = hijos[i];
            }

            System.out.println("Individuos después de la generación " + (g + 1) + ":");
            for (Individuo individuo : individuos) {
                System.out.println(individuo);
            }

            for (int i = 1; i < 10; i++) {
                individuos[i].setDecimal(calcularValorDecimal(individuos[i].getBinario()));
                individuos[i].setAptitud(calcularAptitud(individuos[i].getDecimal()));
            }

            System.out.println("Individuos con binario, decimal y aptitud final:");
            for (Individuo individuo : individuos) {
                System.out.println(individuo);
            }

            System.out.println("El individuo con mayor aptitud es:");
            System.out.println(individuos[0]);

            aptitudesElitistas[g] = individuos[0].getAptitud();

        }

        System.out.println("Aptitudes elitistas:");
        for (int i = 0; i < 10; i++) {
            System.out.println(aptitudesElitistas[i]);
        }


        graficar(aptitudesElitistas);




    }

    private static Double calcularValorDecimal(int[] binario) {
        int signo = binario[0];

        int magnitud = 0;
        for (int j = 1; j < 4; j++) {
            magnitud += binario[j] * Math.pow(2, 3 - j);
        }

        double decimal = 0;
        for (int j = 4; j < 7; j++) {
            decimal += binario[j] * Math.pow(2, 6 - j);
        }
        decimal /= 10.0;

        return signo == 1 ? -(magnitud + decimal) : (magnitud + decimal);
    }

    private static Double calcularAptitud(Double decimal) {
        return Math.sin(3 * decimal) + Math.cos(5 * decimal) + decimal / 10;
    }

    public static void graficar(Double[] aptitudesElitistas) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        for (int i = 0; i < aptitudesElitistas.length; i++) {
            dataset.addValue(aptitudesElitistas[i], "Aptitud", "Generación " + (i + 1));
        }

        JFreeChart chart = ChartFactory.createLineChart(
                "Evolución de Aptitudes Elitistas",
                "Generaciones",
                "Valor de Aptitud",
                dataset
        );

        ChartPanel panel = new ChartPanel(chart);
        JFrame ventana = new JFrame("Gráfica de Aptitudes Elitistas");
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventana.setSize(800, 600);
        ventana.setVisible(true);
        ventana.add(panel);
    }
}
