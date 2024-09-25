package requests;

import java.time.LocalDate;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;

public class LoteRequest {
	
	@Schema(description = "Código del lote", example = "MFN20240705", required = true)
	private String codigo;
	
	@Schema(description = "Estado del lote", example = "True", required = true)
	private boolean activo;
	
	@Schema(description = "Nombre del lote", example = "Mermelada de Frutilla y naranja", required = true)
    private String nombre;
	
	@Schema(description = "Fecha de elaboración del lote", example = "2024/07/23", required = true)
    private LocalDate fechaElaboracion;
	
	@Schema(description = "Cantidad en KGs de la producción", example = "50", required = true)
    private double cantidadProducida;
	
	@Schema(description = "Código del lote", example = "MFN20240705", required = true)
    private List<ItemDeMateriaPrimaRequest> itemsDeMateriaPrima;
	
	
    
    public String getCodigo() {
		return codigo;
	}
    
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	
	public String getNombre() {
		return nombre;
	}
	
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public LocalDate getFechaElaboracion() {
		return fechaElaboracion;
	}
	
	public void setFechaElaboracion(LocalDate fechaElaboracion) {
		this.fechaElaboracion = fechaElaboracion;
	}
	
	public double getCantidadProducida() {
		return cantidadProducida;
	}
	
	public void setCantidadProducida(double cantidadProducida) {
		this.cantidadProducida = cantidadProducida;
	}
	
	public List<ItemDeMateriaPrimaRequest> getItemsDeMateriaPrima() {
		return itemsDeMateriaPrima;
	}
	
	public void setItemsDeMateriaPrima(List<ItemDeMateriaPrimaRequest> itemsDeMateriaPrima) {
		this.itemsDeMateriaPrima = itemsDeMateriaPrima;
	}

	public boolean isActivo() {
		return activo;
	}

	public void setActivo(boolean activo) {
		this.activo = activo;
	}
	
	

    
}
