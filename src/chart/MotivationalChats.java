package chart;
/* Clase que permite visualizar los esquemas motivacionales perceptuales APS, RPS, CSP, MPS y SPS. 
   Gonzalo Tejera
   Version: 1
   Fecha: 9 de agosto de 2010
 */


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Paint;
import java.awt.Panel;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.jCharts.chartData.*;
import org.jCharts.chartData.interfaces.IAxisDataSeries;
import org.jCharts.properties.*;
import org.jCharts.axisChart.*;
import org.jCharts.types.ChartType;
import org.jCharts.test.TestDataGenerator;

import support.Configuration;

public class MotivationalChats extends JFrame {
	private final int CANT_CHATS = 5;
    public static final int DEFAULT_SIZE = Configuration.getInt("PerceptualSchema.DEFAULT_SIZE");

	public static final int width= 500;
	public static final int height= 150;
	private JPanel chats[]=new JPanel[CANT_CHATS];
	AxisChart axisChart;
	ChartProperties chartProperties= new ChartProperties();
	AxisProperties axisProperties= new AxisProperties();
	LegendProperties legendProperties= null; //new LegendProperties();
	double[][] data=new double[1][DEFAULT_SIZE];
	
	DataSeries dataSeries[];
	String[][] legendLabels= {{"APS"},{"RPS"},{"CPS"},{"MPS*4"},{"SPS"}};
	Paint[][] paintsBlue= {{Color.BLUE}};
	Paint[][] paintsRed= {{Color.RED}};
	AxisChartDataSet axisChartDataSet=null;
	AreaChartProperties areaChartProperties= new AreaChartProperties();
	
	private final double RPS_HEIGHT = Configuration.getDouble("MotivationalSchema.RPS_HEIGHT");// Double.MIN_NORMAL;


	public MotivationalChats() {
		super("Grafica esquemas perceptuales");
		// inicializo graficas
		axisProperties.getYAxisProperties().setShowAxisLabels(false);
		axisProperties.getXAxisProperties().setShowAxisLabels(false);
		String xAxisTitle= "Perceptual Schema Position";
		String [] yAxisTitle= {"APS","RPS","CPS","EMR","~Suma~"};
		String title= "Motivational Perceptual Schema";
		String[] xAxisLabels=new String[DEFAULT_SIZE];
		this.getContentPane().setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		for (int pos=0;pos<xAxisLabels.length;pos++)
			xAxisLabels[pos]=""+pos;
		
		dataSeries=new DataSeries[5];

		c.fill = GridBagConstraints.BOTH;
		c.gridx = 0;
		c.weightx=width;
		c.weighty=height;
		// inicializo Panel
		this.setSize( width, height*5 );
		for (int chat=0;chat<CANT_CHATS; chat++) {
			c.gridy = chat;
			dataSeries[chat] = new DataSeries( xAxisLabels, null, null, yAxisTitle[chat]);
			this.chats[chat]=new JPanel( true );
			this.chats[chat].setSize( width, height );
			this.getContentPane().add(this.chats[chat],c);
			//this.getContentPane().add(this.chats[chat]);
		}
		this.setVisible( true );
		

	}
	
	public void setPerceptualSchema(double [][] APS,double [][] RPS, double [][] CPS,double [][]MPS, double suma[]) {
		Paint[][] paints; 
		for (int chat=0;chat<CANT_CHATS; chat++) {
			paints=paintsBlue;

			for (int pos=0;pos<data[0].length;pos++) {
				switch (chat) {
					case 0:
						data[0][pos]=APS[pos][0];
						break;
					case 1:
						data[0][pos]=RPS[pos][0]/RPS_HEIGHT;
						paints=paintsRed;
						break;					
					case 2:
						data[0][pos]=CPS[pos][0];
						break;
					case 3:
						//if (!WorldGraphLayer.explotando) paints=paintsRed; 
						data[0][pos]=MPS[pos][0]/RPS_HEIGHT;
						break;
					case 4:
						data[0][pos]=suma[pos]*100;
						break;
					
				}
			}
			try {
				axisChartDataSet = new AxisChartDataSet(data, legendLabels[0], paints[0], ChartType.AREA, areaChartProperties );
			} catch (ChartDataException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			dataSeries[chat].addIAxisPlotDataSet( axisChartDataSet );
			axisChart= new AxisChart( dataSeries[chat], chartProperties, axisProperties, legendProperties, width, height );
			axisChart.setGraphics2D( (Graphics2D) this.chats[chat].getGraphics() );
			//axisChart.getYAxis().setScaleCalculator(scaleCalculator);
			try {
				axisChart.render();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
			}
		}

	}

}
