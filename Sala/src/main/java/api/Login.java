package api;

import java.util.Date;
import java.util.List;

import javax.crypto.SecretKey;
import javax.persistence.PersistenceException;
import org.hibernate.PropertyValueException;
import org.hibernate.exception.ConstraintViolationException;
import org.json.JSONObject;

import bd.Usuario;
import dao.UsuarioDAO;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import requests.UsuarioLogin;



@Path("/login")
public class Login {

	
private static final SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
	
	
	@Inject
	private UsuarioDAO userDao;
	
@POST
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Operation(summary = "Iniciar sesión")
@ApiResponses(value = { @ApiResponse(responseCode = "201", description = "Inicio de sesión exitoso"),
		@ApiResponse(responseCode = "401", description = "Inicio de sesión fallido") })
public Response Login(UsuarioLogin usuario) {
	Usuario usuarioLogin = userDao.login(usuario.getEmail(), usuario.getPassword());
	if (usuarioLogin != null) {
    String jwt = Jwts.builder().signWith(this.getSecretKey()).
 		   setSubject(usuario.getEmail()).
 		   setIssuedAt(new Date()).
 		   claim("permisos", usuarioLogin.getPermisos()).
 		   compact();
    String json = new JSONObject().put("token", jwt).toString();
    return Response.status(Response.Status.CREATED).entity(json).build();
	}
	 String errorJson = new JSONObject().put("error", "Usuario o contraseña incorrectos").toString();
	 return Response.status(Response.Status.UNAUTHORIZED).entity(errorJson).build();
	}


	public SecretKey getSecretKey() {
		return key;
	}
	
}





