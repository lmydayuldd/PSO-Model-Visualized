package PSO;

public class Fitness {

	private int[] goal;

	public Fitness(int[] goal) {

		this.goal = goal.clone();
	}

	public float calcFitness(Particle p) {
		float sum = 0f;
		for (int i = 0; i < 2; i++) {
			sum += (float) (Math.pow(p.getLocation().getPosition()[i] - this.goal[i], 2));
		}
		return (float) Math.sqrt(sum);
	}

	public int[] getGoal() {
		return this.goal;
	}

	public void setGoal(int[] goal) {
		this.goal = goal.clone();
	}

}
