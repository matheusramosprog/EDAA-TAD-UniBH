/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.edaa.tad.unibh;
import java.io.IOException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author matheus
 */
public class TCaixa {
    private static TContaBancaria ActiveAccount;
    private static final Scanner Input = new Scanner(System.in);
    public static void main(String[] args) {
        try {
            while(true) {
                printHeader();
                selectOperation();
                System.out.println();
                System.out.println("Pressione ENTER para voltar ao menu inicial.");
                System.in.read();
            }
        } catch (IOException ex) {
            Logger.getLogger(TCaixa.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    private static void selectOperation() {
        final String operationExit = "0 - Sair da Conta";
        final String operationNewAccount = "1 - Iniciar nova conta";
        final String operationSelectAccount = "2 - Selecionar conta";
        final String operationDeposit = "3 - Depositar valor";
        final String operationRemove = "4 - Retirar valor";
        final String operationBalance = "5 - Exibir saldo";
        final String operationTransfer = "6 - Tranferir valor";
        if (existeClienteAtivo()) {
            System.out.println(
                    String.format(
                            "%s\n%s\n%s\n%s\n%s\n",
                            operationDeposit,
                            operationRemove,
                            operationBalance,
                            operationTransfer,
                            operationExit
                    )
            );
        }
        else {
            System.out.println(
                    String.format(
                            "%s\n%s\n",
                            operationNewAccount,
                            operationSelectAccount
                    )
            );
        }       
        System.out.print("Escolha uma das operações e pressione ENTER: ");
        int selectedOperation = Input.nextInt();
        System.out.println();
        if (existeClienteAtivo()) {
            switch(selectedOperation) {
                case 0 -> exitAccount();
                case 3 -> depositValueAccount();
                case 4 -> withdrawAccountValue();
                case 5 -> displayAccountBalance();
                case 6 -> transferValorAccounts();
                default -> System.out.println("Operação inserida inválida.");
            }        
        }
        else {
            switch(selectedOperation) {
                case 1 -> registerCustomer();
                case 2 -> selectAccount();
                default -> System.out.println("Operação inserida inválida.");             
            }        
        }        
    }
    private static void registerCustomer() {        
        System.out.println("==============CADASTRO=============");
        System.out.println("Informe os dados abaixo");        
        System.out.println("===================================");
        System.out.println("Informe o nome completo:");
        String nome = Input.next();
        System.out.println("Informe o CPF:");
        String cpf = Input.next();   
        System.out.println();
        TContaBancaria openAccount = TGerenciaDeContas.novoCliente(nome, cpf);
        System.out.println(
                String.format(
                        "A conta foi criada com êxito.\nNro. da Conta: %s\nAgência: %s",
                        openAccount.obterNcon(),
                        openAccount.obterNroAgec()
                )
        );
    }
    private static void depositValueAccount() {        
        if (!existeClienteAtivo()) {
            System.out.println("Erro no sistema. Nenhum cliente selecionado.");
            return;
        }
        System.out.println("==============DEPÓSITO=============");
        System.out.println("Informe o valor de depósito desejado");        
        System.out.println("===================================");
        System.out.print("Valor desejado:\nR$");
        double valor = Input.nextDouble();
        TResultadoOperacao resultado = ActiveAccount.depositarValor(valor);
        System.out.println();
        System.out.println(resultado.getMessage());
    }
    private static void withdrawAccountValue() {        
        if (!existeClienteAtivo()) {
            System.out.println("Erro no sistema. Nenhum cliente selecionado.");
            return;
        }
        System.out.println("===============SAQUE==============");
        System.out.println("Informe o valor de saque desejado");        
        System.out.println("===================================");
        System.out.print("Valor desejado:\nR$");
        double valor = Input.nextDouble();
        TResultadoOperacao resultado = ActiveAccount.retirarValor(valor);
        System.out.println();
        System.out.println(resultado.getMessage());
    }
    private static void displayAccountBalance() {                
        if (!existeClienteAtivo()) {
            System.out.println("Erro no sistema. Nenhum cliente selecionado.");
            return;
        }
        System.out.println("================SALDO==============");
        System.out.println("Confira o seu saldo abaixo");        
        System.out.println("===================================");
        System.out.println(
                String.format(
                    "Saldo em conta: R$%s",
                    String.format("%.2f", ActiveAccount.obterSald())
                )
        );
    }
    private static void printHeader() {
        Boolean existeClienteSelecionado = existeClienteAtivo();
        System.out.println("===============CAIXA===============");
        if (existeClienteSelecionado) {
            System.out.println(String.format("Seja bem-vindo, %s", ActiveAccount.obternameCli()));
            System.out.println(String.format("Nro. Conta: %s - Agência: %s", ActiveAccount.obterNcon(), ActiveAccount.obterNroAgec()));
        }
        else 
            System.out.println("Olá, seja bem-vindo");   
        System.out.println("===================================");
        System.out.println("Selecione uma operação:");
    }
    private static void transferValorAccounts() {        
        if (!existeClienteAtivo()) {
            System.out.println("Erro no sistema. Nenhum cliente selecionado.");
            return;
        }
        System.out.println("===========TRANSFERÊNCIA===========");
        if (TGerenciaDeContas.obterQuantidadeContas() < 2) {
            System.out.println("Não existem outros clientes na carteira bancária. Cadastre um novo cliente antes de continuar.");
            return;
        }
        System.out.println("Informe a conta de destino e o valo\nr de transferência desejado");        
        System.out.println("===================================");     
        System.out.println("Contas disponíveis para transferência:");
        System.out.println(TGerenciaDeContas.obterContasTransferencia(ActiveAccount.obterNcon(), ActiveAccount.obterNroAgec()));
        System.out.println("Número da conta de destino:");        
        String nroContaDestino = Input.next();
        System.out.println("Agência da conta de destino:");        
        String nroAgenciaDestino = Input.next();
        System.out.print("Valor desejado:\nR$");        
        double valor = Input.nextDouble();
        TResultadoOperacao resultado = ActiveAccount.transferirValor(nroContaDestino, nroAgenciaDestino, valor);
        System.out.println();
        System.out.println(resultado.getMessage());
    }
    private static void selectAccount() {
        System.out.println("===============ENTRAR===============");
        if (TGerenciaDeContas.obterQuantidadeContas() == 0) {
            System.out.println("Não existem clientes na carteira bancária. Cadastre um novo cliente antes de continuar.");
            return;
        }
        System.out.println(TGerenciaDeContas.obterContasDisponiveis());
        System.out.println("Insira o Número da Conta:");
        String nroConta = Input.next();
        System.out.println("Insira a Agência:");        
        String nroAgencia = Input.next();
        TContaBancaria conta = TGerenciaDeContas.obterContaCliente(nroConta, nroAgencia);
        if (conta == null) {
            System.out.println("Conta não encontrada. Não foi possível prosseguir.");
            return;
        }
        ActiveAccount = conta;
        System.out.println();
        System.out.println("Você entrou na conta com êxito.");
    }   
    private static void exitAccount() {
        System.out.println("===============SAIR===============");
        System.out.println("Você saiu da conta com êxito.");
        ActiveAccount = null;
    }
    private static Boolean existeClienteAtivo() {
        return ActiveAccount != null;
    }
}
