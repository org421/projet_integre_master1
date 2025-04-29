package serveur.serveurjeux.Entity.Utility;

public class Position{
	private float x, y;

	public Position(float x, float y) {
		this.x = x;
		this.y = y;
	}

	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}

	public String toString() {
		return "(" + x + "," + y + ")";
	}

	public boolean equals(Position p) {
		return this.x == p.getX() && this.y == p.getY();
	}

}