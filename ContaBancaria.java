package org.example;

public class ContaBancaria {

    private double saldo;

    public void depositar(double valor) {
        saldo += valor;
    }

    public void sacar(double valor) {

        if (valor <= 0) {
            throw new IllegalArgumentException("Valor do saque deve ser positivo");
        }

        if (valor > saldo) {
            throw new IllegalArgumentException("Saldo insuficiente");
        }

        saldo -= valor;
    }

    public double getSaldo() {
        return saldo;
    }


}
