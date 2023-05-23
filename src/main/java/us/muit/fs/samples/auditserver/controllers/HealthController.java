package us.muit.fs.samples.auditserver.controllers;


import java.util.HashMap;

import java.util.Map;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import us.muit.fs.a4i.model.remote.GitHubRepositoryEnquirer;
import us.muit.fs.a4i.model.remote.RemoteEnquirer;
import us.muit.fs.a4i.model.entities.Metric;

import us.muit.fs.samples.auditserver.config.AppProperties;

import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthController {
	@Autowired
	private AppProperties config;
	private static Logger log = Logger.getLogger(HealthController.class.getName());
	
	private String getHealthzGithubRepo()
	{
		return config.getHealthzGithubRepo();
	}
	/*
	Kubernetes usa los recursos (o endpoints) /healthz/livez/readyz para validar el estado del servicio y sus dependencias.
	Al realizar un GET a estos recursos kubernetes puede saber, en cualquier momento, si el servicio esta operativo.
	En caso afirmativo, recibie 200 OK. En cualquier otro caso 500. Este endpoint lo consume directamente la
	readinessProbe de Kubernetes para gestionar de manera automatica el service
	discovery.
	Consultar https://kubernetes.io/docs/reference/using-api/health-checks/
*/
@RequestMapping("/readyz")
@GetMapping(path = "/readyz", produces=MediaType.APPLICATION_JSON_VALUE)
ResponseEntity<Map<String, Object>> healthz() {
	/*
		En esta seccion se evaluan las dependencias del servicio
		Por ejemplo verifica que los backend (en este caso la API de github, a traves de Audit4Improve)
		Para ello se solicita la metrica totalAdditions del repositorio que este configurado en el fichero de configuracion
	*/
	try{
		
		String healthzGithubRepo = this.getHealthzGithubRepo();
		Map<String, Object> body = new HashMap<>();
		RemoteEnquirer remote = new GitHubRepositoryEnquirer();
		
		Metric myMetric = remote.getMetric("totalAdditions",healthzGithubRepo);
	    
		if(((Integer)myMetric.getValue()!=0) && (myMetric.getName()=="totalAdditions")){
			body.put("healthy", true);
			body.put("totalAdditions", myMetric.getValue());
			body.put("metric", myMetric);
			log.fine("La respuesta recibida ha sido: "+myMetric);
		}else {
			log.fine("La respuesta del remoto no se ha recibido bien");
		}
		return ResponseEntity.status(HttpStatus.OK).body(body);
	}	catch(Exception ref) {
		Map<String, Object> body = new HashMap<>();
        log.fine("Se ha recibido esta excepción: "+ref);
		body.put("healthy", false);
		body.put("error", ref.getMessage());
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(body);
	}
}

}
