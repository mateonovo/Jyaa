package requests;


import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Modelo para la creación de recetas")
public class RecetaRequest {
	
	@Schema(description = "Nombre de la receta", example = "Mermelada de Tomates", required = true)
	private String nombre;

	@Schema(description = "Contenido de la receta", example = "Ingredientes: ...; Preparacion: ...", required = true)
	private String texto;


	public String getNombre() {
		return nombre;
	}

	public void setNombre(String titulo) {
		this.nombre = titulo;
	}

	public String getTexto() {
		return texto;
	}

	public void setTexto(String texto) {
		this.texto = texto;
	}
	
	
}
