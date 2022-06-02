/**
 * This class represents an uploaded Motorcraft order, with order info, order content, and upload issues.
 */

package com.admi.data.dto;

import com.admi.data.entities.DowOrderRow;
import com.admi.data.entities.McOrdersContentEntity;
import com.admi.data.entities.McOrdersEntity;
import com.admi.data.repositories.McOrdersContentRepository;
import com.admi.data.repositories.McOrdersRepository;
import org.springframework.beans.factory.annotation.Autowired;

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

	public String getTotalOrderCost(){
		Float total = 0F;
		for(McOrdersContentEntity part: orderContent){
			total += part.getPrice();
		}
		return "$" + String.format("%.2f", total); //currency format
	}

	public Long getTotalOrderQuantity(){
		Long count = 0L;
		for(McOrdersContentEntity part: orderContent){
			count += part.getQty();
		}
		return count;
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
