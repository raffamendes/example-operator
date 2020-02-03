package com.raffamendes.cicdmetrics.operator.provider;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.enterprise.inject.Produces;
import javax.inject.Named;
import javax.inject.Singleton;

import com.raffamendes.cicdmetrics.operator.cr.ExampleResource;
import com.raffamendes.cicdmetrics.operator.cr.ExampleResourceDoneable;
import com.raffamendes.cicdmetrics.operator.cr.ExampleResourceList;

import io.fabric8.kubernetes.api.model.apiextensions.CustomResourceDefinition;
import io.fabric8.kubernetes.client.KubernetesClient;
import io.fabric8.kubernetes.client.dsl.NonNamespaceOperation;
import io.fabric8.kubernetes.client.dsl.Resource;
import io.fabric8.kubernetes.internal.KubernetesDeserializer;

public class ClientProvider {

	@Produces
	@Singleton
	@Named("namespace")
	private String findNamespace() throws IOException {
		return new String(Files.readAllBytes(Paths.get("/var/run/secrets/kubernetes.io/serviceaccount/namespace")));
	}


	@Produces
	@Singleton
	NonNamespaceOperation<ExampleResource, ExampleResourceList, ExampleResourceDoneable, Resource<ExampleResource, ExampleResourceDoneable>> makeCustomResourceClient(KubernetesClient defaultClient, @Named("namespace") String namespace) {

		KubernetesDeserializer.registerCustomKind("rmendes.com/v1alpha1", "Example", ExampleResource.class);

		CustomResourceDefinition crd = defaultClient
				.customResourceDefinitions()
				.list()
				.getItems()
				.stream()
				.filter(d -> "examples.rmendes.com".equals(d.getMetadata().getName()))
				.findAny()
				.orElseThrow(
						() -> new RuntimeException("Deployment error: Custom resource definition examples.rmendes.com not found."));

		return defaultClient
				.customResources(crd, ExampleResource.class, ExampleResourceList.class, ExampleResourceDoneable.class)
				.inNamespace(namespace);
	}

}
