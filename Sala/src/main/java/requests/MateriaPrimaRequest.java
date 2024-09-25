package requests;


import java.time.LocalDate;
import io.swagger.v3.oas.annotations.media.Schema;


@Schema(description = "Modelo para la creación de materias primas")
public class MateriaPrimaRequest {

	@Schema(description = "Nombre de la materia prima", example = "Azucar", required= true)
	private String nombre;

	@Schema(description = "Fecha de compra de la materia prima", example = "2021-06-15", required = true)
	private LocalDate fechaCompra;
	
	@Schema(description = "Fecha de vencimiento de la materia prima", example = "2021-06-15", required = true)
	private LocalDate fechaVencimiento;
	
	@Schema(description = "Costo por kilogramo de la materia prima", example = "1000", required = true)
	private double costoPorKg;
	
	@Schema(description = "Forma de almacenamiento de la materia prima", example = "Seco", required = true)
	private String formaAlmacenamiento;
	
	
	@Schema(description = "Peso de la materia prima", example = "10", required = true)
	private int peso;
	
	@Schema(description = "nombre del productor de la materia prima", example = "flia", required = true)
	private String nombreProductor;
	
	
	
	public String getNombreProductor() {
		return nombreProductor;
	}

	public double getPeso() {
		return peso;
	}
	

	public LocalDate getFechaCompra() {
		return fechaCompra;
	}
	
	public LocalDate getFechaVencimiento() {
		return fechaVencimiento;
	}
	
	public double getCostoPorKg() {
		return costoPorKg;
	}
	
	
	public String getFormaAlmacenamiento() {
		return formaAlmacenamiento;
	}
	
	
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public void setFechaCompra(LocalDate fecha_compra) {
		this.fechaCompra = fecha_compra;
	}
	
	public void setFechaVencimiento(LocalDate fecha_vencimiento) {
		this.fechaVencimiento = fecha_vencimiento;
	}
	
	public void setCostoPorKg(double costo_por_kg) {
		this.costoPorKg = costo_por_kg;
	}
	
	public void setFormaAlmacenamiento(String forma_almacenamiento) {
		this.formaAlmacenamiento = forma_almacenamiento;
	}
	

	
	
	
	
	

}
