package us.mitfs.samples.auditserver;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.spotify.github.v3.clients.GitHubClient;
import com.spotify.github.v3.clients.RepositoryClient;

import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthController {
	@Autowired
 	AppProperties config;

	private String getGithubToken()
	{
		return config.getGithubToken();
	}

	private String getGithubApiUrl()
	{
		return config.getGithubApiUrl();
	}

	private String getHealthzGithubOrg()
	{
		return config.getHealthzGithubOrg();
	}

	private String getHealthzGithubRepo()
	{
		return config.getHealthzGithubRepo();
	}
	/*
	/healthz endpoint valida el estado del servicio y sus dependencias,
	y reporta en todo momento si el servicio esta operativo y acepta
	solicitudes HTTP. En caso afirmativo, devuelve 200 OK. En cualquier
	otro caso devuelve 500. Este endpoint lo consume directamente la
	readinessProbe de Kubernetes para gestionar de manera automatica el service
	discovery.
*/
@RequestMapping("/healthz")
@GetMapping(path = "/healthz", produces=MediaType.APPLICATION_JSON_VALUE)
ResponseEntity<Map<String, Object>> healthz() {
	/*
		En esta seccion se evaluan las dependencias del servicio
	*/
	try{
		String githubToken = this.getGithubToken();
		String githubApiUrl = this.getGithubApiUrl();
		String healthzGithubOrg = this.getHealthzGithubOrg();
		String healthzGithubRepo = this.getHealthzGithubRepo();
		Map<String, Object> body = new HashMap<>();
		final GitHubClient githubClient = GitHubClient.create(URI.create(githubApiUrl), githubToken);
		final RepositoryClient repositoryClient = githubClient.createRepositoryClient(healthzGithubOrg, healthzGithubRepo);
		repositoryClient.getCommitStatus("main").thenAccept(commitStatus -> {
			body.put("check", commitStatus.sha());
		}).get();

		body.put("healthy", true);
		
		return ResponseEntity.status(HttpStatus.OK).body(body);
	}	catch(Exception ref) {
		Map<String, Object> body = new HashMap<>();

		body.put("healthy", false);
		body.put("error", ref.getMessage());
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(body);
	}
}

}
