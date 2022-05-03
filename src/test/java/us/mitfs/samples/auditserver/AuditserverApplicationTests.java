package us.mitfs.samples.auditserver;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Map;

@SpringBootTest
class AuditserverApplicationTests {

	@Autowired
	private HealthController controller;

	@Test
	void contextLoads() {
		assertThat(controller).isNotNull();
	}

}

@SpringBootTest
class SmokeTest {

	@Autowired
	private HealthController controller;

	@Test
	void contextLoads() {
		assertThat(controller).isNotNull();
	}

}

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class SuccessfulHealthzTest {

	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;

	@Test
	public void healthz() throws Exception {
		String endpoint = "http://localhost:" + port + "/healthz";
		Map<String, Object> endpointResponse = this.restTemplate.getForObject(endpoint, Map.class);
		assertThat(endpointResponse).containsKeys("healthy", "check");
		assertThat(endpointResponse.get("healthy")).isEqualTo(true);
	}
}

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT, properties = { "app.githubToken=faketoken" })
class UnsuccessfulHealthzTest {

	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;

	@Test
	public void healthz() throws Exception {
		String endpoint = "http://localhost:" + port + "/healthz";
		Map<String, Object> endpointResponse = this.restTemplate.getForObject(endpoint, Map.class);
		assertThat(endpointResponse).containsKeys("healthy", "error");
		assertThat(endpointResponse.get("healthy")).isEqualTo(false);
	}
}

