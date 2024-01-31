package eventos.modelo.dao;

import java.util.List;

import eventos.modelo.entitis.Evento;

public interface EventoDao {
	
	List<Evento> buscarTodos();
	Evento buscarEvento(int idEvento);
	List<Evento> verActivo();
	List<Evento> verDestacados();
	List<Evento> mostrarTipo(String nombre);
	int reservarEvento(int idEvento, int cantidad);
	

}
