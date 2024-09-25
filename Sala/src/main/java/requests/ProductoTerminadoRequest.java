package requests;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Modelo para la creación de productos")
public class ProductoTerminadoRequest {
	
	    private int id;

		@Schema(description = "Nombre del producto", example = "Mermelada de ciruela", required = true)
	    private String nombre;

	    @Schema(description = "Fecha de envasado del producto", example = "2024-06-26", required = true)
	    private LocalDate fechaEnvasado;

	    @Schema(description = "Fecha de vencimiento del producto", example = "2024-10-26", required = true)
	    @JsonProperty("fechaVencimiento")
	    private LocalDate fechaVencimiento;
	    
	    @Schema(description = "Precio de venta del producto", example = "1000", required = true)
	    private Double precioVenta;
	    
	    @Schema(description = "Costo total del producto", example = "1000", required = true)
	    private Double costoTotal;
	    
	    @Schema(description = "Cantidad total de stock", example = "150", required = true)
	    private int cantidadProductos;
	    
	    @Schema(description = "Insumos utilizados", required = true)
	    private List<InsumoRequest> insumos;

		public String getNombre() {
			return nombre;
		}

		public Double getCostoTotal() {
			return costoTotal;
		}

		public void setCostoTotal(Double costoTotal) {
			this.costoTotal = costoTotal;
		}

		public LocalDate getFechaEnvasado() {
			return fechaEnvasado;
		}

		public LocalDate getFechaVencimiento() {
			return fechaVencimiento;
		}

		public Double getPrecioVenta() {
			return precioVenta;
		}

		public int getCantidadProductos() {
			return cantidadProductos;
		}
	    
		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		public List<InsumoRequest> getInsumos() {
			return insumos;
		}

		public void setInsumos(List<InsumoRequest> insumos) {
			this.insumos = insumos;
		}
		
		
	    

}
