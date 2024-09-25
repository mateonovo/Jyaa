package bd;

import java.time.LocalDate;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import com.fasterxml.jackson.annotation.JsonBackReference;

import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
@Table(name = "lotes")
public class Lote {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(unique = true, nullable = false, length = 32, name = "codigo", updatable = false)
	private String codigo;

	@Column(unique = false, nullable = false, length = 64, name = "nombre")
	private String nombre;

	@Column(unique = false, nullable = false, name = "fecha_elaboracion", updatable = false)
	private LocalDate fechaElaboracion;

	@Column(unique = false, nullable = false, name = "cantidad_producida")
	private double cantidadProducida;

	@Column(unique = false, nullable = false, name = "costo_lote")
	private double costoLote;

	@OneToMany(mappedBy = "lote")
	@LazyCollection(LazyCollectionOption.FALSE)
	@JsonIgnore
	private List<ItemDeMateriaPrima> listaItemsDeMateriaPrima;

	private boolean activo;

	@ManyToOne()
	@JoinColumn(name = "usuario_id", updatable = false)
    @JsonBackReference
	private Usuario usuario;

	public Lote() {

	}
	
	public Lote(String nombre, String codigo, LocalDate fecha_elaboracion, double cantidad_producida,
			Usuario usuario) {
		this.nombre = nombre;
		this.codigo = codigo;
		this.fechaElaboracion = fecha_elaboracion;
		this.cantidadProducida = cantidad_producida;
		this.costoLote = 0;
		this.listaItemsDeMateriaPrima = new ArrayList<ItemDeMateriaPrima>();
		this.activo = true;
		this.usuario = usuario;
	}

	public Lote(String nombre, String codigo, LocalDate fecha_elaboracion, double cantidad_producida, double costo_lote,
			Usuario usuario) {
		this.nombre = nombre;
		this.codigo = codigo;
		this.fechaElaboracion = fecha_elaboracion;
		this.cantidadProducida = cantidad_producida;
		this.costoLote = costo_lote;
		this.listaItemsDeMateriaPrima = new ArrayList<ItemDeMateriaPrima>();
		this.activo = true;
		this.usuario = usuario;
	}
	
	public int getId() {
		return this.id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public LocalDate getFechaElaboracion() {
		return fechaElaboracion;
	}

	public void setFechaElaboracion(LocalDate fecha_elaboracion) {
		this.fechaElaboracion = fecha_elaboracion;
	}

	public double getCantidadProducida() {
		return cantidadProducida;
	}

	public void setCantidadProducida(double cantidad_producida) {
		this.cantidadProducida = cantidad_producida;
	}

	public double getCostoLote() {
		return costoLote;
	}

	public void setCostoLote(double costo_lote) {
		this.costoLote = costo_lote;
	}

	public List<ItemDeMateriaPrima> getMateriaPrima() {
		return listaItemsDeMateriaPrima;
	}

	public void setMateriaPrima(List<ItemDeMateriaPrima> materia_prima) {
		this.listaItemsDeMateriaPrima = materia_prima;
	}

	public boolean isActivo() {
		return activo;
	}

	public void setActivo(boolean activo) {
		this.activo = activo;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

}
