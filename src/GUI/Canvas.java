package GUI;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;
import javax.swing.Timer;

import PSO.Fitness;
import PSO.Options;
import PSO.Particle;
import PSO.Population;
import PSO.Position;

public class Canvas extends JPanel implements MouseListener {

	/**
	 * 
	 */
	// Fundamental stuff
	private static final long serialVersionUID = 1L;
	private Population population;
	private Fitness fitnessFunction = new Fitness(new int[] { 400, 400 }); // original
																			// goal
	private Options options;


	// Paint related
	private int iterationCnt = 0;
	private int goalRadius = 6; // paint the goal on the screen

	// Color related
	private int RED = 0;
	private int GREEN = 0;
	private int BLUE = 0;
	private int colorCnt = 0;
	private int colorTrigger = 0;
	private boolean colorReverse = false;

	public Canvas(int width, int height, int populationSize, Options options) {
		this.addMouseListener(this);
		this.setBackground(new Color(255, 255, 255));
		this.options = options;
		Position size = new Position(new int[] { width, height });
		this.population = new Population(size, populationSize, fitnessFunction, options);
		this.options.population = population;

	}

	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
//		population.updateParticles();
		colorTrigger++;
		if (colorCnt == 200) {
			colorReverse = true;
		}
		if (colorCnt == 50) {
			colorReverse = false;
		}
		if (!colorReverse) {
			colorCnt++;
		} else {
			colorCnt--;
		}

		Graphics2D g2d = (Graphics2D) g;
		// render goal

		if (colorTrigger == 3) {
			RED = (colorCnt);
			colorTrigger = 0;
		}

		g2d.setColor(new Color(255, 50, 0));
		g2d.fillOval(this.fitnessFunction.getGoal()[0] - this.goalRadius,
				this.fitnessFunction.getGoal()[1] - this.goalRadius, this.goalRadius * 2, this.goalRadius * 2);
		// render particles

		// RED = (int)(Math.random() * 255) % 150;
		// GREEN = (int)(Math.random() * 255) % 255;
		// BLUE = (int)(Math.random() * 255) % 255;

		g2d.setColor(new Color(RED, 255 - RED, RED / 2));
		g2d.setStroke(new BasicStroke(2));
		for (Particle particle : population.getParticles()) {
			g.drawLine(particle.getLocation().getPosition()[0], particle.getLocation().getPosition()[1],
					particle.getLastLocation1().getPosition()[0], particle.getLastLocation1().getPosition()[1]);

		}
	}

	// -------------MOUSE LISTENERS-------------//
	@Override
	public void mouseClicked(MouseEvent arg0) {
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		this.population.resetGoal(new int[] { arg0.getX(), arg0.getY() });
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
	}
	// -------------------------------------------//

}
