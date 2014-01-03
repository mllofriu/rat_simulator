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

import robot.RobotFactory;
import support.Configuration;

public class LPSChats extends JFrame {
	private final int CANT_CHATS =RobotFactory.getRobot().getColorsLandmarks().length;
	private static final int DEFAULT_SIZE = Configuration.getInt("PerceptualSchema.DEFAULT_SIZE");

	public static final int width= 500;
	public static final int height= 150;
	private JPanel chats[]=new JPanel[CANT_CHATS];
	AxisChart axisChart;
	ChartProperties chartProperties= new ChartProperties();
	AxisProperties axisProperties= new AxisProperties();
	LegendProperties legendProperties= null; //new LegendProperties();
	double[][] data=new double[1][DEFAULT_SIZE*2];
	
	DataSeries dataSeries[];
	String[][] legendLabels;
	Paint[][][] paints;
	Paint[][] paintsRosa= {{Color.MAGENTA}};
	Paint[][] paintsGreen= {{Color.GREEN}};
	Paint[][] paintsAmarillo= {{Color.ORANGE}};
	AxisChartDataSet axisChartDataSet=null;
	AreaChartProperties areaChartProperties= new AreaChartProperties();
	
	public LPSChats() {
		super("Grafica esquemas visuales");
		int chat;
		
		legendLabels = new String[CANT_CHATS][1];
		paints = new Paint[CANT_CHATS][1][1];
		String [] yAxisTitle = new String[CANT_CHATS];
		
		for (chat=0;chat<CANT_CHATS; chat++) {
			legendLabels[chat][0]=""+chat;
			paints[chat][0][0] = RobotFactory.getRobot().getColorsLandmarks()[chat];
			yAxisTitle[chat] = legendLabels[chat][0];
		}
		
		// inicializo graficas
		axisProperties.getYAxisProperties().setShowAxisLabels(false);
		axisProperties.getXAxisProperties().setShowAxisLabels(false);
		String xAxisTitle= "Perceptual Schema Position";
		String title= "Motivational Perceptual Schema";
		String[] xAxisLabels=new String[DEFAULT_SIZE*2];
		this.getContentPane().setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		for (int pos=0;pos<xAxisLabels.length;pos++)
			xAxisLabels[pos]=""+pos;
		
		dataSeries=new DataSeries[CANT_CHATS];

		c.fill = GridBagConstraints.BOTH;
		c.gridx = 0;
		c.weightx=width;
		c.weighty=height;
		// inicializo Panel
		this.setSize( width, height*5 );
		for (chat=0;chat<CANT_CHATS; chat++) {
			c.gridy = chat;
			dataSeries[chat] = new DataSeries( xAxisLabels, null, null, yAxisTitle[chat]);
			this.chats[chat]=new JPanel( true );
			this.chats[chat].setSize( width, height );
			this.getContentPane().add(this.chats[chat],c);
		}
		this.setVisible( true );
	}
	
	public void setSchema(double [][] landsPSs) {
		Paint[][] paint;

		for (int chat=0;chat<CANT_CHATS; chat++) {

			for (int pos=0;pos<data[0].length;pos++)
				data[0][pos]=4*landsPSs[chat][pos];
			paint=paints[chat];

			try {
				axisChartDataSet = new AxisChartDataSet(data, legendLabels[0], paint[0], ChartType.AREA, areaChartProperties );
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
				e.printStackTrace();
			}
		}

	}
}
