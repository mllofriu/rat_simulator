/* SCCS  %W%---%G%--%U% */

// Copyright: Copyright (c) 1997 University of Southern California Brain Project.
// Copyright: This software may be freely copied provided the toplevel
// Copyright: COPYRIGHT file is included with each such copy.
// Copyright: Email nsl@java.usc.edu.

package nslj.src.display;

import nslj.src.lang.*;

import java.util.*;
import java.awt.*;

public class NslGrid extends Panel {

    private Image gridImage;
    private Image hBorderImage;
    private Image vBorderImage;

    private Graphics gridGraphics;
    private Graphics hBorderGraphics;
    private Graphics vBorderGraphics;

    private Scrollbar horizontal, vertical;
    private FontMetrics fontMetrics;
    private Font font;
    private Color background, foreground;
    private Component component;

    private boolean gridInitialized = false;
    private boolean editFlag        = true;
    private boolean redraw          = false;
    private boolean mouseDown       = false;

    private int horizontalScrollUnit;
    private int verticalScrollUnit;	

    private int numberOfColumns, numberOfRows;

    private int imageWidth,  imageHeight;
    private int screenWidth, screenHeight;
    private int gridWidth,   gridHeight;
    private int borderWidth, borderHeight;
    private int cellHeight,  cellWidth;
    private int hSbHeight,   hSbWidth;
    private int vSbHeight,   vSbWidth;
    private int vSbShift,    hSbShift;
    private int hSbValue,    vSbValue;

    private int prevRow, prevColumn;
    private int currentRow, currentColumn;

    private int drawX, drawY;

    private Cell[] headerElements;
    private Row[]  rowElements;

    public NslGrid(NslFrame frame, int rows, int columns) {
	
	super();

	//System.out.println("Creating Grid: "+rows+"x"+columns);
	
	setLayout(null);

	horizontal = new Scrollbar(Scrollbar.HORIZONTAL);
	//horizontal.addAdjustmentListener();
	vertical = new Scrollbar(Scrollbar.VERTICAL);
	//vertical.addAdjustmentListener();

	add(horizontal);
	add(vertical);

	hSbValue = 0;
	vSbValue = 0;

	String fn = frame.getFontName()==null?"Times":frame.getFontName();
	String bg = frame.getBackgroundColor()==null?"white":frame.getBackgroundColor();
	String fg = frame.getForegroundColor()==null?"black":frame.getForegroundColor();

	font       = new Font(fn, Font.PLAIN,12);
	background = NslColor.getColor(bg);
	foreground = NslColor.getColor(fg);

	numberOfRows    = rows;
	numberOfColumns = columns;

	borderWidth  = 80;
	borderHeight = 25;

	cellWidth  = 80;
	cellHeight = 25;

	gridWidth  = borderWidth  + cellWidth*numberOfColumns;
	gridHeight = borderHeight + cellHeight*numberOfRows;

	headerElements = new Cell[numberOfColumns+1];
	rowElements    = new Row[numberOfRows+1];

	Cell cell = new Cell();
	headerElements[0] = cell;
	for (int i=1; i<=numberOfColumns; i++) {
	    cell = new Cell();
	    /*cell.bgColor = Color.lightGray;
	    cell.fgColor = Color.black;*/
	    cell.setValue(""+(i-1));
	    headerElements[i]=cell;
	}	

	rowElements[0] = new Header(headerElements); 

	createRows();
    }

    public void setEditable(boolean flag) {
	editFlag = flag;
    }

    private void setHScrollValues() {
	if (gridInitialized) {
	    int increment = Math.round(screenWidth/horizontalScrollUnit);
	    horizontal.setValues(hSbValue,increment,0,Math.round(imageWidth/horizontalScrollUnit));
	    horizontal.setBlockIncrement((int)(increment*0.9));
	}
    }

    private void setVScrollValues() {
	if (gridInitialized) {
	    int increment = Math.round((screenHeight-borderHeight)/verticalScrollUnit);
	    vertical.setValues(vSbValue,increment,0,Math.round((imageHeight-borderHeight)/verticalScrollUnit));
	    vertical.setBlockIncrement((int)(increment*0.9));
	}
    }

