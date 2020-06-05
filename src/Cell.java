
public class Cell {
	int x,y;
	public Cell(int r,int c){
		this.x=r*Config.BOX_SIZE;
		this.y=c*Config.BOX_SIZE;
	}
	public void translate(Directions dir){
		switch (dir) {
		case UP:
			y-=Config.BOX_SIZE;
			break;
		case DOWN:
			y+=Config.BOX_SIZE;
			break;
		case LEFT:
			x-=Config.BOX_SIZE;
			break;
		case RIGHT:
			x+=Config.BOX_SIZE;
			break;
		}
	}
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof Cell){
			Cell c=(Cell) obj;
			return x==c.x&&y==c.y;
		}
		return super.equals(obj);
	}
}
