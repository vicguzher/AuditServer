package us.mitfs.samples.auditserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
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
