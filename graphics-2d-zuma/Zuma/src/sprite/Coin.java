package sprite;
import javafx.scene.shape.Circle;
import it.unical.mat.embasp.languages.Id;
import it.unical.mat.embasp.languages.Param;

@Id("coin")
public class Coin extends Circle {
	
	@Param(0)
	int x;
	
	@Param(1)
	int y;

	public Coin(int x, int y) {
		super();
		this.x = x;
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
	
	

}
