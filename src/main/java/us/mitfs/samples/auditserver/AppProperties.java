package us.mitfs.samples.auditserver;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "app")
class AppProperties {
	private String githubToken;
	private String githubApiUrl;
	private String healthzGithubOrg;
	private String healthzGithubRepo;

	public String getGithubToken() {
		return githubToken;
	}

	public void setGithubToken(String githubToken) {
		this.githubToken = githubToken;
	}

	public String getGithubApiUrl() {
		return githubApiUrl;
	}

	public void setGithubApiUrl(String githubApiUrl) {
		this.githubApiUrl = githubApiUrl;
	}

	public String getHealthzGithubOrg() {
		return healthzGithubOrg;
	}

	public void setHealthzGithubOrg(String healthzGithubOrg) {
		this.healthzGithubOrg = healthzGithubOrg;
	}

	public String getHealthzGithubRepo() {
		return healthzGithubRepo;
	}

	public void setHealthzGithubRepo(String healthzGithubRepo) {
		this.healthzGithubRepo = healthzGithubRepo;
	}
}
