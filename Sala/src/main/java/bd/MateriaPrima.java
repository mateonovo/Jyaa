package bd;

import java.time.LocalDate;

import javax.persistence.*;



@Entity
@Table(name = "materias_primas")
public class MateriaPrima {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(nullable = false, length = 64, updatable = true)
	private String nombre;

	@Column(nullable = false)
	private double peso;

	@Column(nullable = false, length = 64)
	private LocalDate fechaCompra;

	@Column(nullable = false, length = 64)
	private LocalDate fechaVencimiento;

	@Column(nullable = false)
	private double costoPorKg;

	@Column(nullable = false, length = 64)
	private String formaAlmacenamiento;

	private boolean activo;
	
	{this.activo = true;
	
	}

	@ManyToOne
	@JoinColumn(name = "productor_id",updatable = false)
	private FamiliaProductora productor;

	public MateriaPrima(String nombre, double peso, LocalDate fecha_compra, LocalDate fecha_vencimiento,
			double costo_por_kg, String forma_almacenamiento, FamiliaProductora productor) {
		super();
		this.nombre = nombre;
		this.peso = peso;
		this.fechaCompra = fecha_compra;
		this.fechaVencimiento = fecha_vencimiento;
		this.costoPorKg = costo_por_kg;
		this.formaAlmacenamiento = forma_almacenamiento;
		this.productor = productor;
	}

	public MateriaPrima() {
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public double getPeso() {
		return peso;
	}

	public void setPeso(double peso) {
		this.peso = peso;
	}

	public LocalDate getFechaCompra() {
		return fechaCompra;
	}

	public void setFechaCompra(LocalDate fecha_compra) {
		this.fechaCompra = fecha_compra;
	}

	public LocalDate getFechaVencimiento() {
		return fechaVencimiento;
	}

	public void setFechaVencimiento(LocalDate fecha_vencimiento) {
		this.fechaVencimiento = fecha_vencimiento;
	}

	public double getCostoPorKg() {
		return costoPorKg;
	}

	public void setCostoPorKg(double costo_por_kg) {
		this.costoPorKg = costo_por_kg;
	}

	public String getFormaAlmacenamiento() {
		return formaAlmacenamiento;
	}

	public void setFormaAlmacenamiento(String forma_almacenamiento) {
		this.formaAlmacenamiento = forma_almacenamiento;
	}

	public boolean isActivo() {
		return activo;
	}

	public void setActivo(boolean activo) {
		this.activo = activo;
	}

	public FamiliaProductora getProductor() {
		return productor;
	}

	public void setProductor(FamiliaProductora productor) {
		this.productor = productor;
	}

	public int getId() {
		return id;
	}

}
