package requests;

import io.swagger.v3.oas.annotations.media.Schema;

public class InsumoRequest {

    @Schema(description = "Cantidad del insumo", example = "50", required = true)
    private int cantidad;
    
    @Schema(description = "ID del insumo", example = "1", required = true)
    private int insumo;

    

	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

	public int getInsumo() {
		return insumo;
	}

	public void setInsumo(int insumo) {
		this.insumo = insumo;
	}
    
    
	
}
