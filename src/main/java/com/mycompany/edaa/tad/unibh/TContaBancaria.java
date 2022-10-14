/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.edaa.tad.unibh;

/**
 *
 * @author matheus
 */
public class TContaBancaria {
     public TContaBancaria(String Ncons, String agencia, String nameClit, String cpfCliente) {
        this.Ncon = Ncons;
        this.Agec = agencia;
        this.nameCli = nameClit;
        this.cpfCli = cpfCliente;
        Sald = 0;
    }
    private final String nameCli;
    private final String cpfCli;
    private final String Ncon;
    private final String Agec;
    private double Sald;
    public Boolean valorEstaDisponivel(double valorDesejado) {
        return valorDesejado < Sald;
    }
    public String obternameCli() {
        return nameCli;
    }
    public String obtercpfCli() {
        return cpfCli;
    }
    public String obterNcon() {
        return Ncon;
    }
    public String obterNroAgec() {
        return Agec;
    }
    public double obterSald() {
        return Sald;
    }
    public TResultadoOperacao depositarValor(double valor) {
        if (valor < 0)
            return new TResultadoOperacao(false, "O valor inserido para depósito é inválido. Tente novamente.");
        
        this.Sald += valor;
        
        return new TResultadoOperacao(
                true, 
                String.format("Valor depositado com êxito. Seu saldo agora é de R$%.2f.", Sald)
        );
    }
    public TResultadoOperacao retirarValor(double valor) {
        if (valor < 0)
            return new TResultadoOperacao(false, "O valor inserido para retirada é inválido. Tente novamente.");
                
        if (!valorEstaDisponivel(valor))
            return new TResultadoOperacao(false, "Você não tem saldo suficiente.");
        
        this.Sald -= valor;
        
        return new TResultadoOperacao(
                true, 
                String.format("Valor retirado com êxito. Seu saldo agora é de R$%.2f.", this.Sald)
        );
    }
    public TResultadoOperacao transferirValor(String NconsDestino, String agenciaContaDestino, double valor) {
        return TGerenciaDeContas.transferir(Ncon, Agec, NconsDestino, agenciaContaDestino, valor);
    }
}
