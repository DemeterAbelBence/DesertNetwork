package prototipus;

import java.awt.Graphics;
import java.awt.Image;

public class DrawablePlayer extends Drawable{
	Player player;


	public DrawablePlayer(Player player, Vector2 coordinates, Image image){
		super(coordinates, image);
		this.player = player;
	}
	
	@Override
	public void Draw(Graphics g) {

		//TODO ha van idő: Egy komponensen több player legyen látható
		if(player.host.getNode()){
			Component host = player.getHost();
			Drawable hostDrawable = Observer.getDrawableOfComponent(host);
			Vector2 hostPosition = hostDrawable.getCoordinates();

			int nrPlayers = host.getPlayers().size();
			
			//avoiding player image collision
			if(nrPlayers == 1) {
				Vector2 newPosition = hostPosition.plus(new Vector2(spriteOffset));
				Move(newPosition);
			}else {
				if(host.getPlayers().get(1) == player)
					Move(hostPosition);
			}
		}
		else{
			Drawable drawableOfNeighbour1 = Observer.getDrawableOfComponent(player.getHost().getNeighbour(0));
			Drawable drawableOfNeighbour2 = Observer.getDrawableOfComponent(player.getHost().getNeighbour(1));
			Vector2 middle = new Vector2((drawableOfNeighbour1.getX() + drawableOfNeighbour2.getX() ) / 2,(drawableOfNeighbour1.getY() + drawableOfNeighbour2.getY() ) / 2);
			Move(middle);
		}

		g.drawImage(image,getX(),getY(), null);
	}
}