    public void setDrawableArea() {
	if (gridInitialized) {
	    imageWidth  = (gridWidth > screenWidth) ? gridWidth : screenWidth;
	    imageHeight = (gridHeight > screenHeight) ? gridHeight : screenHeight;
	}
    }

    public void setBounds(int x, int y, int width, int height) {

	super.setBounds(x,y,width,height);

	//System.out.println("SetBounds was called: "+x+", "+y+", "+width+", "+height);

	Insets in = getInsets();
	drawX = in.left;
	drawY = in.top;

	hSbHeight = horizontal.getMinimumSize().height;
	vSbWidth  = vertical.getMinimumSize().width;

	screenWidth  = width  - (in.left + in.right) - vSbWidth;
	screenHeight = height - (in.top + in.bottom) - hSbHeight;

	vertical.setBounds(screenWidth+in.left,drawY,vSbWidth,screenHeight);
	//System.out.println("Vertical: "+(screenWidth+in.left)+", "+drawY+", "+vSbWidth+", "+screenHeight);
	horizontal.setBounds(drawX,screenHeight+in.top,screenWidth,hSbHeight);
	//System.out.println("Horizontal: "+drawX+", "+(screenHeight+in.top)+", "+screenWidth+", "+hSbHeight);

	setDrawableArea();
	setHScrollValues();
	setVScrollValues();

	repaint();
    }

    public boolean createRows() {
	Cell   cell;
	Cell[] newRow;

	TextField comp;

	for (int j=1; j<=numberOfRows; j++) {
	    newRow = new Cell[numberOfColumns+1];
	    cell = new Cell();
	    newRow[0] = cell;
	    comp = new TextField(""+(j-1));
	    cell.setComponent(comp);
	    cell.setValue(""+(j-1));
	    for (int i=1; i<=numberOfColumns; i++) {
	    	cell = new Cell();
	    	comp = new TextField("0.0");
	    	cell.setComponent(comp);
	    	cell.setValue("0.0");
	    	newRow[i]=cell;
	    }
	    rowElements[j] = new Row(newRow);
	}
	
	setVScrollValues();

	gridImage = null;
	vBorderImage = null;
	repaint();

	return true;
    }

    private void drawGrid(Graphics g) {

	Graphics tg = g.create(drawX,drawY,imageWidth,borderHeight);
	rowElements[0].draw(tg);
	tg.dispose();

	for (int i=1; i <= numberOfRows; i++) {
	    tg = g.create(drawX,cellHeight*i,imageWidth,cellHeight);
	    rowElements[i].draw(tg);
	    tg.dispose();
	}

	int height = (imageHeight > gridHeight) ? gridHeight : imageHeight;
	g.setColor(foreground);
	//System.out.println("ImageHeight: "+imageHeight);
	//System.out.println("GridHeight:  "+gridHeight);
	//System.out.println("Last: "+drawX+", "+height+", "+imageWidth+", "+height);

	g.drawLine(drawX,height-1,imageWidth-1,height-1);
	
	return;
    }

