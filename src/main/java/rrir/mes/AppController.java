package rrir.mes;

import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.net.URL;
import java.util.ResourceBundle;

public class AppController{
    @FXML
    private LineChart<Number, Number> plot;

    @FXML
    private TextField fieldN;


    private Solver solver = new Solver();

    @FXML
    public void buttonAction(){
        try{
            Integer givenN= Integer.parseInt(fieldN.getText());
            recalculate(givenN);
        }
        catch(NumberFormatException exception){exception.printStackTrace();}
    }

//    @Override
//    public void initialize(URL location, ResourceBundle resources) {
//
//        XYChart.Series<Number, Number> exactSolution = new XYChart.Series<>();
//        exactSolution.getData().add(new XYChart.Data<>();
//        exactSolution.getData().add(new XYChart.Data<>());
//        exactSolution.getData().add(new XYChart.Data<>());
//
//        this.plot.getData().add(0, exactSolution);
//    }

    private void recalculate(int n) {
        //double[] wValues=this.solver.generateAndSolve(n);
        double h=3/n;
        XYChart.Series<Number, Number> series = new XYChart.Series<>();
        series.getData().add(new XYChart.Data<>(0, 5));
        series.getData().add(new XYChart.Data<>(3, 4));
//        for (int i = 0; i < wValues.length-1; i++) {
//            double x=h*(i+1);
//            double y=5-x/3+wValues[i];
//            series.getData().add(new XYChart.Data<>(x, y));
//        }
        this.plot.getData().add(series);
    }

}
