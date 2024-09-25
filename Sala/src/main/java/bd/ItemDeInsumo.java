package bd;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "items_de_insumos")
public class ItemDeInsumo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(nullable = false, length = 64)
	private int cantidad;

	@ManyToOne
	@JoinColumn(name = "stock_id", updatable = false)
	@JsonBackReference
	private StockProductoTerminado stock;

	@ManyToOne()
	@JoinColumn(name = "insumo_id", updatable = false)
	private Insumo insumo;

	private boolean activo;

	public ItemDeInsumo() {

	}

	public ItemDeInsumo(int cantidad, StockProductoTerminado stock, Insumo insumo) {
		this.cantidad = cantidad;
		this.stock = stock;
		this.insumo = insumo;
		this.activo = true;
	}

	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

	public StockProductoTerminado getStock() {
		return stock;
	}

	public void setStock(StockProductoTerminado stock) {
		this.stock = stock;
	}

	public Insumo getInsumo() {
		return insumo;
	}

	public void setInsumo(Insumo insumo) {
		this.insumo = insumo;
	}

	public boolean isActivo() {
		return activo;
	}

	public void setActivo(boolean activo) {
		this.activo = activo;
	}


}