package us.mitfs.samples.auditserver;


import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import us.muit.fs.a4i.config.Context;

@RestController
public class MetricController {
		@GetMapping("/metricsInfo")
		public HashMap<String,MetricInfo> allMetrics() throws FileNotFoundException, IOException {
			HashMap<String,MetricInfo> metrics=new HashMap<String,MetricInfo>();

			List<String> metricsNames=Context.getContext().getChecker().listAllMetrics();
			for(String name:metricsNames) {
				metrics.put(name,new MetricInfo(name));
			}
			return metrics;

	}
		@GetMapping("/metricsInfo/{name}")
		public MetricInfo metric(@PathVariable String name) {
			return new MetricInfo(name);

	}
}
