package rdb.labs.lab1.service;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import rdb.labs.lab1.model.JasperContractModel;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ReportService {

    public File exportContract(String reportFormat, JasperContractModel contract) throws FileNotFoundException, JRException {
        String path = "src\\main\\resources\\contracts";
        List<JasperContractModel> model = new ArrayList<>();
        model.add(contract);
        File file = ResourceUtils.getFile("classpath:contract.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(model);
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("createdBy","Notary");
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport,parameters,dataSource);

        File result = null;
        String fullpath;
        if(reportFormat.equalsIgnoreCase("html")){
            fullpath = path +"\\contract"+contract.getId()+".html";
            JasperExportManager.exportReportToHtmlFile(jasperPrint,fullpath);
            result = new File(fullpath);
        }
        if(reportFormat.equalsIgnoreCase("pdf")){
            fullpath = path +"\\contract"+contract.getId()+".pdf";
            JasperExportManager.exportReportToPdfFile(jasperPrint,fullpath);
            result = new File(fullpath);
        }
        return result;
    }
}
