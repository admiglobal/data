/**
 * This interface is for transferring inventory data for OPC by brand.
 * @author Julia Betzig +JMJ+
 * @version 5/2/22
 */

package com.admi.data.dto;

public interface OpcKpiDto {
    public String getBrand();
    public Double getValue();
    public Integer getSku();

}
