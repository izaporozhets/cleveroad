package rdb.labs.lab1.service;
import rdb.labs.lab1.model.Contract;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

public interface ContractService {
    List<Contract> findAll();
    Optional<Contract> findById(Integer id);
    List<Contract> findAllByClientNameOrSurname(String name);
    List<Contract> findByClientNameAndSurname(String name, String surname);
    List<Contract> findAllByServiceName(String name);
    List<Contract> findAllByDate(Date date);
    List<Contract> findAllByClientId(Integer id);
    void saveContract(Contract contract);
}
