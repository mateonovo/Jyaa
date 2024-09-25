package bd;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;


@Entity
@Table(name = "items_de_materias_primas")
public class ItemDeMateriaPrima {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(nullable = false, length = 64)
	private int cantidadEnKg;

	@ManyToOne
	@JoinColumn(name = "lote_id", updatable = false)
	@JsonBackReference
	private Lote lote;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "materia_prima_id", updatable = false)
	private MateriaPrima materiaPrima;

	private boolean activo;

	public ItemDeMateriaPrima() {
	}

	public ItemDeMateriaPrima(int cantidadEnKg, Lote lote, MateriaPrima materiaPrima) {
		this.cantidadEnKg = cantidadEnKg;
		this.lote = lote;
		this.materiaPrima = materiaPrima;
		this.activo = true;
		materiaPrima.setPeso(materiaPrima.getPeso() - cantidadEnKg);
	}

	public int getCantidadEnKg() {
		return cantidadEnKg;
	}

	public void setCantidadEnKg(int cantidadEnKg) {
		this.cantidadEnKg = cantidadEnKg;
	}

	public Lote getLote() {
		return lote;
	}

	public void setLote(Lote lote) {
		this.lote = lote;
	}

	public MateriaPrima getMateriaPrima() {
		return materiaPrima;
	}

	public void setMateriaPrima(MateriaPrima materiaPrima) {
		this.materiaPrima = materiaPrima;
	}

	public boolean isActivo() {
		return activo;
	}

	public void setActivo(boolean activo) {
		this.activo = activo;
	}
}
