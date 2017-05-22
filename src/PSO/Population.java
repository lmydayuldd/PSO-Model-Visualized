package PSO;

import java.util.ArrayList;


public class Population {

	private Thread [] particleThread;
	private ArrayList<Particle> particles = new ArrayList<Particle>();
	private double groupBestVal;
	private Position groupBestPosition;
	private Fitness fitnessFunction;
	private int numParticles;
	private Position searchSpaceSize;
	
	public Population (Position searchSpaceSize, int numParticles, Fitness fitnessFunction, Options options) {
		this.searchSpaceSize = searchSpaceSize;
		this.numParticles = numParticles;
		this.fitnessFunction = fitnessFunction;
		this.groupBestVal = 10000;
		for (int i = 0; i < this.numParticles; i++) {
			particles.add(new Particle(
				new Position( getRandomLocation() ),
				this.getRandomVelocity(),
				fitnessFunction,
				options,
				this
			));
		}
		
		//---MultiThread Implementation---//
		particleThread = new Thread[this.numParticles];
		for (int index = 0; index < this.numParticles; index++){
			particleThread[index] = new Thread(particles.get(index));
		}
			
	}
	
	
	public ArrayList<Particle> getParticles () {
		return this.particles;
	}
	
	public Thread []getParticleThread(){
		return particleThread;
	}
	
	public void resetGoal (int[] goal) {
		this.fitnessFunction.setGoal(goal);
		this.groupBestVal = 10000;
		this.particles.forEach(particle -> particle.reset());
	}
	
	
	private int[] getRandomLocation () {
		int[] randomLocation = new int[2]; 
		for (int index = 0; index < 2; index++) {
			randomLocation[index] = (int) Math.floor(this.searchSpaceSize.getPositionByIndex(index) * Math.random());
		}
		return randomLocation;
	}
	
	private Velocity getRandomVelocity () {
		double[] velocity = new double[2];
		int Multiplier = 25;
		for (int index = 0; index < 2; index++) {
			velocity[index] = (Math.random() - 0.5) * Multiplier * Math.random();
		}
		return new Velocity(velocity);
	}
	
	public synchronized Position getGlobalBestPosition () {
		return this.groupBestPosition;
	}
	
	public synchronized void setGlobalBestPosition (Position gBest) {
		this.groupBestPosition = gBest;
	}

	public synchronized double getGroupBestVal() {
		return groupBestVal;
	}

	public synchronized void setGroupBestVal(double groupBestVal) {
		this.groupBestVal = groupBestVal;
	}
	
	
	
}
