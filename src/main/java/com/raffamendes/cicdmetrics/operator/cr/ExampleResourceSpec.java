package com.raffamendes.cicdmetrics.operator.cr;

import java.util.Arrays;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import io.quarkus.runtime.annotations.RegisterForReflection;

@JsonDeserialize
@RegisterForReflection
public class ExampleResourceSpec {
	
	@JsonProperty("messageParts")
	private Set<String> messageParts;
	
	@JsonProperty("namespace")
	private String namespace;
		
	@Override
	public String toString() {
		return "messageParts="+ Arrays.toString(messageParts.toArray(new String[] {}))+"\namespace="+namespace;
	}

	public Set<String> getMessageParts() {
		return messageParts;
	}

	public void setMessageParts(Set<String> messageParts) {
		this.messageParts = messageParts;
	}

	public String getNamespace() {
		return namespace;
	}

	public void setNamespace(String namespace) {
		this.namespace = namespace;
	}

}
