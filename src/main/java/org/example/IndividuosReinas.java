package org.example;

import java.util.Arrays;

class IndividuosReinas {
    private int[] valores;
    private double aptitud;

    public IndividuosReinas(int[] valores, double aptitud) {
        this.valores = Arrays.copyOf(valores, valores.length);
        this.aptitud = aptitud;
    }

    public int[] getValores() {
        return valores;
    }

    public void setAptitud(double aptitud) {
        this.aptitud = aptitud;
    }

    public double getAptitud() {
        return aptitud;
    }

    @Override
    public String toString() {
        return "Valores: " + Arrays.toString(valores) + " - Aptitud: " + aptitud;
    }
}