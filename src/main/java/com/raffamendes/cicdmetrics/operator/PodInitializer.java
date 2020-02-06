package com.raffamendes.cicdmetrics.operator;

import java.io.IOException;
import java.util.Arrays;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.raffamendes.cicdmetrics.operator.cache.ExampleResourceCache;
import com.raffamendes.cicdmetrics.operator.cr.ExampleResource;

import io.fabric8.kubernetes.api.model.EnvVar;
import io.fabric8.kubernetes.api.model.Pod;
import io.fabric8.kubernetes.client.KubernetesClient;
import io.fabric8.kubernetes.client.Watcher;
import io.quarkus.runtime.StartupEvent;

@ApplicationScoped
public class PodInitializer {


	@Inject
	private KubernetesClient client;

	@Inject
	private ExampleResourceCache cache;
	
	void onStartUp(@Observes StartupEvent _ev) {
		new Thread(this::runReconcile).start();
	}

	private void runReconcile() {
		cache.listThenWatch(this::handleEvent);
	}

	private void handleEvent(Watcher.Action action, String uid) {
		try {
			ExampleResource resource = cache.get(uid);
			if(resource == null){
				return;
			}
			createMessagePod(resource);

		}catch (Exception e) {
			e.printStackTrace();
			System.exit(-1);
		}
	}
	
	
	private void createMessagePod(ExampleResource resource) throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		Pod p = mapper.readValue(getClass().getResourceAsStream("/pod.json"), Pod.class);
		EnvVar var = new EnvVar();
		var.setName("MESSAGE");
		var.setValue(String.join(" ", resource.getSpec().getMessageParts()));
		p.getSpec().getContainers().get(0).setEnv(Arrays.asList(new EnvVar[] {var}));
		client.pods().inNamespace(resource.getSpec().getNamespace()).create(p);		
	}
}
