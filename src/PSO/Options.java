package PSO;

public class Options {

	public float c1 = 0.1f;
	public float c2 = 0.1f;
	public float speedLimit = 20;
	public int convergenceTrigger;
	public Population population;
	public int populationSize = 500;
}

//v[] = v[] + c1 * rand() * (pbest[] - present[]) + c2 * rand() * (gbest[] - present[])
