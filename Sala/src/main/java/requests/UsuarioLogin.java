package requests;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Modelo para la creación y actualización de usuarios")
public class UsuarioLogin {
	



	    @Schema(description = "Correo electrónico del usuario", example = "jose@gmail.com", required = true)
	    private String email;


	    @Schema(description = "Contraseña del usuario", example = "1234", required = true)
	    private String password;

		public String getEmail() {
			return email;
		}



		public String getPassword() {
			return password;
		}

		public void setEmail(String email) {
			this.email = email;
		}


		public void setPassword(String password) {
			this.password = password;
		}
	    
	    
	   
	    
	    
	}

