package de.hska.ibsys;

import de.hska.ibsys.beans.CalculateBean;
import de.hska.ibsys.dto.InputDTO;
import de.hska.ibsys.dto.ResultDTO;
import de.hska.ibsys.result.Results;
import de.hska.ibsys.util.XMLUtil;
import java.io.*;
import org.custommonkey.xmlunit.Validator;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.xml.sax.SAXException;

/**
 *
 * @author p0004
 */
public class TestCalculateBean {
    
    private CalculateBean calculateBean;
    private InputDTO inputDTO;
    private ResultDTO resultDTO;
    
    @Before
    public void setUp() throws FileNotFoundException, IOException {
        inputDTO = new InputDTO();
        resultDTO = new ResultDTO();
        
        // Get Test-XML-file from Supply Chain Simulator
        String path = new java.io.File("").getAbsolutePath() 
                + File.separatorChar +  "src" + File.separatorChar + "test"  + File.separatorChar + "resources" 
                + File.separatorChar + "xml" + File.separatorChar + "result.xml";
        File file = new File(path);
        FileInputStream fileInputStream = new FileInputStream(file);
        byte fileContent[] = new byte[(int)file.length()];
        fileInputStream.read(fileContent);
        resultDTO.setResult((Results)XMLUtil.unmarshal(fileContent, Results.class));
    }
    
    @After
    public void tearDown() {
        inputDTO = null;
        resultDTO = null;
        calculateBean = null;
    }
    
    @Test
    public void testAll() throws InterruptedException, SAXException {
        // Set sales orders, forecasts and selldirects
        Integer[] selldirectQuantities = new Integer[] {100, 0, 0};
        Double[] selldirectPenalties = new Double[] {Double.valueOf(20.0), Double.valueOf(0.0), Double.valueOf(0.0)};
        Double[] selldirectPrices = new Double[] {Double.valueOf(190.0), Double.valueOf(0.0), Double.valueOf(0.0)};
        Integer[] salesOrders = new Integer[] {150, 150, 150};
        Integer[][] forcasts = new Integer[][] {{150,150,150},{100,100,50},{100,50,50}};
        
        resultDTO.setSelldirectQuantities(selldirectQuantities);
        resultDTO.setSelldirectPrices(selldirectPrices);
        resultDTO.setSelldirectPenalties(selldirectPenalties);
        resultDTO.setSalesOrders(salesOrders);
        resultDTO.setForcasts(forcasts);
        
        // Create a calculation bean for testing input methods
        calculateBean = new CalculateBean(resultDTO);
        
        // Get the calculated information to generate XML
        inputDTO = calculateBean.getInputDTO();
        StringWriter stringWriter = XMLUtil.marshal(inputDTO.getInput());
        
        // Validate the XML
        Validator validator = new Validator(stringWriter.getBuffer().toString());
        validator.useXMLSchema(true);
        validator.setJAXP12SchemaSource(new File("").getAbsolutePath() 
                + File.separatorChar + "src" + File.separatorChar + "main" + File.separatorChar + "resources" 
                + File.separatorChar + "xsd" + File.separatorChar +  "input.xsd");
        Assert.assertTrue(validator.toString(), validator.isValid());
        
        System.out.println(stringWriter);
    }
}