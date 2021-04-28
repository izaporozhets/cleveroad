package rdb.labs.lab1.controller;

import net.sf.jasperreports.engine.JRException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import rdb.labs.lab1.model.Client;
import rdb.labs.lab1.model.Contract;
import rdb.labs.lab1.model.JasperContractModel;
import rdb.labs.lab1.model.Service;
import rdb.labs.lab1.service.ClientService;
import rdb.labs.lab1.service.ContractService;
import rdb.labs.lab1.service.ReportService;
import rdb.labs.lab1.service.ServiceServ;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.sql.Date;
import java.util.List;
import java.util.Optional;

@Controller
public class MainController {

    private final ServiceServ serviceServ;
    private final ReportService reportService;
    private final ClientService clientService;
    private final ContractService contractService;

    public MainController(ServiceServ serviceServ, ReportService reportService, ClientService clientService, ContractService contractService) {
        this.serviceServ = serviceServ;
        this.reportService = reportService;
        this.clientService = clientService;
        this.contractService = contractService;
    }

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("name", "Ihor");
        return "home";
    }

    @GetMapping("/clients")
    public String clients(Model model) {
        List<Client> clientList = clientService.findAll();
        model.addAttribute("clientList", clientList);
        return "clients";
    }

    @GetMapping("/services")
    public String services(Model model) {
        List<Service> serviceList = serviceServ.findAll();
        model.addAttribute("serviceList", serviceList);
        return "services";
    }

    @GetMapping("/contracts")
    public String contracts(Model model) {
        List<Contract> contractList = contractService.findAll();
        model.addAttribute("contractList", contractList);
        return "contracts";
    }

    @GetMapping("/contracts/search/client/{id}")
    public String searchByClientId(@PathVariable(name = "id")Integer id, Model model){
        model.addAttribute("contractList", contractService.findAllByClientId(id));
        return "contracts";
    }

    @GetMapping("/contracts/search")
    public String search(@RequestParam("search") String search, Model model) {
        String[] split = search.split("\\s+");
        List<Contract> contractList;
        if(search.matches("\\d{4}-\\d{2}-\\d{2}")){
            model.addAttribute("contractList", contractService.findAllByDate(Date.valueOf(search)));
            return "contracts";
        }
        else if(search.matches(".*\\d.*")){
            model.addAttribute("error", "Enter valid date");
            return "contracts";
        }

        if (split.length == 2) {
           contractList =  contractService.findByClientNameAndSurname(split[0],split[1]);
           contractList.addAll(contractService.findAllByServiceName(search));
           model.addAttribute("contractList",contractList);
        }
        else if(split.length > 2){
            contractList = contractService.findAllByServiceName(search);
            if(contractList.isEmpty()){
                model.addAttribute("error","Name cannot contain more than three words");
            }
            model.addAttribute("contractList", contractList);
        }
        else {
            contractList = contractService.findAllByClientNameOrSurname(search);
            contractList.addAll(contractService.findAllByServiceName(search));
            model.addAttribute("contractList",contractList);
        }


        return "contracts";
    }

    @GetMapping("/clients/search")
    public String searchClient(@RequestParam("search") String search, Model model) {
        String[] split = search.split("\\s+");
        if(split.length == 2){
            model.addAttribute("clientList", clientService.findClientByNameAndSurname(split[0],split[1]));
        }
        else if(split.length > 2){
            model.addAttribute("error", "Name cannot contain more than two words");
        }
        else {
            model.addAttribute("clientList", clientService.findClientByNameOrSurname(search));
        }
        return "clients";
    }

    @GetMapping("/services/search")
    public String searchService(@RequestParam("search") String search, Model model) {
        model.addAttribute("serviceList", serviceServ.findByName(search));
        return "services";
    }

    @GetMapping("/contracts/info/{id}")
    public String contractInfo(Model model, @PathVariable(name = "id")Integer id) {
        Optional<Contract> contract = contractService.findById(id);
        model.addAttribute("contract", contract.get());
        return "contract-info";
    }

    @GetMapping("/contracts/report/{id}")
    public String record(Model model, @PathVariable(name = "id")Integer id)
    {
        Optional<Contract> contract = contractService.findById(id);
        model.addAttribute("contract", contract.get());
        return "report";
    }

    @GetMapping("/contracts/create")
    public String create(Model model){
        model.addAttribute("clientList", clientService.findAll());
        model.addAttribute("serviceList", serviceServ.findAll());
        return "create-contract";
    }

    @PostMapping("/contracts/create")
    public String create(@RequestParam(name = "clientId")Integer clientId,
                         @RequestParam(name = "serviceId")Integer serviceId,
                         @RequestParam(name = "commission")Double commission){
        Client client = clientService.findById(clientId).get();
        Service service = serviceServ.findById(serviceId).get();
        Contract contract = new Contract();
        contract.setClient(client);
        contract.setService(service);
        Double percentageValue = service.getPrice()*(commission/100.0);
        contract.setCommission(commission);
        contract.setTotalSum(service.getPrice() + percentageValue);
        Date date = new Date(new java.util.Date().getTime());
        contract.setDate(date);
        contractService.saveContract(contract);
        return "redirect:/contracts";
    }

    @RequestMapping("/report/{format}/{id}")
    public void generateReport(@PathVariable("format") String format,
                                 @PathVariable("id")Integer id,HttpServletResponse response) throws FileNotFoundException, JRException {
        Optional<Contract> contract = contractService.findById(id);
        JasperContractModel contractModel = new JasperContractModel().fromContract(contract.get());
        File file = reportService.exportContract(format,contractModel);
        download(response, file);
    }

    @RequestMapping("/download")
    public String download(HttpServletResponse response, File file){
        response.setHeader("Content-Disposition","attachment; filename=" + file.getName());
        response.setHeader("Content-Transfer-Encoding","binary");
        try{
            BufferedOutputStream bos = new BufferedOutputStream(response.getOutputStream());
            FileInputStream fis = new FileInputStream(file.getAbsolutePath());
            int len;
            byte[] buf = new byte[1024];
            while ((len = fis.read(buf)) > 0){
                bos.write(buf,0,len);
            }
            bos.close();
            response.flushBuffer();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "contracts";
    }

    @GetMapping("/clients/create")
    public String clientCreate(){
        return "create-client";
    }

    @PostMapping("/clients/create")
    public String saveClient(@RequestParam("name")String firstName,
                             @RequestParam("surname")String secondName,
                             @RequestParam("employment")String employment,
                             @RequestParam("address")String address,
                             @RequestParam("contact")String contact){
        Client client = new Client(firstName,secondName,employment,address,contact);
        clientService.saveClient(client);
        return "redirect:/clients";
    }

    @GetMapping("/services/create")
    public String serviceCreate(){
        return "create-service";
    }

    @PostMapping("/services/create")
    public String saveService(@RequestParam("name") String name,
                              @RequestParam("description")String description,
                              @RequestParam("price")Double price){
        Service service = new Service(name, description, price);
        serviceServ.saveService(service);
        return "redirect:/services";
    }

}
