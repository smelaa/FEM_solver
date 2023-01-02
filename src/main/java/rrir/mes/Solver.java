package rrir.mes;

import org.apache.commons.math3.analysis.integration.IterativeLegendreGaussIntegrator;
import org.apache.commons.math3.linear.*;

import static java.lang.Math.*;

public class Solver {
    private static final double G = 6.673e-11;
    //private static final double G = 6.673e-2;
    private final IterativeLegendreGaussIntegrator integrator=new IterativeLegendreGaussIntegrator(2, 1e-5, 1e-5);

    public double[] generateAndSolve(int n){
        if (n < 2) {
            throw new IllegalArgumentException("n must be > 2");
        }
        Double h= Double.valueOf(3.0 / n);
        //tworzenie macierzy B(ei,ej)
        Array2DRowRealMatrix matrixB = new Array2DRowRealMatrix(n, n);
        double diagonal_val=this.integrator.integrate(
                Integer.MAX_VALUE,
                x->1/pow(h,2),
                0, 2*h);
        for (int r=1;r<n+1;r++){
            for (int c=1;c<n+1;c++){
                double b = 0;
                if (r==c){b=diagonal_val;}
                if (r==c+1){
                    b=this.integrator.integrate(
                            Integer.MAX_VALUE,
                            x->-1/pow(h,2),
                            c*h, r*h);
                }
                if (r+1==c){
                    b=this.integrator.integrate(
                            Integer.MAX_VALUE,
                            x->-1/pow(h,2),
                            r*h, c*h);
                }
                matrixB.addToEntry(r-1, c-1, b);
            }
        }

        //tworzenie wektora L(ej)
        ArrayRealVector vectorL= new ArrayRealVector(n);
        double constant=4*PI*G;
        for (int i=1;i<n+1;i++){
            double l=0;
            l+=1/3*integrator.integrate(
                    Integer.MAX_VALUE,
                    x -> 1/h,
                    h*(i-1), h*i);
            l+=1/3*integrator.integrate(
                    Integer.MAX_VALUE,
                    x -> -1/h,
                    h*i, h*(i+1));

            int finalI = i;
            if(max(h*(i-1),1)< min(h*i,2)) {
                l += constant * integrator.integrate(
                        Integer.MAX_VALUE,
                        x -> x / h - finalI + 1,
                        max(h * (i - 1), 1), min(h * i, 2));
            }
            if(max(h*i,1)< min(h*(i+1),2)) {
                l += constant * integrator.integrate(
                        Integer.MAX_VALUE,
                        x -> -x / h + finalI + 1,
                        max(h * i, 1), min(h * (i + 1), 2));
                vectorL.addToEntry(i - 1, l);
            }
        }

        RealVector vectorW = new LUDecomposition(matrixB).getSolver().solve(vectorL);
        return vectorW.toArray();
    }

}
