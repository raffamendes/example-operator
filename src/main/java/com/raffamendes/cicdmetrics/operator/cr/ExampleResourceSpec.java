package com.raffamendes.cicdmetrics.operator.cr;

import java.util.Arrays;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import io.quarkus.runtime.annotations.RegisterForReflection;

@JsonDeserialize
@RegisterForReflection
public class ExampleResourceSpec {
	
	@JsonProperty("specialRoles")
	private Set<String> specialRoles;

	public Set<String> getSpecialRoles() {
		return specialRoles;
	}

	public void setSpecialRoles(Set<String> specialRoles) {
		this.specialRoles = specialRoles;
	}
	
	@Override
	public String toString() {
		return "specialRoles="+ Arrays.toString(specialRoles.toArray(new String[] {}));
	}

}
