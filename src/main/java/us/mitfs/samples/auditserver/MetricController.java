/**
 * 
 */
package us.mitfs.samples.auditserver;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Isabel Rom�n
 *
 */
@RestController
public class MetricController {
	
		
		@GetMapping("/metrics")
		public MetricInfo metric(@RequestParam(value = "name", defaultValue = "all") String name) {
			return new MetricInfo(name);
		
	}
}
