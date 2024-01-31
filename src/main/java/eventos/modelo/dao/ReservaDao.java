package eventos.modelo.dao;

import java.util.List;

import eventos.modelo.entitis.Reserva;

public interface ReservaDao {
	
	int cancelarReserva(int idReserva);
	Reserva buscarUna(int idReserva);
	
	boolean reservarEvento(Reserva reserva); // deprecar porque solo utiliza 2 posibilidades
	
	/*
	 * 0 -> todo bien
	 * 1 -> error: aforo maximo excedido
	 * 2 -> error: ya existe una reserva con el nombre
	 * 3 -> error: cantidad de entradas mayor de 10
	 */
	int reservarEventoV2(Reserva reserva); // devuelve los 4 posibles retornos del enunciado
	
}