    private void drawVerticalBorder(Graphics g) {
	int y;
	int y1;

	g.setColor(foreground);
	g.drawLine(drawX,drawY,drawX,screenHeight);

	if (vBorderImage == null) {
	    vBorderImage = createImage(borderWidth,gridHeight);
	    vBorderGraphics = vBorderImage.getGraphics();
		
	    y1 = borderHeight;	
	    for (int i=0; i<=numberOfRows; i++) {
		y = cellHeight*i+1;
		vBorderGraphics.setColor(Color.lightGray);
		vBorderGraphics.fillRect(0,y,borderWidth,(y1-y));
		vBorderGraphics.setColor(background);
		vBorderGraphics.drawLine(0,y,borderWidth,y);
		vBorderGraphics.drawLine(0,y,0,y1);
		vBorderGraphics.setColor(foreground);
		vBorderGraphics.drawLine(borderWidth-1,y,borderWidth-1,y1);
		vBorderGraphics.drawLine(0,y1,borderWidth-1,y1);
		if (i>0) {
		    vBorderGraphics.drawString(""+(i-1),3,y+(cellHeight + fontMetrics.getHeight())/2 - fontMetrics.getDescent());
		}
		y1 += cellHeight;
	    }
	}	
	vSbShift = vSbValue*verticalScrollUnit;
	vSbShift = (screenHeight-imageHeight) > -vSbShift ? (imageHeight-screenHeight) : vSbShift;
	int actualY = drawY-vSbShift;
	
	if (vBorderImage != null) {
	    g.drawImage(vBorderImage,drawX+1,actualY,this);

	}

	
	g.setColor(Color.lightGray);
	g.fillRect(drawX+1,0,borderWidth,borderHeight);

	g.setColor(background);
	g.drawLine(drawX+1,borderHeight,borderWidth,borderHeight);
	g.drawLine(drawX+1,0,drawX+1,borderHeight);

	g.setColor(foreground);
	g.drawLine(borderWidth,0,borderWidth,borderHeight);
	g.drawLine(drawX+1,0,borderWidth,0);
	g.drawLine(drawX+1,borderHeight,borderWidth,borderHeight);

	int height = (imageHeight > gridHeight) ? gridHeight : imageHeight;
	g.setColor(foreground);
	g.drawLine(drawX,height-1,imageWidth-1,height-1);
	
    }

    private void invalidate(boolean flag) {
	if (rowElements != null) {
	    for (int i=0; i<=numberOfRows; i++) {
		rowElements[i].invalidate(flag);	
	    }	
	}	
    }

    public Component getComponent(int row, int column) {
	Row currRow;
	Cell cell;
	
	if ((row < 0) || (row > numberOfRows) 
	    || (column <= 0) || (column > numberOfColumns))
	    return null;

	currRow = rowElements[row];
	cell    = currRow.getCell(column);
	return  cell.getComponent();
    }

    public String getComponentValue (int row, int column) {
	Component cmp = getComponent(row, column);
	if (cmp instanceof TextField) 
	    return ((TextField)cmp).getText();
	else
	    return null;
    }

    public boolean setValue(int row, int column, String value) {
	Row currRow;
	Cell cell;
	
	if ((row <= 0) || (row > numberOfRows) 
	    || (column <= 0) || (column > numberOfColumns))
	    return false;

	rowElements[row].getCell(column).setValue(value);
	gridImage = null;
	repaint();

	return true;
    }
    
    public void cleanCell() {
    	if (component != null) {
    	    component.setVisible(false);

    	}
    }

    private void displayCell() {
	Component oldComp;
	Row	row;

	if (component != null) {

	    row = rowElements[prevRow];
	    Cell cell = row.getCell(prevColumn);

	    String oldVal = cell.getValue();
	    String newVal = getComponentValue(prevRow,prevColumn);
	
	    if ((oldVal != null) && (oldVal.equals(newVal) == false)) {
		setValue(prevRow,prevColumn,newVal);
		if (rowElements != null) {
		    row.invalidate(true);
		    redraw = true;
		    repaint();
		}	
	    }	
	}

	oldComp = getComponent(currentRow,currentColumn);
	if (component != oldComp) {
	    if (component != null)
		remove(component);
	    component = oldComp;
	    if (component != null)
		add(component);
	}

	if (component != null) {
	    if ((cellHeight*currentRow-vSbShift) >= borderHeight) {
		int compWidth,compHeight;

		compWidth = (screenWidth > (cellWidth*currentColumn-hSbShift + cellWidth)) 
		            ? cellWidth 
			    : (screenWidth - (cellWidth*currentColumn-hSbShift));

		compHeight = (screenHeight > (cellHeight*currentRow-vSbShift + cellHeight)) 
			     ? cellHeight 
			     : (screenHeight - (cellHeight*currentRow-vSbShift));

		if ((cellWidth*currentColumn-hSbShift) >= borderWidth)
		    component.setBounds(cellWidth*currentColumn-hSbShift, 
			cellHeight*currentRow-vSbShift, compWidth, compHeight);
		else
		    component.setBounds(borderWidth, cellHeight*currentRow-vSbShift, 
			cellWidth-hSbShift, compHeight);

		component.setVisible(true);
		component.requestFocus();

	    } else {
		component.setVisible(false);
	    }		
	}	
    }

