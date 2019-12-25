package cursospringboot.report;

import java.io.File;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletContext;

import org.springframework.stereotype.Component;

import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Component
public class ReportUtil<T> implements Serializable {

	private static final long serialVersionUID = 1L;

	public byte[] generateReport(List<T> listData, String report, ServletContext servletContext) throws Exception {
        
        JRBeanCollectionDataSource jrbcds = new JRBeanCollectionDataSource(listData);
        String pathJasper = servletContext.getRealPath("relatorios") + File.separator + report + ".jasper";
        JasperPrint printerJasper = JasperFillManager.fillReport(pathJasper, new HashMap<>(), jrbcds);
         
        return JasperExportManager.exportReportToPdf(printerJasper);
        

	}
}