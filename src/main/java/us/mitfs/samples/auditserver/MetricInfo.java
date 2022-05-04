package us.mitfs.samples.auditserver;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;

import us.muit.fs.a4i.config.Context;

public class MetricInfo {

	private final String name;
	private String unit=null;
	private String description=null;
	private String type=null;


	public MetricInfo(String name) {
		this.name = name;
		HashMap<String, String> info;
		try {
			info = Context.getContext().getChecker().getMetricInfo(name);
			if (info!=null) {
				unit=info.get("unit");
				description=info.get("description");
				type=info.get("type");
			}

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		}

	public String getName() {
		return name;
	}

	public String getUnit() {
		return unit;
	}

	public String getDescription() {
		return description;
	}

	public String getType() {
		return type;
	}



}
