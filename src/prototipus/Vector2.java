package prototipus;

public class Vector2{
	private int x;
	private int y;
	public Vector2() {x=0;y=0;}
	public Vector2(int x,int y)
	{
		this.x = x; this.y = y;
	}
	public int getX() {return x;}
	public void setX(int x) {this.x = x;}
	public void setY(int y) {this.y = y;}
	public int getY() {return y;}
}
