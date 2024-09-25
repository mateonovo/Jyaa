package bd;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

@Entity
@Table(name = "insumos")
public class Insumo {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonProperty(access = Access.READ_WRITE)
	private int id;

	@Column(unique = true, nullable = false, length = 64, updatable = true)
	private String nombre;

	@Column(unique = false, nullable = false, updatable = true)
	private int cantidad;

	@Column(unique = false, nullable = false, updatable = true)
	private double costoUnitario;

	@JsonIgnore
	private boolean activo;

	{
        // Bloque de inicialización
        this.activo = true;
    }
	
	public Insumo() {}

	public Insumo(String nombre, int cantidad, double costo_unitario) {
		this.nombre = nombre;
		this.cantidad = cantidad;
		this.costoUnitario = costo_unitario;
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

	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

	public double getCostoUnitario() {
		return costoUnitario;
	}

	public void setCostoUnitario(double costo_unitario) {
		this.costoUnitario = costo_unitario;
	}

	public boolean isActivo() {
		return activo;
	}

	public void setActivo(boolean activo) {
		this.activo = activo;
	}

}
