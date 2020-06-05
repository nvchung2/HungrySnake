import java.awt.Graphics;
import java.awt.Image;
import java.util.Random;

public class Apple {
	private Cell pos;
	private Image appleImg;
	private Random rd;
	public Cell getPos(){
		return pos;
	}
	public Apple(){
		rd=new Random(System.currentTimeMillis());
		pos=new Cell(rd.nextInt(Config.BOARD_WIDTH/Config.BOX_SIZE),rd.nextInt(Config.BOARD_HEIGHT/Config.BOX_SIZE));
		appleImg=ImageLoader.load("apple.png");
	}
	public void draw(Graphics g){
		g.drawImage(appleImg,pos.x,pos.y,null);
	}
	public void instantiate(Snake s){
		do{
			pos=new Cell(rd.nextInt(Config.BOARD_WIDTH/Config.BOX_SIZE),rd.nextInt(Config.BOARD_HEIGHT/Config.BOX_SIZE));
		} while(s.getSegments().contains(pos));
	}
}
