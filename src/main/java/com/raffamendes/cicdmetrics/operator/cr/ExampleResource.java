package com.raffamendes.cicdmetrics.operator.cr;

import io.fabric8.kubernetes.client.CustomResource;

public class ExampleResource extends CustomResource{
	
	private static final long serialVersionUID = -2908084230633655503L;
	
	private ExampleResourceSpec spec;

	public ExampleResourceSpec getSpec() {
		return spec;
	}

	public void setSpec(ExampleResourceSpec spec) {
		this.spec = spec;
	}
	
	
	@Override
	public String toString() {
		String name = getMetadata() != null ? getMetadata().getName() : "unknown";
	    String version = getMetadata() != null ? getMetadata().getResourceVersion() : "unknown";
	    return "name=" + name + " version=" + version + " value=" + spec;
	}

}
