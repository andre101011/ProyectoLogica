package vista;

import java.awt.*;
import java.awt.geom.*;
import javax.swing.*;

import modelo.Nodo;

import java.util.*;
/*
 * Un componente grafico para graficar el contenido de un arbol binario
 */

public class TreeDisplay extends JComponent {

	// Numero de pixeles entre el texto y el borde
	private static final int ARC_PAD = 10;

	// el arbol siendo graficado
	private Nodo root = null;

	// Crea un frame con un nuevo componente treeDisplay
	// (constructor returns the TreeDisplay component--not the frame).
	public TreeDisplay(String title) {
		// crea el frame que lo contiene
		JFrame frame = new JFrame(title);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		// Agrega el componente TreeDisplay al frame
		frame.getContentPane().add(this);

		// Muestra el frame
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

	// Le dice al frame el tamaño por defecto del arbol
	public Dimension getPreferredSize() {
		return new Dimension(400, 300);
	}

	// Llamado cada vez que el treeDisplay se dibuja en la pantalla
	public void paint(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		Dimension d = getSize();

		// Dibuja el fondo blanco
		g2.setPaint(Color.white);
		g2.fill(new Rectangle2D.Double(0, 0, d.width, d.height));

		int depth = h();

		if (root == null)
			// No hay ningun arbol que dibujar
			return;

		// Truco para evadir la division por cero, si solo hay un nivel en el arbol
		if (depth == 1)
			depth = 2;

		// Computa el tamaño del texto
		FontMetrics fuente = g2.getFontMetrics();
		Nodo leftmost = root;
		while (leftmost.getIzquierdo() != null)
			leftmost = leftmost.getIzquierdo();
		Nodo rightmost = root;
		while (rightmost.getDerecho() != null)
			rightmost = rightmost.getDerecho();
		int leftPad = fuente.stringWidth(leftmost.getData() + "") / 2;
		int rightPad = fuente.stringWidth(rightmost.getData() + "") / 2;
		int altoTexto = fuente.getHeight();

		// Dibuja el arbol
		drawTree(g2, root, leftPad + ARC_PAD, d.width - rightPad - ARC_PAD, altoTexto / 2 + ARC_PAD,
				(d.height - altoTexto - 2 * ARC_PAD) / (depth - 1));

	}
	/*
	 * Dibuja el arbol, empezando desde el nodo dado, en la region con valores x que
	 * van desde minX hasta maxX, con el valor de y empezando en y, y el siguiente
	 * nivel en y + yIncr.
	 */

	private void drawTree(Graphics2D g2, Nodo nodo, int minX, int maxX, int y, int yIncr) {
		// Lo ignora si está vacio
		if (nodo == null)
			return;

		// Computa coordenadas utiles
		int x = (minX + maxX) / 2;
		int nextY = y + yIncr;

		// Dibuja las lineas negras
		g2.setPaint(Color.black);

		if (nodo.getIzquierdo() != null) {
			int nextX = (minX + x) / 2;
			g2.draw(new Line2D.Double(x, y, nextX, nextY));

		}
		if (nodo.getDerecho() != null)

		{
			int nextX = (x + maxX) / 2;
			g2.draw(new Line2D.Double(x, y, nextX, nextY));
		}

		// mide el tamaño del texto
		FontMetrics fuente = g2.getFontMetrics();
		String text = nodo.getData() + "";
		int alturaTexto = fuente.getHeight();
		int anchoTexto = fuente.stringWidth(text);

		// dibuja el cuadro al rededor del nodo
		Rectangle2D.Double caja = new Rectangle2D.Double(x - anchoTexto / 2 - ARC_PAD, y - alturaTexto / 2 - ARC_PAD,
				anchoTexto + 2 * ARC_PAD, alturaTexto + 2 * ARC_PAD);
		Color c = new Color(187, 224, 227);
		g2.setPaint(c);
		g2.fill(caja);
		// Dibuja el borde negro
		g2.setPaint(Color.black);
		g2.draw(caja);

		// Dibuja el texto
		g2.drawString(text, x - anchoTexto / 2, y + alturaTexto / 2);

		// Dibuja los hijos
		drawTree(g2, nodo.getIzquierdo(), minX, x, nextY, yIncr);
		drawTree(g2, nodo.getDerecho(), x, maxX, nextY, yIncr);
	}

	// Le dice al componente que pase a graficar el arbol que le mandemos
	public void setRoot(Nodo root) {
		this.root = root;

		// Señal de que la grafica necesita ser re-dibujada
		repaint();
	}

	private int h() {
		Stack<Nodo> nodeStack = new Stack<Nodo>();
		Stack<Integer> leftStack = new Stack<Integer>();
		Stack<Integer> rightStack = new Stack<Integer>();

		nodeStack.push(root);
		leftStack.push(-1);
		rightStack.push(-1);

		while (true) {
			Nodo t = nodeStack.peek();
			int left = leftStack.peek();
			int right = rightStack.peek();

			if (t == null) {
				nodeStack.pop();
				leftStack.pop();
				rightStack.pop();
				int value = 0;
				if (nodeStack.isEmpty())
					return value;
				else if (leftStack.peek() == -1) {
					leftStack.pop();
					leftStack.push(value);
				} else {
					rightStack.pop();
					rightStack.push(value);
				}
			} else if (left == -1) {
				nodeStack.push(t.getIzquierdo());
				leftStack.push(-1);
				rightStack.push(-1);
			} else if (right == -1) {
				nodeStack.push(t.getDerecho());
				leftStack.push(-1);
				rightStack.push(-1);
			} else {
				nodeStack.pop();
				leftStack.pop();
				rightStack.pop();
				int value = 1 + Math.max(left, right);
				if (nodeStack.isEmpty())
					return value;
				else if (leftStack.peek() == -1) {
					leftStack.pop();
					leftStack.push(value);
				} else {
					rightStack.pop();
					rightStack.push(value);
				}
			}
		}
	}
}