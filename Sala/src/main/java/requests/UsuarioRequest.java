package requests;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Modelo para la creación y actualización de usuarios")
public class UsuarioRequest {

    @Schema(description = "Correo electrónico del usuario", example = "usuario@example.com", required = true)
    private String email;

    @Schema(description = "Nombre del usuario", example = "Juan", required = true)
    private String nombre;

    @Schema(description = "Apellido del usuario", example = "Perez", required = true)
    private String apellido;

    @Schema(description = "Contraseña del usuario", example = "password123", required = true)
    private String password;

	public String getEmail() {
		return email;
	}

	public String getNombre() {
		return nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public String getPassword() {
		return password;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public void setPassword(String password) {
		this.password = password;
	}
    
    
   
    
    
}