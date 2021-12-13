package com.admi.data.enums;

import java.util.HashMap;
import java.util.Map;

public enum PriceCode {
	CORE_PRICE_AMOUNT("CorePriceAmount"),
	PART_COST("PartCost"),
	UNIT_LIST_PRICE("UnitListPrice"),
	COST("Cost"),
	LIST("List"),
	STANDARD_MSRP("StandardMSRP"),
	UNIT_PRICE("UnitPrice"),
	DEALER_COST("DealerCost"),
	TRADE_IN("TradeIn"),
	ACTUAL_WHOLESALE_PRICE("ActualWholesalePrice");

	private static final Map<String, PriceCode> map = new HashMap<>(values().length, 1);

	static {
		for (PriceCode p : values()) map.put(p.priceCodeString, p);
	}

	private final String priceCodeString;

	private PriceCode(String priceCodeString) {
		this.priceCodeString = priceCodeString;
	}

	public static PriceCode of(String priceCodeString) {
		PriceCode result = map.get(priceCodeString);
		if (result == null)
			throw new IllegalArgumentException("Invalid PriceCode: " + priceCodeString);

		return result;
	}

	public String getPriceCodeString() {
		return priceCodeString;
	}

}
