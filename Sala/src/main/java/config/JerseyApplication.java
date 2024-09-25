package config;

import org.glassfish.jersey.server.ResourceConfig;
import jakarta.ws.rs.ApplicationPath;


@ApplicationPath("/api")
public class JerseyApplication extends ResourceConfig {

	public JerseyApplication() {
		register(new MyApplicationBinder());
		packages(true, "api");
	}

}