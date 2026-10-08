package desarrolloInterfacesRA1;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JToolBar;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class PaintMarcosFdez extends JPanel {
	
	private List<Point>points = new ArrayList<>();
	private List<Color>colors = new ArrayList<>();
	private List<String>tipos = new ArrayList<>();
	
	private JPanel lienzo;
	private Color colorActual = Color.BLACK;
	
	private String modoActual ="Dibujo libre";
	private Point pInicio = null;
	private Point pFinal = null;
	
	
	public PaintMarcosFdez(){
		setLayout(new BorderLayout());
		
		JToolBar tb = new JToolBar();
		tb.setFloatable(false);

		JButton color = new JButton("Color");
		color.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				losColores();
			}
		});
		JLabel lb = new JLabel ("5 px");
		JSlider sl = new JSlider(1, 50, 5);
		sl.addChangeListener(new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent e) {
				// TODO Auto-generated method stub
				lb.setText(sl.getValue()+ "px");
			}
		});
		JButton libre = new JButton("Dibujo libre");
		libre.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				modoActual = "Dibujo libre";
			}
		});
		JButton recto = new JButton("Linea recta");
		recto.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				modoActual ="Linea recta";
			}
		});
		JButton circulo = new JButton("Circulo");
		circulo.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				modoActual ="Circulo";
			}
		});
		JButton ovalo = new JButton("Ovalo");
		ovalo.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				modoActual = "Ovalo";
			}
		});
		JButton cuadrado = new JButton("Cuadrado");
		cuadrado.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				modoActual ="Cuadrado";
			}
		});
		
		tb.add(color);
		tb.add(sl);
		tb.add(lb);
		tb.add(libre);
		tb.add(recto);
		tb.add(circulo);
		tb.add(ovalo);
		tb.add(cuadrado);
		
		add(tb, BorderLayout.NORTH);
		
		lienzo = new JPanel() {
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				
				Graphics2D g2 = (Graphics2D) g;
				g2.setStroke(new BasicStroke(sl.getValue()));
				
				for(int i = 1; i<points.size(); i++) {
					Point p1 = points.get(i - 1);
					Point p2 = points.get(i);
					
					if(p1 != null && p2 != null) {
						g2.setColor(colors.get(i));
						lasFiguras(g2, p1, p2, tipos.get(i));
					}
				}
				//previsualizar
				
				if(!modoActual.equals("Dibujo libre") && pInicio != null && pFinal != null) {
					g2.setColor(colorActual);
					lasFiguras(g2, pInicio, pFinal, modoActual);
					
				}
			}
		};
			lienzo.setBackground(Color.WHITE);
			
			lienzo.addMouseListener(new MouseAdapter() {		
				
				@Override
				public void mousePressed(MouseEvent e) {
					// TODO Auto-generated method stub
					
					pInicio = e.getPoint();
					pFinal = pInicio;
					
					if(modoActual.equals("Dibujo libre")){
					points.add(e.getPoint());
					colors.add(colorActual);
					tipos.add("Dibujo libre");
					}
					lienzo.repaint();
				}
				
				@Override
				public void mouseReleased(MouseEvent e) {
					// TODO Auto-generated method stub
					pFinal = e.getPoint();
					
					if(modoActual.equals("Dibujo libre")) {
						points.add(null);
						colors.add(null);
						tipos.add(null);
					}else if (pInicio != null) {
						points.add(pInicio);
						colors.add(colorActual);
						tipos.add(modoActual);
						
						points.add(pFinal);
						colors.add(colorActual);
						tipos.add(modoActual);
						
						points.add(null);
						colors.add(null);
						tipos.add(null);
									
					}
					pInicio = null;
					pFinal=null;
					lienzo.repaint();
				}
				
				
			});
			lienzo.addMouseMotionListener(new MouseMotionAdapter() {
				
				@Override
				public void mouseDragged(MouseEvent e) {
					// TODO Auto-generated method stub
					pFinal = e.getPoint();
					
					if(modoActual.equals("Dibujo libre")) {
						points.add(e.getPoint());
						colors.add(colorActual);
						tipos.add("Dibujo libre");
						
					}
				lienzo.repaint();
				}
				
			});
			add(lienzo, BorderLayout.CENTER);
		
	}
	
		protected void lasFiguras(Graphics2D g2, Point p1, Point p2, String forma) {
			int x = Math.min(p1.x, p2.x);
			int y = Math.min(p1.y, p2.y);
			int w = Math.abs(p1.x - p2.x);
			int h = Math.abs(p1.y - p2.y);
			
			switch(forma) {
			case "Dibujo libre":
			case "Linea recta":
				g2.drawLine(p1.x, p1.y, p2.x, p2.y);
				break;
			case "Circulo":
				int d = Math.max(w, h);
				g2.drawOval(x, y, d, d);
				break;
			case "Ovalo":
				g2.drawOval(x, y, w, h);
				break;
			case ("Cuadrado"):
				g2.drawRect(x, y, w, h);
				break;
			}
			
		}
		
		protected void losColores() {
			Color colo = JColorChooser.showDialog(this, "Elige un color", colorActual);
			if(colo != null) {
				colorActual = colo;
			}
		}
	
    public static void main(String[] args) {
    
        JFrame frame = new JFrame("Paint");
        PaintMarcosFdez paint = new PaintMarcosFdez();
        
       frame.setTitle("Paint");
       frame.setSize(600, 500);
       frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       frame.setLocationRelativeTo(null); 
       
        JMenuBar mb = new JMenuBar();
        
        JMenu archivo = new JMenu("Archivo");
        
        JMenuItem nuevo = new JMenuItem("Nuevo");
        nuevo.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				paint.points.clear();
				paint.colors.clear();
				paint.tipos.clear();
				paint.lienzo.repaint();
			}
		});
        JMenuItem salir = new JMenuItem("salir");
        	salir.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					System.exit(0);
				}
			});
        	
        	archivo.add(nuevo);
        	archivo.add(salir);
        	
        	mb.add(archivo);
        	
        	frame.setJMenuBar(mb);
        	frame.add(paint);
        	frame.setVisible(true);
    }	
}