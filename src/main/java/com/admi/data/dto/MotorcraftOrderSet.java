package com.admi.data.dto;

import com.admi.data.entities.DowOrderRow;
import com.admi.data.entities.McOrdersContentEntity;
import com.admi.data.entities.McOrdersEntity;

import java.util.ArrayList;
import java.util.List;

public class MotorcraftOrderSet {

	private McOrdersEntity order;
	private List<McOrdersContentEntity> orderContent;
	private List<ImportIssue> issues;

	public MotorcraftOrderSet(McOrdersEntity order, List<McOrdersContentEntity> orderContent, List<ImportIssue> issues) {
		this.order = order;
		this.orderContent = orderContent;
		this.issues = issues;
	}

	public List<DowOrderRow> getOrderContentAsDowOrder() {
		List<DowOrderRow> newOrders = new ArrayList<>();

		for (McOrdersContentEntity part : orderContent) {
			newOrders.add(
					new DowOrderRow(
							order.getPaCode(),
							part.getOcPartno(),
							part.getQty(),
							order.getPoNumber()
					)
			);
		}
		return newOrders;
	}


	public McOrdersEntity getOrder() {
		return order;
	}

	public void setOrder(McOrdersEntity order) {
		this.order = order;
	}

	public List<McOrdersContentEntity> getOrderContent() {
		return orderContent;
	}

	public void setOrderContent(List<McOrdersContentEntity> orderContent) {
		this.orderContent = orderContent;
	}

	public List<ImportIssue> getIssues() {
		return issues;
	}

	public void setIssues(List<ImportIssue> issues) {
		this.issues = issues;
	}

}
