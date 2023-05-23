package us.muit.fs.samples.auditserver;

import org.junit.jupiter.api.Test;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;

import org.springframework.boot.test.web.server.LocalServerPort;

import us.muit.fs.samples.auditserver.controllers.HealthController;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Map;


@SpringBootTest(classes = us.muit.fs.samples.auditserver.AuditserverApplication.class)
class AuditserverApplicationTests {

	@Autowired
	private HealthController controller;

	@Test
	void contextLoads() {
		assertNotNull(controller,"El controlador no se ha cargado");
	}
	
}

@SpringBootTest(classes = us.muit.fs.samples.auditserver.AuditserverApplication.class)
class SmokeTest {

	@Autowired
	private HealthController controller;

	@Test
	void contextLoads() {
		assertThat(controller).isNotNull();
	}

}
@SpringBootTest(classes = us.muit.fs.samples.auditserver.AuditserverApplication.class,webEnvironment = WebEnvironment.RANDOM_PORT)
class SuccessfulHealthzTest {

	@LocalServerPort
	private int port;
	@Autowired
	private TestRestTemplate restTemplate;
	 
	private Logger log = Logger.getLogger(SuccessfulHealthzTest.class.getName());



	@Test
	public void healthz() throws Exception {
		assertNotNull(port,"port no tiene valor");		
		log.info("El puerto al que me dirijo es "+port);
		String endpoint = "http://localhost:" + port + "/readyz";
		Map<String, Object> endpointResponse = this.restTemplate.getForObject(endpoint, Map.class);
		log.info("Esta es la respuesta "+endpointResponse);
		assertThat(endpointResponse).containsKeys("healthy", "totalAdditions", "metric");
		assertThat(endpointResponse.get("healthy")).isEqualTo(true);
	}
}

@SpringBootTest(classes = us.muit.fs.samples.auditserver.AuditserverApplication.class,webEnvironment = WebEnvironment.RANDOM_PORT, properties = { "app.healthzGithubRepo=fakerepo" })
class UnsuccessfulHealthzTest {

	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;

	@Test
	public void healthz() throws Exception {
		String endpoint = "http://localhost:" + port + "/readyz";
		Map<String, Object> endpointResponse = this.restTemplate.getForObject(endpoint, Map.class);
		assertThat(endpointResponse).containsKeys("healthy", "error");
		assertThat(endpointResponse.get("healthy")).isEqualTo(false);
	}
}

