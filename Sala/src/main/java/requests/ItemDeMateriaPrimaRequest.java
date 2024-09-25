package requests;

import io.swagger.v3.oas.annotations.media.Schema;

public class ItemDeMateriaPrimaRequest {

	@Schema(description = "ID de la materia prima referida", example = "4", required = true)
	private int materiaPrimaId;
	@Schema(description = "Cantidad en Kg de la materia utilizada", example = "5", required = true)
    private int cantidadEnKg;
    
    
	public int getMateriaPrimaId() {
		return materiaPrimaId;
	}
	public void setMateriaPrimaId(int materiaPrimaId) {
		this.materiaPrimaId = materiaPrimaId;
	}
	public int getCantidadEnKg() {
		return cantidadEnKg;
	}
	public void setCantidadEnKg(int cantidadEnKg) {
		this.cantidadEnKg = cantidadEnKg;
	}
    
    

}
