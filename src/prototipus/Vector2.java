package prototipus;
/**A koordináták tárolására való osztály.*/
public class Vector2{
	private int x;
	private int y;
	/**Az osztály paraméter nélküli konstruktora.*/
	public Vector2() {x=0;y=0;}

	/**Az osztály egyparaméteres konstruktora.
	 * @param a: mindkét koordináta értéke*/
	public Vector2(int a) {x=a;y=a;}
	/**Az osztály kétparaméteres konstruktora.
	 * @param x: az x koordináta értéke
	 * @param y: az y koordináta értéke*/
	public Vector2(int x,int y)
	{
		this.x = x; this.y = y;
	}

	/**Két vektor koordinátánkéni összeadása.
	 * @param other: a másik vektor
	 * @return Vector2*/
	public Vector2 plus(Vector2 other)
	{
		return new Vector2(x + other.getX(),y + other.getY());
	}
	/**Visszaadja x koordináta értékét
	 * @return int*/
	public int getX() {return x;}
	/**Beállítja x koordináta értékét
	 * @param x: beállítandó érték*/
	public void setX(int x) {this.x = x;}
	/**Beállítja y koordinát értékét.
	 * @param y: beállítandó érték*/
	public void setY(int y) {this.y = y;}
	/**Visszaadja y koordináta értékét.
	 * @return int*/
	public int getY() {return y;}
}