    public boolean mouseDown(Event evt, int x, int y) {
	x += hSbShift;
	y += vSbShift;
	mouseDown = true;
	
	return false;
    }

    public boolean mouseUp(Event evt, int x, int y) {	
	if (y <= borderHeight || x <= borderWidth
	    || y > gridHeight || x > gridWidth) {
	    return true;
	}

	x += hSbShift;
	y += vSbShift;

	Point p = new Point(0,0);
	p.x = (int)(x/cellWidth);
	p.y = (int)(y/cellHeight);
	
	prevRow = currentRow;
	prevColumn = currentColumn;
	currentRow = p.y;
	currentColumn = p.x;
	
	if (editFlag) 
	    displayCell();

	Row row = rowElements[currentRow];
	row.invalidate(true);
	redraw = true;
	repaint();
	
	mouseDown = false;
	return true;
    }

    public boolean handleEvent(Event evt) {

	switch (evt.id) {
	    case Event.KEY_PRESS:
		if (evt.key==9) {	
		    prevRow = currentRow;
		    prevColumn = currentColumn;

		    if ((currentRow == numberOfRows)&&(currentColumn == numberOfColumns))
			return super.handleEvent(evt);

		    if (currentColumn == numberOfColumns) {
			currentRow++;
			currentColumn = 1;
		    } else {
			currentColumn++;
		    }

		    displayCell();
		    repaint();
		}	
	    case Event.SCROLL_LINE_UP:
	    case Event.SCROLL_LINE_DOWN:
	    case Event.SCROLL_PAGE_UP:
	    case Event.SCROLL_PAGE_DOWN:
	    case Event.SCROLL_ABSOLUTE:
		if (evt.target == horizontal) {
		    hSbValue = horizontal.getValue();
		    setHScrollValues();
		    repaint();
		    return true;
		} else if (evt.target == vertical) {
		    vSbValue = vertical.getValue();
		    setVScrollValues();
		    repaint();
		    return true;
		}	
  	} 
	return super.handleEvent(evt);
    }

    public void paint(Graphics g) {

	Graphics temp;

	super.paint(g);

	g.setFont(font);

	if (!gridInitialized) {
	    fontMetrics          = g.getFontMetrics();
	    horizontalScrollUnit = fontMetrics.charWidth(' ');
	    verticalScrollUnit   = fontMetrics.getHeight() + 15;

	    borderHeight    = cellHeight = verticalScrollUnit;

	    gridWidth  = borderWidth  + cellWidth*numberOfColumns;
	    gridHeight = borderHeight + cellHeight*numberOfRows;

	    gridInitialized = true;

	    setDrawableArea();
	}

	if (gridImage==null) {
	    setDrawableArea();
	    gridImage    = createImage(imageWidth,imageHeight);
	    gridGraphics = gridImage.getGraphics();
	    invalidate(true);
	    drawGrid(gridGraphics);
	    setHScrollValues();
	    setVScrollValues();
	} else if (redraw) {
	    setDrawableArea();
	    drawGrid(gridGraphics);
	    redraw = false;	
	}

	if (gridImage!=null) {

	    hSbShift = hSbValue*horizontalScrollUnit;
	    hSbShift = (screenWidth-imageWidth) > -hSbShift ? (imageWidth-screenWidth) : hSbShift;

	    vSbShift = vSbValue*verticalScrollUnit;
	    vSbShift = (screenHeight-imageHeight) > -vSbShift ? (imageHeight-screenHeight) : vSbShift;

	    drawVerticalBorder(g);

	    int x = 1;
	    int y = borderHeight + 1;
	    int actualX = drawX-hSbShift;
	    int actualY = drawY-vSbShift;

	    x += borderWidth;
try{
	    g.clipRect(x,0,screenWidth,screenHeight);
	    if (hBorderImage == null) {
		hBorderImage = createImage(gridWidth, y);
		hBorderGraphics = hBorderImage.getGraphics();
		Header row = (Header)rowElements[0];
		row.invalidate(true);

		//System.out.println("--->Before header draw: "+(x-1)+", "+0+", "+gridWidth+", "+y);

		row.draw(hBorderGraphics,new Rectangle(x-1, 0, gridWidth, y));
	    }
	    if (hBorderImage != null) {
	    	g.drawImage(hBorderImage,actualX,drawY,this);

	    }
	    g.clipRect(x,y,screenWidth,screenHeight);
	    if (gridImage != null) {
	        g.drawImage(gridImage,actualX,actualY,this);
	    }
	   } catch (Exception e) {
	   	e.printStackTrace();
	   }
	}

	if (horizontal.isVisible() && vertical.isVisible()) {
	    g.setColor(Color.lightGray);
	    g.fillRect(screenWidth,screenHeight,vSbWidth,hSbHeight);	
	}

	if (editFlag)
	    displayCell();
    }

