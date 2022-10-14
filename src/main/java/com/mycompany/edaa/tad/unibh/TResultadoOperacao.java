/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.edaa.tad.unibh;

/**
 *
 * @author matheus
 */
public class TResultadoOperacao {
    public TResultadoOperacao(Boolean sucess, String returning) {
        this.Sucessing = sucess;
        this.Retorning = returning;
    }
    
    private final Boolean Sucessing;
    private final String Retorning;
    
    public String getMessage()  {
        if (this.Sucessing)
            return String.format("Operação realizada com sucesso. %s", Retorning);
        else
            return String.format("Não foi possível completar a operação. %s", Retorning);
    }
}
