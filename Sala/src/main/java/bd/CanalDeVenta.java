package bd;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "canales_de_venta")
public class CanalDeVenta {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(nullable = false, unique = true)
	private String nombre;

	@Column(nullable = false, length = 64)
	private String ubicacion;
	
    @ManyToMany
    @JoinTable(
        name = "canal_producto",
        joinColumns = @JoinColumn(name = "canalDeVenta_id"),
        inverseJoinColumns = @JoinColumn(name = "producto_id")
    )
    private List<StockProductoTerminado> productos;

	private boolean activo;
	
	{activo = true;
	productos = new ArrayList<StockProductoTerminado>();}

	public CanalDeVenta() {
	}

	public CanalDeVenta(String nombre, String ubicacion) {
		this.nombre = nombre;
		this.ubicacion = ubicacion;
	}

	public void agregarProductoTerminado(StockProductoTerminado producto) {
		this.productos.add(producto);
	}
	
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getUbicacion() {
		return ubicacion;
	}

	public void setUbicacion(String ubicacion) {
		this.ubicacion = ubicacion;
	}

	public boolean isActivo() {
		return activo;
	}

	public void setActivo(boolean activo) {
		this.activo = activo;
	}

	public Integer getId() {
		return id;
    }

	public List<StockProductoTerminado> getProductos() {
		return productos;
	}
	
	

}
