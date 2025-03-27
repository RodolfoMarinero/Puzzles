package org.example;

import java.util.Arrays;
public class Individuo {
    private int[] binario;
    private double decimal;
    private double aptitud;
    public Individuo(int[] binario, double decimal, double aptitud) {
        this.binario = binario;
        this.decimal = decimal;
        this.aptitud = aptitud;
    }

    public int[] getBinario() {
        return binario;
    }

    public double getDecimal() {
        return decimal;
    }
    public double getAptitud() {
        return aptitud;
    }

    public void setDecimal(double decimal) {
        this.decimal = decimal;
    }
    public void setAptitud(double aptitud) {
        this.aptitud = aptitud;
    }
    @Override
    public String toString() {
        return "Binario: " + Arrays.toString(binario) + ", Decimal: " + decimal + ", Aptitud: " + aptitud;
    }
 }