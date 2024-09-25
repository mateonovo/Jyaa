package bd;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "stocks_productos_terminados")
public class StockProductoTerminado {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(nullable = false, length = 64)
	private String nombre;

	@Column(nullable = false)
	private LocalDate fechaEnvasado;

	@Column(nullable = false)
	private double costoTotal;

	@Column(nullable = false)
	private double precioVenta;

	@Column(nullable = false)
	@JsonProperty("fechaVencimiento")
	private LocalDate fechaVencimiento;

	@Column(nullable = false)
	private int cantidadProductos;

	private boolean activo;

	@OneToMany(mappedBy = "stock", fetch = FetchType.LAZY)
	@LazyCollection(LazyCollectionOption.FALSE)
    @JsonManagedReference
	private List<ItemDeInsumo> insumos;

	@OneToOne
	@JoinColumn(name = "lote_id")
	private Lote lote;

    {
        // Bloque de inicialización
		this.insumos = new ArrayList<ItemDeInsumo>();
		this.activo = true;
    }
	
	
	public StockProductoTerminado() {
	}

	public StockProductoTerminado(String nombre, LocalDate fecha_envasado, double precio_venta,
			LocalDate fecha_vencimiento, int cantidad_productos) {
		this.nombre = nombre;
		this.fechaEnvasado = fecha_envasado;
		this.precioVenta = precio_venta;
		this.fechaVencimiento = fecha_vencimiento;
		this.cantidadProductos = cantidad_productos;

	}



	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public LocalDate getFechaEnvasado() {
		return fechaEnvasado;
	}

	public void setFechaEnvasado(LocalDate fecha_envasado) {
		this.fechaEnvasado = fecha_envasado;
	}

	public double getCostoTotal() {
		return costoTotal;
	}

	public void setCostoTotal(double costoTotal) {
		this.costoTotal = costoTotal;
	}

	public double getPrecioVenta() {
		return precioVenta;
	}

	public void setPrecioVenta(double precio_venta) {
		this.precioVenta = precio_venta;
	}

	public LocalDate getFecha_vencimiento() {
		return fechaVencimiento;
	}

	public void setFechaVencimiento(LocalDate fecha_vencimiento) {
		this.fechaVencimiento = fecha_vencimiento;
	}

	public int getCantidadProductos() {
		return cantidadProductos;
	}

	public void setCantidadProductos(int cantidad_productos) {
		this.cantidadProductos = cantidad_productos;
	}

	public boolean isActivo() {
		return activo;
	}

	public void setActivo(boolean activo) {
		this.activo = activo;
	}

	public List<ItemDeInsumo> getInsumos() {
		return insumos;
	}

	public void addInsumos(ItemDeInsumo item) {
		this.insumos.add(item);
	}
	
	public void setInsumos(List<ItemDeInsumo> insumos) {
		this.insumos = insumos;
	}

	public Lote getLote() {
		return lote;
	}

	public void setLote(Lote lote) {
		this.lote = lote;
	}

	public int getId() {
		return this.id;
	}
}