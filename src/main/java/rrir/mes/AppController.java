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

    private void recalculate(int n) {
        double[] wValues=this.solver.generateAndSolve(n);
        double h=3.0/n;
        XYChart.Series<Number, Number> series = new XYChart.Series<>();
        series.getData().add(new XYChart.Data<>(0.0, 5.0));
        series.getData().add(new XYChart.Data<>(3.0, 4.0));
        for (int i = 0; i < wValues.length-1; i++) {
            double x=h*(i+1);
            double y=5-x/3+wValues[i];
            System.out.println(x);
            System.out.println(y);
            series.getData().add(new XYChart.Data<>(x, y));
        }
        System.out.println(series.getData().size());
        if (this.plot.getData().size() >= 1) {
            this.plot.getData().remove(0);
        }

        this.plot.getData().add(0,series);
    }

}
