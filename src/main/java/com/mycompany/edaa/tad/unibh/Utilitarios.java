/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.edaa.tad.unibh;

/**
 *
 * @author matheus
 */
public class Utilitarios {
     public static int GerarNumeroAleatorio(int numeroMin, int numeroMax) {
        return (int)(Math.random() * (numeroMax - numeroMin + 1) + numeroMin);
    }
}
