package PSO;

import java.util.Arrays;import java.util.concurrent.atomic.AtomicInteger;


public class Particle implements Runnable{

	private Population population;
	private Position loaction;
	private Position lastLocation1;
	private Position lastLocation2;
	private Position personalBestLocation;
	private Velocity velocity;
	private double personalBestVal;
	private double groupBestVal;
	private Fitness fitnessFunction;
	private Options options;
	
	public Particle (Position position, Velocity velocity, Fitness fitnessFunction, Options options, Population population) {
		this.loaction = position.clone();
		this.velocity = velocity.clone();
		this.fitnessFunction = fitnessFunction;
		this.options = options;
		this.personalBestLocation = position.clone();
		this.lastLocation1 = position.clone();
		this.lastLocation2 = position.clone();
		this.population = population;
		this.personalBestVal = 10000;

	}
	
	public Particle (Position position, Velocity velocity) {
		this.loaction = position;
		this.velocity = velocity;
	}
	
	public float evaluateFitness () {
		float fitness = fitnessFunction.calcFitness(this);
		if (fitness < this.personalBestVal) {
			this.personalBestVal = fitness;
			this.personalBestLocation = this.loaction.clone();
		}
		return fitness;
	}
	
	public void updateSpeed (Position groupBest) {
		if (groupBest == null) {
			System.out.println("gBest is null, exiting now");
			System.exit(0);
		}
//		System.out.println(fitnessFunction.calcFitness(this));
	
		// iterator
		AtomicInteger indexCnt = new AtomicInteger(0);
		double[] updatedVelocity = new double[2];
		for(double velocityElement : velocity.getVelocity() ){
			int index = indexCnt.getAndIncrement();
			double personalBestElement = options.c1 * 1.0 * (personalBestLocation.getPositionByIndex(index) - loaction.getPositionByIndex(index)) ;
			double globalBestElement = options.c2 * 1.0 * (groupBest.getPositionByIndex(index) - loaction.getPositionByIndex(index)) ;
			updatedVelocity[index] = (velocityElement + personalBestElement + globalBestElement) 
					/ ((options.speedLimit * 2) /fitnessFunction.calcFitness(this)) ;
		}
			
		
//		System.out.println(updatedVelocity[0] +"/" +updatedVelocity[1]);
		this.velocity.setVelocity(updatedVelocity);
		
		this.validateSpeed();
		this.updateLocation();
	}
	
	private void validateSpeed () {
		
		double velocitySquared = Arrays.stream( velocity.getVelocity() )
				.map(velocityElement -> Math.pow(velocityElement, 2))
				.sum();
		double scalarVelocity = Math.sqrt(velocitySquared);
		
		if (scalarVelocity > options.speedLimit) {
			this.applySpeedLimit(scalarVelocity);
		}
	}
	
	private void applySpeedLimit (double scalarVelocity) {

		double desiredSpeedRatio = this.options.speedLimit / scalarVelocity;
		double[] appliedSpeedLimit = Arrays.stream( this.velocity.getVelocity() )
				.map(velocityElement -> desiredSpeedRatio * velocityElement)
				.toArray();
		this.velocity.setVelocity(appliedSpeedLimit);
	}
	
	private void updateLocation () {
		this.lastLocation2 = this.lastLocation1.clone();
		this.lastLocation1 = this.loaction.clone();
		
		int[] newPos = new int[2];
		for (int index = 0; index < 2; index++) {
			newPos[index] = (int) Math.round(this.loaction.getPositionByIndex(index) + this.velocity.getVelocityByIndex(index));
		}
		this.loaction.setPosition(newPos);
	}
	
	public double getPersonalBestVal () {
		return this.personalBestVal;
	}
	
	public Position getLocalBestPosition () {
		return this.personalBestLocation;
	}

	public Position getLocation () {
		return this.loaction;
	}
	
	public Position getLastLocation1 () {
		return this.lastLocation1;
	}
	
	public Position getLastLocation2 () {
		return this.lastLocation2;
	}
	
	public void reset () {
		this.personalBestVal = 10000;
	}
	
	
	public void setLocation (Position position) {
		this.loaction = position;
		this.lastLocation1 = position.clone();
		this.lastLocation2 = position.clone();
	}
	
	public void setVelocity (Velocity velocity) {
		this.velocity = velocity;
	}
	
	public Velocity getVelocity () {
		return this.velocity;
	}
	
	

	@Override
	public void run() {

		while(true){
			try {
				Thread.sleep(15);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			this.evaluateFitness();
			groupBestVal = population.getGroupBestVal();
			if (this.personalBestVal < groupBestVal) {
				population.setGroupBestVal(this.getPersonalBestVal());
				population.setGlobalBestPosition(this.getLocalBestPosition().clone());
			}
		
			this.updateSpeed(population.getGlobalBestPosition());
			}
	}
	
}
