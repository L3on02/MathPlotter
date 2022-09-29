package Grid;

import javax.swing.*;

import expressions.Expression;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.image.BufferStrategy;

@SuppressWarnings("serial")
public class Grid extends JFrame implements MouseMotionListener,MouseWheelListener, ActionListener{
	private BufferStrategy bufferStrategy;
	public int width = 1024;
	public int height = 786;
	private int xOffsetToMid = 0;
	private int yOffsetToMid = 0;
	private int mousePosX;
	private int mousePosY;
	private int gridSize = 20; // anzahl an Pixel pro kleinem Feld (kontroliert Zoom)
	private String toPlot = "0";
	
	/**
	 * Konstruktor aus der Übung
	 */
	public Grid() {
		super();
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		this.setSize(width, height);
		this.setTitle("Plotter");
		this.setVisible(true);
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		
		this.createBufferStrategy(2);
        this.setIgnoreRepaint(true);
        this.bufferStrategy = getBufferStrategy();
        
        /*
        JTextField TextField = new JTextField(20);
        TextField.addActionListener(new ActionListener() {
        	@Override
        	public void actionPerformed(ActionEvent evt) {
        	    toPlot = TextField.getText();
        	}

        });
        TextField.setBounds(50, 100, 200, 30);
        this.add(TextField);
       	*/
        
        this.setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
		this.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
            	//resets Grid
            	if(e.getButton() == 2) {
            		// toPlot = (field.getText());
            		xOffsetToMid = 0;
            		yOffsetToMid = 0;
            		gridSize = 20;
            	}
            	mousePosX = e.getX();
                mousePosY = e.getY();
                repaint();
            }
        }); 
        this.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                int dx = e.getX() - mousePosX;
                int dy = e.getY() - mousePosY;
                xOffsetToMid += dx;
                yOffsetToMid += dy;
                
                mousePosX = e.getX();
                mousePosY = e.getY();
                repaint();
            }
        });
        
        this.addMouseWheelListener(this); 
        repaint();
        
	}



	/**
	 * coordinates zeichnet ein Koordinatenkreuz.
	 */
	public void coordinates(Graphics g) {
		int height = this.getHeight();
		int width = this.getWidth();
		int midX = (width / 2) + xOffsetToMid;
		int midY = (height / 2) + yOffsetToMid;

		// Grid

		// links von Y-Achse
		for (int i = midX - gridSize, counter = 1; i > 0; i -= gridSize, counter++) {
			g.setColor(counter%5==0?Color.GRAY:Color.LIGHT_GRAY);	
			g.drawLine(i, height, i, 0);
		}
		// rechts von Y-Achse
		for (int i = midX + gridSize, counter = 1; i < width; i += gridSize, counter++) {
			g.setColor(counter%5==0?Color.GRAY:Color.LIGHT_GRAY);
			g.drawLine(i, height, i, 0);
		}

		// über der X-Achse
		for (int i = midY - gridSize, counter = 1; i > 0; i -= gridSize, counter++) {
			g.setColor(counter%5==0?Color.GRAY:Color.LIGHT_GRAY);
			g.drawLine(width, i, 0, i);
		}
		// unter der X-Achse
		for (int i = midY + gridSize, counter = 1; i < height; i += gridSize, counter++) {
			g.setColor(counter%5==0?Color.GRAY:Color.LIGHT_GRAY);
			g.drawLine(width, i, 0, i);
		}
		
		// Achsen
		g.setColor(Color.BLACK);
		Graphics2D g2 = (Graphics2D) g;  
		g2.setStroke(new BasicStroke(2)); // macht Linien dicker
		g2.drawLine(midX, height, midX, 0);
		g2.drawLine(width, midY, 0, midY);
		g2.setStroke(new BasicStroke(1)); // Linien wieder dünn

	}

	/**
	 * labels zeichnet Achsenbeschriftungen bei ganzen Zahlen.
	 */
	public void labels(Graphics g) {
		int height = this.getHeight();
		int width = this.getWidth();
		int midX = (width / 2) + xOffsetToMid;
		int midY = (height / 2) + yOffsetToMid;

		g.setColor(Color.BLACK);

		int STOP;
		{ // damit a und b danach wieder gelöscht werden 
			// damit die Beschriftungen immer korrekt angezeigt werden
			int a = width + Math.abs(xOffsetToMid);
			int b = height + Math.abs(yOffsetToMid);
			STOP = (a <= b ? b : a);
		}
		
		
		
		// zeichnet die 0
		g.clearRect((midX - 15),(midY + 2), 13, 15); // kleiner weißer Hintergrund für die Zahlen
		g.drawString("0",(midX - 12),(midY + 14));
		
		int UP = 0;
		int DOWN = 0;
		
		for (int i = 0; i < STOP; i += gridSize, UP++, DOWN--) {
			if (UP % 5 == 0&&(DOWN != 0)) {
				// Y-Achse positiv
				g.clearRect((midX - 20),(midY - i - 10), 13, 15); // kleiner weißer Hintergrund für die Zahlen
				String lableX = " " + String.valueOf(UP / 5);
				g.drawString(lableX,(midX - 20),(midY - i + 2));
				
				// X-Achse positiv
				g.clearRect((midX - i - 8),(midY + 3), 13, 15); // kleiner weißer Hintergrund für die Zahlen
				String lableY = String.valueOf(DOWN / 5);
				g.drawString(lableY,(midX - i - 7),(midY + 13));
			}
			
			if ((DOWN % 5 == 0)&&(DOWN != 0)) {
				// Y-Achse negativ
				g.clearRect((midX - 20),(midY + i - 10), 13, 15); // kleiner weißer Hintergrund für die Zahlen
				String lableX = String.valueOf(DOWN / 5);
				g.drawString(lableX,(midX - 20),(midY + i + 2));
				
				// X-Achse negativ
				g.clearRect((midX + i - 8),(midY + 3), 13, 15); // kleiner weißer Hintergrund für die Zahlen
				String lableY = " " + String.valueOf(UP / 5);
				g.drawString(lableY,(midX + i - 7),(midY + 13));
			}
		}
	}

	/**
	 * plot zeichnet die durch die Expression e angegebene Funktion in der
	 * angegebenen Farbe in g.
	 */
	public void plot(Graphics g, Expression e, Color c) {
		int height = this.getHeight();
		int width = this.getWidth();
		int midX = (width / 2) + xOffsetToMid;
		int midY = (height / 2) + yOffsetToMid;
		

		double stepX = 1.0 / (5.0 * gridSize); // jeder Pixel entspricht stepX abhängig von dem Darstellungsraum
		double stepToMid = -(midX / (gridSize * 5.0)); // verschiebt den Graphen so, dass die Null in der
													   // Mitte des Fensters und nicht am Rand liegt

		// macht Linien dicker
		Graphics2D g2 = (Graphics2D)g;
		g2.setStroke(new BasicStroke(2));
		g2.setColor(c);
		
		
		int valueY0 = (int) -(5 * gridSize * (e.eval(stepToMid))) + midY;;
		
		for (int i = 0; i < width; i++) {
		
			int valueY1;
			double funcValue = e.eval(stepToMid + i * stepX);
			
			if(!Double.isNaN(funcValue)) {
				valueY1 = (int) -(5 * gridSize * (funcValue)) + midY;
			}															 // Stelle von x (i) die
			else {														 // y-Koordinate von der
				valueY1 = 4 * width;	// damit es auserhalb des 	     // Funktion
			}							// Fensters liegt
			
			if (Math.abs(valueY0 - valueY1) <= height + 1) { // Damit Polstellen richtig abgebildet werden
				g2.drawLine(i, valueY0, (i + 1), valueY1);
			}
				
			valueY0 = valueY1;
		}
	}

	/**
	 * paint-Methode wie in der Vorlesung
	 */
	public void paint(Graphics g) {
		try {
			g = this.bufferStrategy.getDrawGraphics();
			int height = this.getHeight();
			int width = this.getWidth();
			g.clearRect(0, 0, width, height);

			coordinates(g);
			labels(g);
			plotFunctions(g);

			g.dispose();
			bufferStrategy.show();
		} catch (Exception e) {
		};
	}

	/**
	 * speichert die zu zeichnenden Funktionen
	 */
	private void plotFunctions(Graphics g) {
		
		Expression main = Expression.parseInfixString(this.toPlot);
		if(toPlot != "0") 
			plot(g,main, Color.BLUE);
		
		Expression fn11 = Expression.parseInfixString("3*x-6");
		plot(g,fn11, Color.BLUE);
		
		Expression fn12 = Expression.parseInfixString("-sin(x)");
		plot(g,fn12, Color.BLUE);
		
		Expression fn13 = Expression.parseInfixString("x^3-3*x^2");
		plot(g,fn13, Color.BLUE);
		
		Expression fn14 = Expression.parseInfixString("-(6-5)-tan(x)");
		plot(g,fn14, Color.BLUE);
		
		
	}
	
	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		int wheelRot = -e.getWheelRotation(); // scroll up -> zoom in
		
		int mouseX = e.getX();
		int mouseY = e.getY();
		int orgiginX = (this.getWidth() / 2) + this.xOffsetToMid;
		int orgiginY = (this.getHeight() / 2) + this.yOffsetToMid;
		
		int distanceMouseToOriginX = orgiginX - mouseX;
		int distanceMouseToOriginY = orgiginY - mouseY;
		
		// dynamically adjusts "amount of zoom" depending on current level of zoom
		int zoomFactor = this.gridSize / 10;
		if(zoomFactor < 1)
			zoomFactor = 1;
		
		// in case zoomed in to far -> revert to previous level
		int tempX = this.xOffsetToMid;
		int tempY = this.yOffsetToMid;
		
		// calculates new offset to mid now adjusted for level of zoom
		this.xOffsetToMid += Math.round(wheelRot *(distanceMouseToOriginX /(gridSize / zoomFactor)));
		this.yOffsetToMid += Math.round(wheelRot *(distanceMouseToOriginY /(gridSize / zoomFactor)));
		this.gridSize += zoomFactor * wheelRot;
		
		
		// prevents zooming in / out too far
		if(this.gridSize < 5) {
			this.gridSize = 5;
			this.xOffsetToMid = tempX;
			this.yOffsetToMid = tempY;
		}
		if(this.gridSize > 100000) {
			this.gridSize = 100000;
			this.xOffsetToMid = tempX;
			this.yOffsetToMid = tempY;
		}
		
		repaint();
	}



	@Override
	public void mouseDragged(MouseEvent e) {
		
	}
	

	@Override
	public void mouseMoved(MouseEvent e) {
		
	}



	@Override
	public void actionPerformed(ActionEvent e) {
		
	}

}