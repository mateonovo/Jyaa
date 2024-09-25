package api;

import java.io.IOException;
import org.json.JSONObject;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.annotation.Priority;
import jakarta.ws.rs.Priorities;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;
import api.Login;
import bd.Usuario;

@Provider
@Priority(Priorities.AUTHENTICATION)
public class Intercepter implements ContainerRequestFilter {

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        String url = requestContext.getUriInfo().getRequestUri().toString();
        if (url.contains("login") || url.equals("https://grupo9.java2024.linti.unlp.edu.ar/api/users") || url.equals("http://grupo9.java2024.linti.unlp.edu.ar/api/users") || url.contains("cargarBD")) {
            return;
        }

        String token = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);


        if (token == null || !token.startsWith("Bearer ")) {
            String json = new JSONObject().put("message", "No se encontró el token de autorización").put("url", url).toString();
            requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).entity(json).build());
            return;
        }

        try {
            String jwtToken = token.substring(7);
            Login logi = new Login();
            Claims claims = Jwts.parserBuilder()
                .setSigningKey(logi.getSecretKey())
                .build()
                .parseClaimsJws(jwtToken)
                .getBody();

            
            requestContext.setProperty("userEmail", claims.getSubject());

        } catch (Exception e) {
            String json = new JSONObject().put("message", "Por favor, vuelva a iniciar sesión").toString();
            requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).entity(json).build());
        }
    }
}
