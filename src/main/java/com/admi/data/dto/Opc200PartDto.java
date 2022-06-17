/**
 * This class nicely contains the data for an OPC 200 part, since it can get confusing with multiple part numbers, etc.
 * Equality is based exclusively on rank.
 */

package com.admi.data.dto;

import com.admi.data.entities.FordPtEntity;
import com.admi.data.entities.OpcTsp200DataEntity;
import com.admi.data.entities.OpcTsp200Entity;
import com.admi.data.repositories.FordPtRepository;

import java.util.Objects;

public class Opc200PartDto {
    Integer rank;
    String ocPartNumber;
    String servicePartNumber;
    Integer qoh;
    boolean matchedByServicePartNumber; //when we find an OPC part in FordDealerInventory, this indicates if the part was found under the OC_PART_NUMBER or the SERVICE_PART_NUMBER

    public Opc200PartDto(OpcTsp200Entity opcTsp200Entity, Integer qoh, boolean matchedByServicePartNumber){
        this.rank = opcTsp200Entity.getRank();
        this.ocPartNumber = opcTsp200Entity.getOcPartNumber();
        this.servicePartNumber = opcTsp200Entity.getServicePartNumber();
        this.qoh = qoh;
        this.matchedByServicePartNumber = matchedByServicePartNumber;
    }

    private Double findPartCost(FordPtRepository fordPtRepo){
        String partNumber = (matchedByServicePartNumber ? servicePartNumber : ocPartNumber);
        if(matchedByServicePartNumber){
            //service part numbers are all found in the Ford PT
            return fordPtRepo.findFirstByPartnoAndTape(partNumber, "FORD_US").getPcValue();
        } else { //it is the OC part number
            //OC part numbers are sometimes under partno, sometimes under motorcraft in the PT
            FordPtEntity fordPtEntity = fordPtRepo.findFirstByPartnoAndTape(partNumber, "FORD_US");
            if(fordPtEntity == null) fordPtEntity = fordPtRepo.findFirstByMotorcraftAndTape(partNumber, "FORD_US");
            return fordPtEntity.getPcValue();
        }
    }

    private Integer findPartCostCents(FordPtRepository fordPtRepo){
        return Math.toIntExact(Math.round(findPartCost(fordPtRepo) * 100));
    }

    public OpcTsp200DataEntity toOpcTsp200DataEntity(String paCode, FordPtRepository fordPtRepo){
        OpcTsp200DataEntity dataEntity = new OpcTsp200DataEntity();
        dataEntity.setPaCode(paCode);
        dataEntity.setPartNumber( matchedByServicePartNumber ? this.servicePartNumber : this.ocPartNumber );
        dataEntity.setQoh(Math.toIntExact(qoh));
        dataEntity.setPartCostCents(findPartCostCents(fordPtRepo));
        return dataEntity;
    }

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    public String getOcPartNumber() {
        return ocPartNumber;
    }

    public void setOcPartNumber(String ocPartNumber) {
        this.ocPartNumber = ocPartNumber;
    }

    public String getServicePartNumber() {
        return servicePartNumber;
    }

    public void setServicePartNumber(String servicePartNumber) {
        this.servicePartNumber = servicePartNumber;
    }

    public Integer getQoh() {
        return qoh;
    }

    public void setQoh(Integer qoh) {
        this.qoh = qoh;
    }

    public void setMatchedByServicePartNumber(boolean matchedByServicePartNumber) {
        this.matchedByServicePartNumber = matchedByServicePartNumber;
    }

    public boolean isMatchedByServicePartNumber() {
        return matchedByServicePartNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Opc200PartDto that = (Opc200PartDto) o;
        return Objects.equals(rank, that.rank);
    }

    @Override
    public int hashCode() {
        return Objects.hash(rank);
    }
}
