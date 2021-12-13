package com.admi.data.enums;

public enum MixSource {
	MOCKSERVICE("mockservice"),
	AUTOMATE("automate"),
	AUTOSOFT("autosoft"),
	DOMINION("dominion"),
	PBS("pbs"),
	QUORUM("quorum");

	private final String sourceName;

	private MixSource(String sourceName) {
		this.sourceName = sourceName;
	}

	public String getSourceName() {
		return sourceName;
	}
}
