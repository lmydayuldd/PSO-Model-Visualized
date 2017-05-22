package PSO;

public class Velocity implements Cloneable {

	private double[] velocity;

	public Velocity(double[] velocity) {

		this.velocity = velocity.clone();
	}

	public double[] getVelocity() {
		return this.velocity;
	}

	public void setVelocity(double[] velocity) {
		this.velocity = velocity.clone();
	}

	public double getVelocityByIndex(int index) {

		return this.velocity[index];
	}

	public void setVelocityByIndex(int index, double value) {

		this.velocity[index] = value;
	}

	@Override
	public Velocity clone() {
		return new Velocity(this.velocity);
	}

	@Override
	public String toString() {
		return "Velocity [velocity=" + this.velocity[0] + "/" + this.velocity[1] + "]";
	}

}
