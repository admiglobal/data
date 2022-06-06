package com.admi.data.dto;

import com.admi.data.entities.McOrdersContentEntity;
import com.admi.data.entities.McOrdersEntity;
import com.admi.data.repositories.McOrdersContentRepository;
import com.admi.data.repositories.McOrdersRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * This class represents a Motorcraft order, combining an McOrder with a list of its corresponding McOrdersContent
 */
public class MotorcraftOrder {

    @Autowired
    McOrdersRepository mcOrdersRepo;

    @Autowired
    McOrdersContentRepository mcOrdersContentRepo;

    private McOrdersEntity order;
    private List<McOrdersContentEntity> orderContent;

    /**
     * Retrieves from our DB based on ADMI order number
     * @param orderNumber The ADMI order number for this order
     */
    public MotorcraftOrder(String orderNumber){
        this.order = mcOrdersRepo.findByOrderNumber(orderNumber);
        this.orderContent = mcOrdersContentRepo.findAllByOrderNumber(orderNumber);
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
}
