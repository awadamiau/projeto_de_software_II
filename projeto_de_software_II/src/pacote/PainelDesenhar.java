package pacote;

import java.awt.Color;
import java.awt.Graphics;
// Classe de adaptadores utilizada para implementar rotinas de tratamento de evento.
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import formas.Circulo;
import formas.Linha;
import formas.Ponto;
import formas.Retangulo;
import formas.Triangulo;
import lista.encadeada.Iterador;

@SuppressWarnings("serial")
public class PainelDesenhar extends JPanel {
	Ponto ponto1;
	Ponto ponto2;
	Ponto ponto3;
	Linha linha1;
	Linha linha2;
	Linha linha3;
	//variaveis de controle 
	public boolean controlePonto = false;
	public boolean controleLinha = false;
	public boolean controleTriangulo = false;
	public boolean controleRetangulo = false;
	public boolean controleCirculo = false;
	int mouseClickedCount = 0;
	
	// configura GUI e registra rotinas de tratamento de evento de mouse
	public PainelDesenhar(JLabel status) {
		// trata evento de movimento de mouse do frame
		addMouseMotionListener(new MouseMotionAdapter() // classe interna an�nima
		{

			@Override
			public void mouseMoved(MouseEvent event) {
				super.mouseMoved(event);
				status.setText("Moved in [" + event.getPoint().getX() + "," + event.getPoint().getY() + "]" + " - Tamanho Total = " + Principal.getPrincipal().getTamanho());
			}

			// armazena coordenadas da a��o de arrastar e repinta
			@Override
			public void mouseDragged(MouseEvent event) {
				if(controlePonto == true) {
				Principal.getPrincipal().inserirFim(new Ponto(event.getPoint().x, event.getPoint().y));
				status.setText("Dragged in [" + event.getPoint().getX() + "," + event.getPoint().getY() + "]" + " - Tamanho Total = " +  Principal.getPrincipal().getTamanho());
				repaint(); // repinta JFrame
				}
			}
		});
			
			addMouseListener(new MouseListener() {
				
				@Override
				public void mouseReleased(MouseEvent event) {
					
					if(controleLinha == true) {
					ponto2 = new Ponto(event.getPoint().x, event.getPoint().y);	
					Principal.getPrincipal().inserirFim(new Linha(ponto1, ponto2));
					status.setText("Realeased in [" + event.getPoint().getX() + "," + event.getPoint().getY() + "]" + " - Tamanho Total = " +  Principal.getPrincipal().getTamanho());
					repaint();
					}
					
				    if(controleRetangulo == true) {
						//calcular altura e largura usando seno e cosseno
						ponto2 = new Ponto(event.getPoint().x, event.getPoint().y);	
						double aux = ponto2.x - ponto1.x;
						double aux2 = ponto2.y - ponto1.y;
						double diagonal = Math.sqrt(Math.pow(aux, 2) + Math.pow(aux2, 2));
						int anguloSen = Integer.parseInt(JOptionPane.showInputDialog(null,"Valor inteiro do seno?", JOptionPane.INFORMATION_MESSAGE));
						int anguloCos = Integer.parseInt(JOptionPane.showInputDialog(null,"Valor inteiro do cosseno?", JOptionPane.INFORMATION_MESSAGE));
						double anguloEmRadianoSen = Math.toRadians(anguloSen);
						double anguloEmRadianoCos = Math.toRadians(anguloCos);
						double sen = Math.sin(anguloEmRadianoSen);
						double cos = Math.cos(anguloEmRadianoCos);
						
						int width = (int) Math.round(cos * diagonal);
						int height = (int) Math.round(sen * diagonal);
						
						Principal.getPrincipal().inserirFim(new Retangulo(ponto1, width, height));
						status.setText("Realeased in [" + event.getPoint().getX() + "," + event.getPoint().getY() + "]" + " - Tamanho Total = " +  Principal.getPrincipal().getTamanho());
						repaint();
					}
					
				    if(controleCirculo == true) {
						//Calcula o radiano
						ponto2 = new Ponto(event.getPoint().x, event.getPoint().y);
						double xTop = Math.min(ponto2.x, ponto1.x);
                        double yTop = Math.min(ponto2.y, ponto1.y);
                        double xBottom = Math.max(ponto2.x, ponto1.x);
                        double yBottom = Math.max(ponto2.y, ponto1.y);

                        double radius = Math.max(xBottom - xTop, yBottom - yTop);
                        xTop = ponto1.x - radius;
                        yTop = ponto1.y - radius;

                        radius *= 2;
                        
                        int r = (int) Math.round(radius);
                        
						Principal.getPrincipal().inserirFim(new Circulo(ponto1.x, ponto1.y, r));
						status.setText("Realeased in [" + event.getPoint().getX() + "," + event.getPoint().getY() + "]" + " - Tamanho Total = " +  Principal.getPrincipal().getTamanho());
						repaint();
					}
						
				}

				@Override
				public void mousePressed(MouseEvent event) {
					
					if(controleLinha == true) {
					ponto1 = new Ponto(event.getPoint().x, event.getPoint().y);
					status.setText("Pressed in [" + event.getPoint().getX() + "," + event.getPoint().getY() + "]" + " - Tamanho Total = " +  Principal.getPrincipal().getTamanho());			
					}
					
					if(controleRetangulo == true) {
						ponto1 = new Ponto(event.getPoint().x, event.getPoint().y);
						status.setText("Pressed in [" + event.getPoint().getX() + "," + event.getPoint().getY() + "]" + " - Tamanho Total = " +  Principal.getPrincipal().getTamanho());			
					}
					
					if(controleCirculo == true) {
						ponto1 = new Ponto(event.getPoint().x, event.getPoint().y);
						status.setText("Pressed in [" + event.getPoint().getX() + "," + event.getPoint().getY() + "]" + " - Tamanho Total = " +  Principal.getPrincipal().getTamanho());			
					}
					
				}

				@Override
				public void mouseExited(MouseEvent e) {
					// TODO Auto-generated method stub

				}

				@Override
				public void mouseEntered(MouseEvent e) {
					// TODO Auto-generated method stub

				}

				@Override
				public void mouseClicked(MouseEvent event) {
					
					mouseClickedCount++;
					
					if(controleTriangulo == true && mouseClickedCount == 1) {
						ponto1 = new Ponto(event.getPoint().x, event.getPoint().y);
						status.setText("Clicked One Time in [" + event.getPoint().getX() + "," + event.getPoint().getY() + "]" + " - Tamanho Total = " +  Principal.getPrincipal().getTamanho());
					} 
					
					if(controleTriangulo == true && mouseClickedCount == 2) {
						ponto2 = new Ponto(event.getPoint().x, event.getPoint().y);	
						status.setText("Clicked Two Times in [" + event.getPoint().getX() + "," + event.getPoint().getY() + "]" + " - Tamanho Total = " +  Principal.getPrincipal().getTamanho());
					} 
					
					if(controleTriangulo == true && mouseClickedCount == 3) {
						ponto3 = new Ponto(event.getPoint().x, event.getPoint().y);
						linha1 = new Linha(ponto1, ponto2);
						linha2 = new Linha(ponto1, ponto3);
						linha3 = new Linha(ponto2, ponto3);
						Principal.getPrincipal().inserirFim(new Triangulo(linha1, linha2, linha3));
						status.setText("Clicked Three Times in [" + event.getPoint().getX() + "," + event.getPoint().getY() + "]" + " - Tamanho Total = " +  Principal.getPrincipal().getTamanho());
						repaint();
						mouseClickedCount = 0;
					}

				}
			});
		super.setBackground(Color.pink);
	}


	@Override
	public void paintComponent(Graphics g) {

		Iterador<FormaGeometrica> it = Principal.getPrincipal().getIterador();
		FormaGeometrica forma;

		super.paintComponent(g); // limpa a �rea de desenho
	
		while((forma = it.proximo()) != null) {
			forma.desenhar(g);
		}
	}

}// fim da classe PaintPanel
