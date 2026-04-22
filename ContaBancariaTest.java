package org.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.* ;

public class ContaBancariaTest {

    private ContaBancaria conta;

    @Test
    void validarSaque() {
        ContaBancaria contaBancaria = new ContaBancaria();
        contaBancaria.depositar(100.0);
        contaBancaria.sacar(50.0);
        assertEquals(50.0, contaBancaria.getSaldo());
    }

    @Test
    void validarSaqueMaiorQueSaldo() {
        ContaBancaria contaBancaria = new ContaBancaria();
        contaBancaria.depositar(10.0);
        assertThrows(IllegalArgumentException.class, () -> contaBancaria.sacar(50));
    }

    @Test
    void validarSaqueNegativo() {
        ContaBancaria contaBancaria = new ContaBancaria();
        assertThrows(IllegalArgumentException.class, () -> contaBancaria.sacar(-50));
    }
}
