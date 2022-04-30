package us.mitfs.samples.auditserver;

import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class AuditserverApplication {

	public static void main(String[] args) {
		SpringApplication.run(AuditserverApplication.class, args);
	}

}

// TODO: Utilizar Audit4Improve e imprimir las metricas en la respuesta
@RestController
class MetricsRestController {
	@RequestMapping("/metrics")
	String metrics() {
		return "TODO: Utilizar Audit4Improve e imprimir las metricas en la respuesta\n";
	}
}

/*
	/healthz endpoint valida el estado del servicio y sus dependencias,
	y reporta en todo momento si el servicio esta operativo y acepta
	solicitudes HTTP. En caso afirmativo, devuelve 200 OK. En cualquier
	otro caso devuelve 500. Este endpoint lo consume directamente la
	readinessProbe de Kubernetes para gestionar de manera automatica.
*/
@RestController
class HealthzRestController {
	@RequestMapping("/healthz")
	@GetMapping(path = "/healthz", produces=MediaType.APPLICATION_JSON_VALUE)
	Map<String, Object> metrics() {
		/*
			En esta seccion se deberian evaluar las dependencias del servicio
		*/
		Map<String, Object> response = new HashMap<>();
    response.put("healthy", true);
    return response;
	}
}
