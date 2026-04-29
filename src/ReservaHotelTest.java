package org.example.hotel;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;

public class ReservaHotelTest {

    private ReservaHotel reservaHotel;
    private Quarto quarto;

    @BeforeEach
    void setup(){
        reservaHotel = new ReservaHotel();
        quarto = new Quarto(10);
    }

    @ParameterizedTest
    @DisplayName("Criar reserva válida")
    @CsvSource({
            "2026-04-30, 2026-05-01",
            "2026-05-10, 2026-05-20",
            "2026-05-15, 2026-05-20",
    })
    void criarReservaValida(String dataDe, String dataAte){
        Reserva novaReserva = reservaHotel.validarReserva(
                "ClienteA",
                quarto,
                LocalDate.parse(dataDe),
                LocalDate.parse(dataAte)
        );
        assertTrue(novaReserva.isAtiva(), "A reserva deve iniciar como ativa");
    }


    @ParameterizedTest
    @DisplayName("Rejeitar datas inválidas")
    @CsvSource({
            "2026-04-01, 2026-04-10, Data de entrada não pode ser anterior à data atual",
            "2026-05-20, 2026-05-10, Data de saída deve ser posterior à data de entrada"
    })
    void rejeitarDatasInvalidas(
            String entrada, String saida, String mensagemEsperada
    ){
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            reservaHotel.validarReserva(
                    "ClienteA",
                    quarto,
                    LocalDate.parse(entrada),
                    LocalDate.parse(saida)
            );
        });
        assertEquals(
                mensagemEsperada,
                exception.getMessage()
        );
    }

    @ParameterizedTest
    @DisplayName("Rejeitar conflito de reservas")
    @CsvSource({
            "2026-05-12, 2026-05-18",
            "2026-05-11, 2026-05-14",
            "2026-05-01, 2026-05-12",
            "2026-05-11, 2026-05-14"
    })
    void rejeitarConflitoDeReservas(String entrada, String saida){

        reservaHotel.validarReserva(
                "ClienteA",
                quarto,
                LocalDate.of(2026, 5, 10),
                LocalDate.of(2026, 5, 15)
        );

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> reservaHotel.validarReserva(
                        "ClienteB",
                        quarto,
                        LocalDate.parse(entrada),
                        LocalDate.parse(saida)
                )
        );

        assertEquals(
                "Quarto indisponível para o período informado",
                exception.getMessage()
        );

    }

    @ParameterizedTest
    @CsvSource({
            "2026-05-01, 2026-05-10, 2026-05-11, 2026-05-12",
            "2026-05-20, 2026-05-21, 2026-05-21, 2026-05-22",
            "2026-10-10, 2026-10-15, 2026-10-20, 2026-10-30",
            "2026-11-01, 2026-11-02, 2026-11-03, 2026-11-04"
    })
    @DisplayName("Permitir reservas em períodos distintoso")
    void permitirReservasEmPeriodosDistintos(
            String entradaA, String saidaA, String entradaB, String saidaB){
        Reserva reservaA = reservaHotel.validarReserva(
                "ClienteA",
                quarto,
                LocalDate.parse(entradaA),
                LocalDate.parse(saidaA)
        );
        Reserva reservaB = reservaHotel.validarReserva(
                "ClienteB",
                quarto,
                LocalDate.parse(entradaB),
                LocalDate.parse(saidaB)
        );
        assertTrue(reservaA.isAtiva(), "A primeira reserva está ativa");
        assertTrue(reservaB.isAtiva(), "A segunda reserva está ativa");
    }

    @Test
    @DisplayName("Cancelamento deve liberar o quarto")
    void cancelamentoDeveLiberarOQuarto(){

        Reserva reserva = reservaHotel.validarReserva(
                "ClienteA",
                quarto,
                LocalDate.of(2026, 5, 10),
                LocalDate.of(2026, 5, 15)
        );
        assertTrue(reserva.isAtiva(), "A primeira reserva está ativa");
        reserva.cancelar();
        assertFalse(reserva.isAtiva(), "A reserva deve estar cancelada");
        Reserva novaReserva = reservaHotel.validarReserva(
                "ClienteB",
                quarto,
                LocalDate.of(2026, 5, 10),
                LocalDate.of(2026, 5, 15)
        );
        assertTrue(novaReserva.isAtiva(), "A nova reserva deve estar ativa");
    }
}
