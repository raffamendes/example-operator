package com.raffamendes.cicdmetrics.operator.util;

import java.util.Arrays;

import javax.enterprise.context.ApplicationScoped;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import io.fabric8.kubernetes.api.model.Container;
import io.fabric8.kubernetes.api.model.ObjectMeta;
import io.fabric8.kubernetes.api.model.Pod;
import io.fabric8.kubernetes.api.model.PodSpec;

@ApplicationScoped
public class LoadPodSpec {
	
	@ConfigProperty(name = "pod.spec.containers.containerName")
	private String containerName;
	
	@ConfigProperty(name = "pod.spec.containers.image")	
	private String image;
	
	@ConfigProperty(name = "pod.spec.containers.command")
	private String command;
	
	@ConfigProperty(name = "pod.spec.containers.args")
	private String args;
	
	@ConfigProperty(name = "pod.metadata.name")
	private String podName;
	
	@ConfigProperty(name = "pod.apiVersion")
	private String apiVersion;
	
	
	
	private PodSpec loadPodSpec() {
		PodSpec spec = new PodSpec();
		spec.setContainers(Arrays.asList(new Container[] {createContainer()}));
		return spec;
	}
	
	//TODO change from this manual approach to load from a yaml file from classpath
	public Pod instantiatePod(String namespace) {
		Pod p = new Pod();
		p.setApiVersion(apiVersion);
		p.setMetadata(createMeta(namespace));
		p.setSpec(loadPodSpec());
		return p;
	}
	
	private ObjectMeta createMeta(String namespace) {
		ObjectMeta meta = new ObjectMeta();
		meta.setName(podName);
		meta.setNamespace(namespace);
		return meta;
	}
	
	
	private Container createContainer() {
		Container c = new Container();
		c.setName(containerName);
		c.setImage(image);
		c.setCommand(Arrays.asList(new String[] {command}));
		c.setArgs(Arrays.asList(new String[] {args}));
		return c;
	}

}
