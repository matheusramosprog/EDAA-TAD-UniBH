/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.edaa.tad.unibh;

/**
 *
 * @author matheus
 */
public class TGerenciaDeContas {
     private static TContaBancaria[] CONTAS = new TContaBancaria[0];
    
    public static String obterContasDisponiveis() {
        String resumoContas = "";
        
        for (TContaBancaria conta : CONTAS)
            resumoContas += String.format(
                    "Cliente: %s - CPF: %s - Nro. da Conta: %s - Agência: %s\n",
                    conta.obternameCli(),
                    conta.obtercpfCli(),
                    conta.obterNcon(),
                    conta.obterNroAgec()
            );
        return resumoContas;
    }
    private static Boolean contaExiste(String nroConta, String agencia) {
        for (TContaBancaria contaExistente : CONTAS) {
            if (contaExistente.obterNcon().equals(nroConta) && contaExistente.obterNroAgec().equals(agencia))
                return true;
        }
        return false;
    }
     public static String obterContasTransferencia(String nroContaOrigem, String nroAgenciaOrigem) {
        String resumoContas = "";
        for (TContaBancaria conta : CONTAS)
            if (!conta.obterNcon().equals(nroContaOrigem) && !conta.obterNroAgec().equals(nroAgenciaOrigem))
                resumoContas += String.format(
                        "Cliente: %s - CPF: %s - Nro. da Conta: %s - Agência: %s\n",
                        conta.obternameCli(),
                        conta.obtercpfCli(),
                        conta.obterNcon(),
                        conta.obterNroAgec()
                );
        return resumoContas;
    }
    public static TContaBancaria novoCliente(String nome, String cpf) {
        int tamanhoListaContas = TGerenciaDeContas.CONTAS.length;      
        var listaContasAtualizada = new TContaBancaria[tamanhoListaContas + 1];
        // Move as contas já cadastradas para uma nova lista maior
        System.arraycopy(TGerenciaDeContas.CONTAS, 0, listaContasAtualizada, 0, tamanhoListaContas);
        String novoNroConta = TGerenciaDeContas.gerarNovoNroConta();
        String novoNroAgencia = TGerenciaDeContas.gerarNovoNroAgencia(novoNroConta);
        TContaBancaria novaConta = new TContaBancaria(novoNroConta, novoNroAgencia, nome, cpf);
        listaContasAtualizada[tamanhoListaContas] = novaConta;
        TGerenciaDeContas.CONTAS = listaContasAtualizada;
        return novaConta;
    }
    public static TContaBancaria obterContaCliente(String nroConta, String nroAgencia) {        
        for (TContaBancaria conta : CONTAS) {
            if (conta.obterNcon().equals(nroConta) && conta.obterNroAgec().equals(nroAgencia))
                return conta;
        }
        return null;
    }
    public static int obterQuantidadeContas() {        
        return CONTAS.length;
    }
    public static TResultadoOperacao transferir(String nroContaOrigem, String agenciaContaOrigem, String nroContaDestino, String agenciaContaDestino, double valor) {
        if (valor < 0)
        return new TResultadoOperacao(false, "O valor inserido para transferência é inválido. Tente novamente.");
        TContaBancaria contaOrigem = TGerenciaDeContas.obterContaCliente(nroContaOrigem, agenciaContaOrigem);
        if (!contaOrigem.valorEstaDisponivel(valor))
            return new TResultadoOperacao(false, "Você não tem saldo suficiente.");       
        TContaBancaria contaDestino = TGerenciaDeContas.obterContaCliente(nroContaDestino, agenciaContaDestino);
        if (contaDestino == null)
            return new TResultadoOperacao(false, "A conta de destino da transferência não existe. Tente novamente.");
        contaOrigem.retirarValor(valor);
        contaDestino.depositarValor(valor);
        return new TResultadoOperacao(
                true, 
                String.format("Valor transferido com êxito. Seu saldo agora é de R$%.2f.", contaOrigem.obterSald())
        );
    }
    private static String gerarNovoNroConta() {
        final int menorNumero = 1;
        final int maiorNumero = 999;
        final int numeroPossivel = Utilitarios.GerarNumeroAleatorio(menorNumero, maiorNumero);
        final String novoNroContaGerado = String.format("%03d", numeroPossivel);
        return novoNroContaGerado;
    }
    private static String gerarNovoNroAgencia(String novoNroConta) {
        final int menorNumero = 1;
        final int maiorNumero = 9999;
        
        final int numeroPossivel = Utilitarios.GerarNumeroAleatorio(menorNumero, maiorNumero);
        final String novoNroAgenciaGerado = String.format("%04d", numeroPossivel);
        
        if (TGerenciaDeContas.contaExiste(novoNroConta, novoNroAgenciaGerado))
            return gerarNovoNroAgencia(novoNroConta);
        else
            return novoNroAgenciaGerado;
    }
}
