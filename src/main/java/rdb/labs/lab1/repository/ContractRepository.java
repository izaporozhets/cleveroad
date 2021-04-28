package rdb.labs.lab1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import rdb.labs.lab1.model.Contract;

import java.sql.Date;
import java.util.List;

public interface ContractRepository extends JpaRepository<Contract, Integer> {

    //SELECT ctr.id, cli.name, cli.surname, serv.name, ctr.commission, ctr.total_sum, ctr.date
    //FROM contract ctr
    //INNER JOIN client cli ON ctr.client_id = cli.id
    //INNER JOIN service serv ON ctr.service_id = serv.id
    List<Contract> findAll();

    //SELECT ctr.id, cli.name, cli.surname, serv.name, ctr.commission, ctr.total_sum, ctr.date
    //FROM contract ctr
    //INNER JOIN client cli ON ctr.client_id = cli.id
    //INNER JOIN service serv ON ctr.service_id = serv.id
    //WHERE cli.name LIKE %?% OR cli.surname LIKE %?%
    @Query("select c from Contract c where c.client.name like %:name% or c.client.surname like %:name%")
    List<Contract> findAllByClientNameOrSurname(@Param("name")String name);

    //SELECT ctr.id, cli.name, cli.surname, serv.name, ctr.commission, ctr.total_sum, ctr.date
    //FROM contract ctr
    //INNER JOIN client cli ON ctr.client_id = cli.id
    //INNER JOIN service serv ON ctr.service_id = serv.id
    //WHERE cli.name = ?
    //AND cli.surname = ?
    @Query("select contract from Contract contract where contract.client.name = ?1 and contract.client.surname = ?2")
    List<Contract> findAllByClientFullName(String name,String surname);

    //SELECT ctr.id, cli.name, cli.surname, serv.name, ctr.commission, ctr.total_sum, ctr.date
    //FROM contract ctr
    //INNER JOIN client cli ON ctr.client_id = cli.id
    //INNER JOIN service serv ON ctr.service_id = serv.id
    //WHERE serv.name LIKE %?%
    @Query("select c from Contract c where c.service.name like %:name%")
    List<Contract> findAllByServiceName(@Param("name") String name);


    //SELECT ctr.id, cli.name, cli.surname, serv.name, ctr.commission, ctr.total_sum, ctr.date
    //FROM contract ctr
    //INNER JOIN client cli ON ctr.client_id = cli.id
    //INNER JOIN service serv ON ctr.service_id = serv.id
    //WHERE ctr.date = ?
    List<Contract> findAllByDate(Date date);

    //SELECT ctr.id, cli.name, cli.surname, serv.name, ctr.commission, ctr.total_sum, ctr.date
    //FROM contract ctr
    //INNER JOIN client cli ON ctr.client_id = cli.id
    //INNER JOIN service serv ON ctr.service_id = serv.id
    //WHERE cli.id = ?
    List<Contract> findAllByClientId(Integer id);

}
