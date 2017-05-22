package PSO;

public class Position implements Cloneable {

	private int[] position;

	public Position(int[] pos) {

		this.position = pos.clone();
	}

	public int[] getPosition() {
		return this.position;
	}

	public void setPosition(int[] position) {
		this.position = position.clone();
	}

	public double getPositionByIndex(int index) {

		return this.position[index];
	}

	public void setPositionByIndex(int index, int value) {

		this.position[index] = value;
	}

	@Override
	public Position clone() {
		return new Position(this.position);
	}
}
