package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.Timer;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import PSO.Options;

@SuppressWarnings("serial")
public class UI extends JFrame implements ActionListener {

	Thread runThread = null;

	// Timer related
	private Timer timer;
	private int refreshTime = 15; // refresh timer

	private JFrame Frame = null;
	private JPanel centralPanel = new JPanel();
	private JPanel sliderPanel = new JPanel();

	private JSlider c1 = new JSlider(0, 100);
	private JLabel _c1 = new JLabel("c1");
	private JSlider c2 = new JSlider(0, 100);
	private JLabel _c2 = new JLabel("c2");
	private JSlider speedLimit = new JSlider(1, 20);
	private JLabel _speedLimit = new JLabel("speedLimit");
	private JSlider populationSize = new JSlider(10, 1000);
	private JLabel _populationSize = new JLabel("Population Number");

	JButton startButton = new JButton("start");
	JButton stopButton = new JButton("stop");

	private int width = 1100;
	private int height = 800;

	private Options options;
	private Canvas canvas;

	public UI(final Options options) {
		this.options = options;

		// ---CREATE SLIDER PANEL---//
		sliderPanel.setLayout(new GridLayout(3, 2));
		sliderPanel.add(c1);
		sliderPanel.add(_c1);
		sliderPanel.add(c2);
		sliderPanel.add(_c2);
		sliderPanel.add(speedLimit);
		sliderPanel.add(_speedLimit);
		sliderPanel.add(populationSize);
		sliderPanel.add(_populationSize);

		sliderPanel.add(startButton);
		sliderPanel.add(stopButton);

		sliderPanel.setBackground(Color.GRAY);

		// ---SLIDER LISTENERS---//
		SliderListener listener = new SliderListener();
		c1.addChangeListener(listener);
		c2.addChangeListener(listener);
		speedLimit.addChangeListener(listener);
		populationSize.addChangeListener(listener);

		// ---BUTTON LISTENER---//
		
		startButton.addActionListener(this);
		stopButton.addActionListener(this);

		// ---SET INITIAL VALUES---//
		this.options.c1 = 0.006f;
		this.options.c2 = 0.001f;
		this.options.speedLimit = 5;
		this.options.populationSize = 500;

		c1.setValue(6);
		c2.setValue(1);
		speedLimit.setValue(5);
		populationSize.setValue(500);

		// ---CREATE PANEL---//
		Border thickBorder = new LineBorder(Color.black, 1);
		centralPanel.setLayout(new BoxLayout(centralPanel, BoxLayout.Y_AXIS));
		centralPanel.setBorder(thickBorder);
		centralPanel.add(sliderPanel);

		// ---ADD CANVAS---//
		canvas = new Canvas(width, height, this.options.populationSize, this.options);

		Frame = new JFrame();

		Frame.setAlwaysOnTop(isAlwaysOnTop());
		Frame.getContentPane().setLayout(new BorderLayout());
		Frame.add(centralPanel, BorderLayout.NORTH);
		Frame.add(canvas, BorderLayout.CENTER);
		Frame.setTitle("Final_PSO");
		Frame.setBackground(Color.WHITE);
		Frame.setSize(width, height);
		Frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Frame.setVisible(true);
		Frame.setResizable(true);
		Frame.setLocationRelativeTo(null);
		Frame.setLocation(100, 10);

	}

	private class SliderListener implements ChangeListener {
		public void stateChanged(ChangeEvent ce) {
			if (ce.getSource() == c1) {
				float val = (float) (c1.getValue() / 1000.0);
				_c1.setText("c1: " + val);
				options.c1 = val;
			} else if (ce.getSource() == c2) {
				float val = (float) (c2.getValue() / 1000.0);
				_c2.setText("c2: " + val);
				options.c2 = val;
			} else if (ce.getSource() == speedLimit) {
				_speedLimit.setText("speedLimit: " + speedLimit.getValue());
				options.speedLimit = speedLimit.getValue();
			}

			else if (ce.getSource() == populationSize) {
				_populationSize.setText("Population Number: " + populationSize.getValue());
				options.populationSize = populationSize.getValue();
			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		Runnable runnable = new Runnable() {
			public void run() {
				timer = new Timer(refreshTime, new ActionListener() {
					public void actionPerformed(ActionEvent evt) {
						canvas.repaint();
					}
				});

				timer.start();
			}
		};

		if (e.getActionCommand().equals("start")) {

			runThread = new Thread(runnable);
			runThread.start();
			for(Thread thread : options.population.getParticleThread()){
				thread.start();
			}
			
		}

		if (e.getActionCommand().equals("stop")) {

			timer.stop();
			runThread.interrupt();
			for(Thread thread : options.population.getParticleThread()){
				thread.interrupt();
			}
		}

	}

}