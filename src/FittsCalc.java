import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.util.Arrays;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import java.awt.Toolkit;

public class FittsCalc { 
	
	public static void main(String[] args){
		
		
		System.out.println("Data: ");
		String datastring=JOptionPane.showInputDialog("Enter the data string!");
		System.out.println(datastring);
		//String datastring="915635000,711,Orange;882272000,477,Gray;1663214000,2094,Blue;1452949000,1450,Green;1544820000,1176,Pink;2012466000,1111,Magenta;847492000,714,Gray;1186337000,1330,Orange;712692000,273,Yellow;1313649000,1565,Gray";
		String[] darray=datastring.split(";");
		double[] MT=new double[darray.length];
		double[] amplitude=new double[darray.length];
		String[] colors=new String[darray.length];
		
		for (int p=0;p<darray.length;p++){
			String[] bacon=darray[p].split(",");
			MT[p]=Double.parseDouble(bacon[0]);
			amplitude[p]=Double.parseDouble(bacon[1]);
			colors[p]=bacon[2];
		}
		
		//Sample data: 915635000,711,Orange;882272000,477,Gray;1663214000,2094,Blue;1452949000,1450,Green;1544820000,1176,Pink;2012466000,1111,Magenta;847492000,714,Gray;1186337000,1330,Orange;712692000,273,Yellow;1313649000,1565,Gray
		//Format: MT in nanoseconds, amplitude in px, color name
		//double[] MT={915635000,882272000,1663214000,1452949000,1544820000,2012466000,847492000,1186337000,712692000,1313649000};
		//double[] amplitude={711,477,2094,1450,1176,1111,714,1330,273,1565};
		
		
		double width=250;
		double[] ID=new double[amplitude.length];
		
		for (int p=0;p<ID.length;p++){
			ID[p]=Math.log(2*(amplitude[p]/width))/Math.log(2);
		}
		
		LinReg lin=new LinReg(ID,MT);

		String outstring="";
		
		outstring=outstring+"\n\nData Report\n\n";
		
		outstring=outstring+"Movement times (in nanoseconds): [";
		for (int l=0;l<MT.length;l++){
			outstring=outstring+(long)MT[l];
			if (l!=MT.length-1) outstring=outstring+", ";
			else outstring=outstring+"]\n";
		}
		outstring=outstring+"Amplitudes (in pixels): [";
		for (int l=0;l<amplitude.length;l++){
			outstring=outstring+(long)amplitude[l];
			if (l!=amplitude.length-1) outstring=outstring+", ";
			else outstring=outstring+"]\n";
		}
		outstring=outstring+"Colors: "+Arrays.toString(colors)+"\n\n";
		outstring=outstring+"Fitts' Equation:";
		outstring=outstring+" y = "+(long)lin.getB()+" + "+(long)lin.getA()+" * log2(2A/W)\n";//Have to switch B of LinReg with A of Equation and vice-versa. Don't ask why.
		outstring=outstring+"r = ";
		outstring=outstring+""+Math.sqrt(lin.getR2())+"\n\n";
		
		outstring=outstring+"\nStandard Deviations\n\n";
		double[] ratiosmain=new double[darray.length];
		for (int p=0;p<ratiosmain.length;p++){
			ratiosmain[p]=MT[p]/amplitude[p];
		}
		double mainstddev=StdStats.stddev(ratiosmain);
		outstring=outstring+"Entire Set: "+(long)mainstddev+"\n";
		/*
		 * 
		"Blue"
		"Gray"
		"Green"
		"Magenta"
		"Orange"
		"Pink"
		"Red"
		"Yellow"
		 * 
		 */
		String colorname;
		colorname="Blue";
		outstring=outstring+colorname+": "+getColorStdDev(colorname, darray)+"\n";
		colorname="Gray";
		outstring=outstring+colorname+": "+getColorStdDev(colorname, darray)+"\n";
		colorname="Green";
		outstring=outstring+colorname+": "+getColorStdDev(colorname, darray)+"\n";
		colorname="Magenta";
		outstring=outstring+colorname+": "+getColorStdDev(colorname, darray)+"\n";
		colorname="Orange";
		outstring=outstring+colorname+": "+getColorStdDev(colorname, darray)+"\n";
		colorname="Pink";
		outstring=outstring+colorname+": "+getColorStdDev(colorname, darray)+"\n";
		colorname="Red";
		outstring=outstring+colorname+": "+getColorStdDev(colorname, darray)+"\n";
		colorname="Yellow";
		outstring=outstring+colorname+": "+getColorStdDev(colorname, darray)+"\n";
		
		JOptionPane.showMessageDialog(null, outstring+"\n\n\n(Also copied to clipboard)");
		System.out.println(outstring);
		
		StringSelection stringSelection = new StringSelection (outstring);
		Clipboard clpbrd = Toolkit.getDefaultToolkit().getSystemClipboard ();
		clpbrd.setContents (stringSelection, null);
	}
	
	public static double getColorStdDev(String color, String[] dataarray){
		ArrayList<Double> temp=new ArrayList<Double>();
		
		for (int k=0;k<dataarray.length;k++){
			
			if (dataarray[k].contains(color)){
				String[] temp2=dataarray[k].split(",");
				double a=Double.parseDouble(temp2[0]);
				double b=Double.parseDouble(temp2[1]);
				double c=a/b;
				temp.add(c);
			}
			
		}
		if (temp.size()!=0){
			double[] temp3=new double[temp.size()];
			for (int l=0;l<temp.size();l++){
				temp3[l]=temp.get(l);
			}

			double stddev1=StdStats.stddev(temp3);

			return stddev1;
		}
		else return Double.NaN;
	}
	
}