    public void update(Graphics g) {
	paint(g);
    }

    private class Row {	
	protected int x, y, rH, rW;
	protected boolean redraw = true;
	protected Cell[] elements;

	public Row(Cell[] cells) {
	    elements = cells;
	}

	public Cell getCell(int i) {
	    if (i <= numberOfColumns) {
		return elements[i];
	    } else {
		return null;
	    }
	}

	public void invalidate (boolean flag) {
	    this.redraw = flag;
	    if (elements != null) {
		for (int i=0; i < elements.length; i++) {
		    elements[i].invalidate(flag);
	        }
	    }	
    	}   

	protected void drawBorder(Graphics g) {
	    int x1 = x + rW;
	    int y1 = y + rH;

	    //System.out.println("Row draw border: "+x+", "+y+", "+x1+", "+y);
	    g.setColor(foreground);
	    g.drawLine(x,y,x1,y);
	    g.drawLine(x,y,x,y1);
	    g.drawLine(x1,y,x1,y1);
	}

	protected void drawMargin (Graphics g) {
	    int x0 = x + 1;
	    int y0 = y + 1;
	    int x1 = cellWidth-1;
	    int y1 = y + rH - 1;
	    //System.out.println("Row draw margin: "+x0+", "+y0+", "+x1+", "+y1);
	    g.setColor(Color.lightGray);
	    g.fillRect(x0,y0,x1,y1);
	
	    g.setColor(background);
	    g.drawLine(x0,y0,x1,y0);
	    g.drawLine(x0,y0,x0,y1);

	    g.setColor(Color.gray);
	    g.drawLine(x1,y0,x1,y1);
	    g.drawLine(x0,y1,x1,y1);

	    //System.out.println("-->Row draw margin title");
	    elements[0].drawValue(new Rectangle(1, y,cellWidth-1,rH),g);	
	    if (redraw){
		g.setColor(background);
		g.fillRect(cellWidth,y,rW,rH);
	    }	
		
	}

	public void draw(Graphics g) {
	    if (this.redraw && (g != null)) {
		int  startX,endX;
		
		Rectangle clipRect = g.getClipBounds();
		x = clipRect.x;
		y = clipRect.y;
		rH = clipRect.height;
		rW = clipRect.width;		

		//System.out.println("Row draw: "+x+", "+y+", "+rH+", "+rW);
	
		drawMargin(g);

		for (int i=1; i<=numberOfColumns; i++) {

		    startX = cellWidth*i +1;
		    endX   = cellWidth*i + cellWidth - 1;
			
		    g.setColor(Color.gray);
		    g.drawLine(cellWidth*i,y,cellWidth*i,y+rH);
		    g.setColor(foreground);
		    elements[i].draw(new Rectangle(startX,y,cellWidth-1,rH),g);
		    //System.out.println("Row draw change?: "+x+", "+y+", "+rH+", "+rW);
		}	

		drawBorder(g);

		this.redraw = false;
	    }
	} 
    }

