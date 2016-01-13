public class LinReg{//Main calculation methods "borrowed" from http://introcs.cs.princeton.edu/java/97data/LinearRegression.java.html
	
	private double[] xenter;
	private double[] yenter;
	private double A;
	private double B;
	
	public LinReg(double[] x, double[] y){
		if (x.length!=y.length) System.err.println("Length of x must equal length of y! NOT INSTANTIATED.");
		else {
			xenter=new double[x.length];
			yenter=new double[y.length];
			for (int k=0; k<x.length;k++){
				xenter[k]=x[k];
				yenter[k]=y[k];
			}
			A=CalcA(xenter,yenter);
			B=CalcB(xenter,yenter);
		}
	}
	public double getA(){
		A=CalcA(xenter,yenter);
		return A;
	}
	public double getB(){
		B=CalcB(xenter,yenter);
		return B;
	}
	public String getEquation(){
		A=CalcA(xenter,yenter);
		B=CalcB(xenter,yenter);
		return "y  = " + A + " * x + " + B;
	}
	public double getR2(){
		return CalcR2(xenter,yenter);
	}
	
    private double CalcA(double[] xstart, double[] ystart) { //EDIT: Changed from main to class
        int MAXN = 1000;
        int n = 0;
        double[] x = new double[MAXN];
        double[] y = new double[MAXN];

        // first pass: read in data, compute xbar and ybar
        
        double sumx = 0.0, sumy = 0.0, sumx2 = 0.0;
        while(n<xstart.length) {//EDIT: Changed from StdIn to values
            x[n] = xstart[n];//EDIT: Changed from StdIn to values
            y[n] = ystart[n];//EDIT: Changed from StdIn to values
            sumx  += x[n];
            sumx2 += x[n] * x[n];
            sumy  += y[n];
            n++;
        }
        double xbar = sumx / n;
        double ybar = sumy / n;

        // second pass: compute summary statistics
        double xxbar = 0.0, yybar = 0.0, xybar = 0.0;
        for (int i = 0; i < n; i++) {
            xxbar += (x[i] - xbar) * (x[i] - xbar);
            yybar += (y[i] - ybar) * (y[i] - ybar);
            xybar += (x[i] - xbar) * (y[i] - ybar);
        }
        double beta1 = xybar / xxbar;
        double beta0 = ybar - beta1 * xbar;

        // print results
//        System.out.println("y   = " + beta1 + " * x + " + beta0);

        // analyze results
        int df = n - 2;
        double rss = 0.0;      // residual sum of squares
        double ssr = 0.0;      // regression sum of squares
        for (int i = 0; i < n; i++) {
            double fit = beta1*x[i] + beta0;
            rss += (fit - y[i]) * (fit - y[i]);
            ssr += (fit - ybar) * (fit - ybar);
        }
        double R2    = ssr / yybar;
        double svar  = rss / df;
        double svar1 = svar / xxbar;
        double svar0 = svar/n + xbar*xbar*svar1;
//        System.out.println("R^2                 = " + R2);
//        System.out.println("std error of beta_1 = " + Math.sqrt(svar1));
//        System.out.println("std error of beta_0 = " + Math.sqrt(svar0));
//        svar0 = svar * sumx2 / (n * xxbar);
//        System.out.println("std error of beta_0 = " + Math.sqrt(svar0));
//
//        System.out.println("SSTO = " + yybar);
//        System.out.println("SSE  = " + rss);
//        System.out.println("SSR  = " + ssr);
        
        return beta1;
    }
    private double CalcB(double[] xstart, double[] ystart) { //EDIT: Changed from main to class
        int MAXN = 1000;
        int n = 0;
        double[] x = new double[MAXN];
        double[] y = new double[MAXN];

        // first pass: read in data, compute xbar and ybar
        
        double sumx = 0.0, sumy = 0.0, sumx2 = 0.0;
        while(n<xstart.length) {//EDIT: Changed from StdIn to values
            x[n] = xstart[n];//EDIT: Changed from StdIn to values
            y[n] = ystart[n];//EDIT: Changed from StdIn to values
            sumx  += x[n];
            sumx2 += x[n] * x[n];
            sumy  += y[n];
            n++;
        }
        double xbar = sumx / n;
        double ybar = sumy / n;

        // second pass: compute summary statistics
        double xxbar = 0.0, yybar = 0.0, xybar = 0.0;
        for (int i = 0; i < n; i++) {
            xxbar += (x[i] - xbar) * (x[i] - xbar);
            yybar += (y[i] - ybar) * (y[i] - ybar);
            xybar += (x[i] - xbar) * (y[i] - ybar);
        }
        double beta1 = xybar / xxbar;
        double beta0 = ybar - beta1 * xbar;

        // print results
//        System.out.println("y   = " + beta1 + " * x + " + beta0);

        // analyze results
        int df = n - 2;
        double rss = 0.0;      // residual sum of squares
        double ssr = 0.0;      // regression sum of squares
        for (int i = 0; i < n; i++) {
            double fit = beta1*x[i] + beta0;
            rss += (fit - y[i]) * (fit - y[i]);
            ssr += (fit - ybar) * (fit - ybar);
        }
        double R2    = ssr / yybar;
        double svar  = rss / df;
        double svar1 = svar / xxbar;
        double svar0 = svar/n + xbar*xbar*svar1;
//        System.out.println("R^2                 = " + R2);
//        System.out.println("std error of beta_1 = " + Math.sqrt(svar1));
//        System.out.println("std error of beta_0 = " + Math.sqrt(svar0));
//        svar0 = svar * sumx2 / (n * xxbar);
//        System.out.println("std error of beta_0 = " + Math.sqrt(svar0));
//
//        System.out.println("SSTO = " + yybar);
//        System.out.println("SSE  = " + rss);
//        System.out.println("SSR  = " + ssr);
        
        return beta0;
    }
    private double CalcR2(double[] xstart, double[] ystart) { //EDIT: Changed from main to class
        int MAXN = 1000;
        int n = 0;
        double[] x = new double[MAXN];
        double[] y = new double[MAXN];

        // first pass: read in data, compute xbar and ybar
        
        double sumx = 0.0, sumy = 0.0, sumx2 = 0.0;
        while(n<xstart.length) {//EDIT: Changed from StdIn to values
            x[n] = xstart[n];//EDIT: Changed from StdIn to values
            y[n] = ystart[n];//EDIT: Changed from StdIn to values
            sumx  += x[n];
            sumx2 += x[n] * x[n];
            sumy  += y[n];
            n++;
        }
        double xbar = sumx / n;
        double ybar = sumy / n;

        // second pass: compute summary statistics
        double xxbar = 0.0, yybar = 0.0, xybar = 0.0;
        for (int i = 0; i < n; i++) {
            xxbar += (x[i] - xbar) * (x[i] - xbar);
            yybar += (y[i] - ybar) * (y[i] - ybar);
            xybar += (x[i] - xbar) * (y[i] - ybar);
        }
        double beta1 = xybar / xxbar;
        double beta0 = ybar - beta1 * xbar;

        // print results
//        System.out.println("y   = " + beta1 + " * x + " + beta0);

        // analyze results
        int df = n - 2;
        double rss = 0.0;      // residual sum of squares
        double ssr = 0.0;      // regression sum of squares
        for (int i = 0; i < n; i++) {
            double fit = beta1*x[i] + beta0;
            rss += (fit - y[i]) * (fit - y[i]);
            ssr += (fit - ybar) * (fit - ybar);
        }
        double R2    = ssr / yybar;
        double svar  = rss / df;
        double svar1 = svar / xxbar;
        double svar0 = svar/n + xbar*xbar*svar1;
//        System.out.println("R^2                 = " + R2);
//        System.out.println("std error of beta_1 = " + Math.sqrt(svar1));
//        System.out.println("std error of beta_0 = " + Math.sqrt(svar0));
//        svar0 = svar * sumx2 / (n * xxbar);
//        System.out.println("std error of beta_0 = " + Math.sqrt(svar0));
//
//        System.out.println("SSTO = " + yybar);
//        System.out.println("SSE  = " + rss);
//        System.out.println("SSR  = " + ssr);
        
        return R2;
    }
}