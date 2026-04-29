package org.example.hotel;

import java.time.LocalDate;


public class Reserva {

    private final String cliente;
    private final Quarto quarto;
    private final LocalDate dataEntrada;
    private final LocalDate dataSaida;
    private boolean ativa;

    public Reserva(String cliente, Quarto quarto, LocalDate dataEntrada, LocalDate dataSaida) {
        //Toda reserva deve conter cliente, quarto, data de entrada, data de saída e status.
        this.cliente = cliente;
        this.quarto = quarto;
        this.dataEntrada = dataEntrada;
        this.dataSaida = dataSaida;
        this.ativa = true; // toda reserva começa como ativa
    }

    public boolean conflita(Quarto outroQuarto,
                            LocalDate novaEntrada,
                            LocalDate novaSaida) {
        //Não é permitido criar reservas com datas sobrepostas para o mesmo quarto.

        //Reservas canceladas não bloqueiam o período.
        if (!ativa) {
            return false;
        }

        if (!this.quarto.equals(outroQuarto)) {
            return false;
        }

        return novaEntrada.isBefore(this.dataSaida)
                && novaSaida.isAfter(this.dataEntrada);
    }

    public void cancelar() {
        this.ativa = false;
    }

    public boolean isAtiva() {
        return ativa;
    }
}

