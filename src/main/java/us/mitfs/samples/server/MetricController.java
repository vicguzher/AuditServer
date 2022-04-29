/**
 * 
 */
package us.mitfs.samples.server;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Isabel Román
 *
 */
@RestController
public class MetricController {
		
		@GetMapping("/metric")
		public MetricInfo metric(@RequestParam(value = "name", defaultValue = "all") String name) {
			return new MetricInfo(name);
		
	}
}
