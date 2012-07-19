package de.hska.ibsys.beans;

import de.hska.ibsys.dto.InputDTO;
import de.hska.ibsys.input.Order;
import de.hska.ibsys.input.Production;
import de.hska.ibsys.input.Workingtime;
import de.hska.ibsys.util.Constant;
import java.util.ResourceBundle;
import javax.faces.context.FacesContext;
import org.primefaces.model.chart.CartesianChartModel;
import org.primefaces.model.chart.ChartSeries;

/**
 *
 * @author p01
 */
public class ChartBean {
    
    private InputDTO inputDTO;
    
    ResourceBundle bundle = ResourceBundle.getBundle(Constant.LOCALE_RESOURCES, FacesContext.getCurrentInstance().getViewRoot().getLocale());

    public ChartBean() {
    }
    
    public ChartBean(InputDTO inputDTO) {
        this.inputDTO = inputDTO;
    }
    
    public CartesianChartModel getShiftModel() {
        return createShiftModel(inputDTO);
    }
    
    public CartesianChartModel getOvertimeModel() {
        return createOvertimeModel(inputDTO);
    }
    
    public CartesianChartModel getOrderModel() {
        return createOrderModel(inputDTO);
    }
    
    public CartesianChartModel getProductionModel() {
        return createProductionModel(inputDTO);
    }

    private CartesianChartModel createShiftModel(InputDTO inputDTO) {
        CartesianChartModel workingtimeShiftModel = new CartesianChartModel();
        ChartSeries shiftSeries = new ChartSeries();
        String localeShift = bundle.getString("chart.shifts");
        
        if (inputDTO != null && inputDTO.getInput().getWorkingtimelist().getWorkingtime() != null 
                && inputDTO.getInput().getWorkingtimelist().getWorkingtime().size() > 0) {
            shiftSeries.setLabel(localeShift);
            for(Workingtime workingtime : inputDTO.getInput().getWorkingtimelist().getWorkingtime()) {
                shiftSeries.set(String.valueOf(workingtime.getStation()), workingtime.getShift());
            }
        } else {
            for(int i = 1; i < 16; i++) {
                if (i != 5) {
                    shiftSeries.set(String.valueOf(i),0);
                }
            }
        }
        
        workingtimeShiftModel.addSeries(shiftSeries);
        
        return workingtimeShiftModel;
    }
    
    private CartesianChartModel createOvertimeModel(InputDTO inputDTO) {
        CartesianChartModel workingtimeOvertimeModel = new CartesianChartModel();
        ChartSeries overtimeSeries = new ChartSeries();
        String localeOvertime = bundle.getString("chart.overtime");
        
        if (inputDTO != null && inputDTO.getInput().getWorkingtimelist().getWorkingtime() != null 
                && inputDTO.getInput().getWorkingtimelist().getWorkingtime().size() > 0) {
            overtimeSeries.setLabel(localeOvertime);
            for(Workingtime workingtime : inputDTO.getInput().getWorkingtimelist().getWorkingtime()) {
                overtimeSeries.set(String.valueOf(workingtime.getStation()), workingtime.getOvertime());
            }
        } else {
            for(int i = 1; i < 16; i++) {
                if (i != 5) {
                    overtimeSeries.set(String.valueOf(i),0);
                }
            }
        }
        
        workingtimeOvertimeModel.addSeries(overtimeSeries);
        
        return workingtimeOvertimeModel;
    }
    
    private CartesianChartModel createOrderModel(InputDTO inputDTO) {
        CartesianChartModel orderModel = new CartesianChartModel();
        ChartSeries orderSeries = new ChartSeries();
        String localeOrder = bundle.getString("chart.ordered");
        
        if (inputDTO != null && inputDTO.getInput().getOrderlist().getOrder() != null 
                && inputDTO.getInput().getOrderlist().getOrder().size() > 0) {
            orderSeries.setLabel(localeOrder);
            for(Order order : inputDTO.getInput().getOrderlist().getOrder()) {
                orderSeries.set(String.valueOf(order.getArticle()), order.getQuantity());
            }
        } else {
            for(int i = 1; i < 30; i++) {
                if (i != 5) {
                    orderSeries.set(String.valueOf(i),0);
                }
            }
        }
        
        orderModel.addSeries(orderSeries);
        
        return orderModel;
    }
    
    private CartesianChartModel createProductionModel(InputDTO inputDTO) {
        CartesianChartModel productionModel = new CartesianChartModel();
        ChartSeries productionSeries = new ChartSeries();
        String localeProduction = bundle.getString("chart.produced");
        
        if (inputDTO != null && inputDTO.getInput().getProductionlist().getProduction() != null 
                && inputDTO.getInput().getProductionlist().getProduction().size() > 0) {
            productionSeries.setLabel(localeProduction);
            for(Production production : inputDTO.getInput().getProductionlist().getProduction()) {
                String article = String.valueOf(production.getArticle());
                int quantity = 0;
                for (Production tempProduction : inputDTO.getInput().getProductionlist().getProduction()) {
                    if (article.equals(tempProduction.getArticle().toString())) {
                        quantity += tempProduction.getQuantity().intValue();
                    }
                }
                productionSeries.set(article, quantity);
            }
        } else {
            for(int i = 1; i < 37; i++) {
                if (i != 5) {
                    productionSeries.set(String.valueOf(i),0);
                }
            }
        }
        
        productionModel.addSeries(productionSeries);
        
        return productionModel;
    }
}
