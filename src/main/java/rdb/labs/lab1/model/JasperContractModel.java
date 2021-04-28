package rdb.labs.lab1.model;

import lombok.Data;

import java.sql.Date;

@Data
public class JasperContractModel {
    private Integer id;
    private String client;
    private String service;
    private Date date;
    private String description;
    private String commission;
    private String toPay;

    public JasperContractModel fromContract(Contract contract){
        JasperContractModel contractModel = new JasperContractModel();
        contractModel.setId(contract.getId());
        contractModel.setClient(contract.getClient().getName() + " " + contract.getClient().getSurname());
        contractModel.setService(contract.getService().getName());
        contractModel.setDate(contract.getDate());
        contractModel.setDescription(contract.getService().getDescription());
        contractModel.setCommission(String.valueOf(contract.getCommission()) + '%');
        contractModel.setToPay(String.valueOf(contract.getTotalSum()) + '$');
        return contractModel;
    }
}
