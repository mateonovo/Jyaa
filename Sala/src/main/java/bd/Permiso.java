package bd;

import javax.persistence.*;

@Entity

@Table(name = "permisos")
public class Permiso {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(unique = true, nullable = false, length = 64, name = "titulo", updatable = true)
	private String titulo;

	public Permiso() {
	}

	public Permiso(String titulo) {
		this.titulo = titulo;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public int getId() {
		return this.id;
	}

}
