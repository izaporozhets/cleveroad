package rdb.labs.lab1.service.impl;

import org.springframework.stereotype.Service;
import rdb.labs.lab1.model.Contract;
import rdb.labs.lab1.repository.ContractRepository;
import rdb.labs.lab1.service.ContractService;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ContractServiceImpl implements ContractService {

    private final ContractRepository contractRepository;

    public ContractServiceImpl(ContractRepository contractRepository) {
        this.contractRepository = contractRepository;
    }

    @Override
    public List<Contract> findAll() {
        return contractRepository.findAll();
    }

    @Override
    public Optional<Contract> findById(Integer id) {
        return contractRepository.findById(id);
    }

    @Override
    public List<Contract> findAllByClientNameOrSurname(String name) {
        return contractRepository.findAllByClientNameOrSurname(name);
    }

    @Override
    public List<Contract> findByClientNameAndSurname(String name, String surname) {
        return contractRepository.findAllByClientFullName(name, surname);
    }

    @Override
    public List<Contract> findAllByServiceName(String name) {
        return contractRepository.findAllByServiceName(name);
    }

    @Override
    public List<Contract> findAllByDate(Date date) {
        return contractRepository.findAllByDate(date);
    }

    @Override
    public List<Contract> findAllByClientId(Integer id) {
        return contractRepository.findAllByClientId(id);
    }

    @Override
    public void saveContract(Contract contract) {
        contractRepository.save(contract);
    }
}
