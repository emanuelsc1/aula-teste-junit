package org.example.hotel;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ReservaHotel {

    private List<Reserva> reservas;

    public ReservaHotel() {
        this.reservas = new ArrayList<>();
    }


    public Reserva validarReserva(String cliente, Quarto quarto, LocalDate dataEntrada, LocalDate dataSaida) {

        if(dataEntrada.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("Data de entrada não pode ser anterior à data atual");
        }

        //A data de saída deve ser posterior à data de entrada.
        if(!dataSaida.isAfter(dataEntrada)){
            throw new IllegalArgumentException("Data de saída deve ser posterior à data de entrada");

        }

        //O sistema deve validar disponibilidade de quartos.
        for(Reserva reserva : reservas) {
            if(reserva.conflita(quarto, dataEntrada, dataSaida)) {
                throw new IllegalArgumentException("Quarto indisponível para o período informado");
            }
        }


        Reserva reserva = new Reserva(cliente, quarto, dataEntrada, dataSaida);
        reservas.add(reserva);
        return reserva;
    }











}
