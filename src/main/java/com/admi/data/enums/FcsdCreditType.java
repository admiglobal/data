package com.admi.data.enums;

import com.admi.data.entities.FcsdProgramCreditsEntity;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

public enum FcsdCreditType {
	INVENTORY_MANAGEMENT_ALLOWANCE("INVENTORY MANAGEMENT ALLOWANCE", FcsdProgramCreditsEntity :: setIma),
	DEALER_TERMS_AND_CONDITIONS("DEALER TERMS AND CONDITIONS", FcsdProgramCreditsEntity :: setDtc),
	WINS_WHOLESALE("WINS WHOLESALE", FcsdProgramCreditsEntity :: setWw),
	WINS_GOVERNMENT_FLEET("WINS GOVERNMENT FLEET", FcsdProgramCreditsEntity :: setWgf),
	WINS_GOVERNMENT("WINS GOVERNMENT", FcsdProgramCreditsEntity :: setWg),
	WINS_DEALER_TO_DEALER("WINS DEALER TO DEALER", FcsdProgramCreditsEntity :: setWdd),
	WINS_FLEET("WINS FLEET", FcsdProgramCreditsEntity :: setWf),
	SIXTY_DAY("60 DAY", FcsdProgramCreditsEntity :: setSixtyDay),
	NORMAL_RETURN("NORMAL RETURN", FcsdProgramCreditsEntity :: setNr);

	private final String creditType;
	private final BiConsumer<FcsdProgramCreditsEntity, BigDecimal> setter;
	private static final Map<String, FcsdCreditType> map = new HashMap<>(values().length, 1);

	private FcsdCreditType(String creditType, BiConsumer<FcsdProgramCreditsEntity, BigDecimal> setter) {
		this.creditType = creditType;
		this.setter = setter;
	}

	static {
		for (FcsdCreditType f : values()) map.put(f.creditType, f);
	}

	public static FcsdCreditType of(String creditType) {
		FcsdCreditType result = map.get(creditType);
		if (result == null)
			throw new IllegalArgumentException("Invalid FcsdCreditType: " + creditType);
		return result;
	}

	public String getCreditType() {
		return creditType;
	}

	public BiConsumer<FcsdProgramCreditsEntity, BigDecimal> getSetter() {
		return setter;
	}
}