    private class Header extends Row {

	public Header(Cell[] cells) {
	    super(cells);
	}

	public void draw (Graphics g) {
	    if (this.redraw && (g != null)) {
		Rectangle clipRect = g.getClipBounds();
		draw(g,clipRect);
	    }	
	    this.redraw = false;
	}

	public void draw(Graphics g, Rectangle rect) {
	    if (this.redraw && (g != null)) {
		int x0, y0, x1, y1, rW, rH;

		x  = rect.x;
		y  = rect.y;
		rW = rect.width;
		rH = rect.height;

	    	x1 = x + rW;
		y1 = y + rH;

		g.setColor(Color.lightGray);
		g.fillRect(x,y,rW,rH);

		//System.out.println("Header draw: "+x+", "+y+", "+x1+", "+y);

		g.setColor(foreground);
		g.drawLine(x,y,x1,y);
		g.drawLine(x,y1-1,x1,y1-1);
		g.drawLine(x,y,x,y1);
		g.drawLine(x1,y,x1,y1);

		for (int i=1; i<=numberOfColumns; i++) {
		    x0 = cellWidth*i+1;
		    y0 = y + 1;
		    x1 = x0+cellWidth-1;
		    y1 = y + rH-1;
		    g.setColor(Color.lightGray);
		    g.fillRect(x0,y0,cellWidth,rH);
			
		    g.setColor(background);
		    g.drawLine(x0,y0,x1,y0);
		    g.drawLine(x0,y0,x0,y1);
		
		    g.setColor(foreground);
		    g.drawLine(x1,y0,x1,y1);
		    g.drawLine(x0,y1,x1,y1);

		    elements[i].draw(new Rectangle(cellWidth*i+2, y+2,cellWidth-2,rH-2),g,Color.lightGray);
		}
	    }	
	    this.redraw = false;
	}
    }

    private class Cell {
	private	boolean	redraw = true;
	private String value;
	private Component component;

	public String getValue() {
	    return value;
	}

	public Component getComponent() {
	    return component;
	}

	public void setValue (String val) {
	    this.value = val;

	    if (component instanceof TextField) {
	        ((TextField)component).setText(this.value);
	    }
	
	    this.redraw = true;
	}

	public void setComponent (Component comp) {
	    component = comp;
	}

	public void invalidate(boolean flag) {
	    this.redraw = flag;
	}

	public void draw (Rectangle rect) {
	    if (component != null) {
		component.setBounds(rect.x,rect.y,rect.width,rect.height);
	    }
	}

	public void drawValue(Rectangle rect, Graphics g) {
	    if (this.redraw) {
		int x = rect.x + 2;
		int y = rect.y + (rect.height + fontMetrics.getHeight())/2 - fontMetrics.getDescent();
		g.setColor(foreground);
		g.drawString(value,x,y);
	    }		
		
	    this.redraw = false;
	}

	public void draw (Rectangle rect, Graphics g, Color bg) {
	    if (this.redraw) {
		int x = rect.x + 2;
		int y = rect.y + (rect.height + fontMetrics.getHeight())/2 - fontMetrics.getDescent();

		g.setColor(bg);
		g.fillRect(rect.x,rect.y,rect.width,rect.height-1);
		g.setColor(foreground);
		g.drawString(value,x,y);
	    }		
		
	    this.redraw = false;
	}

	public void draw (Rectangle rect, Graphics g) {
	    if (this.redraw) {
		int x = rect.x + 2;
		int y = rect.y + (rect.height + fontMetrics.getHeight())/2 - fontMetrics.getDescent();
		
		g.setColor(background);
		g.fillRect(rect.x,rect.y,rect.width,rect.height-1);
		g.setColor(foreground);
		g.drawString(value,x,y);
	    }		
		
	    this.redraw = false;
	}
    }
}