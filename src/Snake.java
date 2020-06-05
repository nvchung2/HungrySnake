import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;
import java.util.List;

public class Snake {
	private List<Cell> segs;
	private boolean eaten=false;
	private Image[] headSprites,bodySprites,tailSprites,joinSprites;
	private Directions dir;

	public Directions getDir() {
		return dir;
	}

	public void setDir(Directions dir) {
		this.dir = dir;
	}
	public List<Cell> getSegments(){
		return segs;
	}
	public Snake() {
		headSprites = new Image[4];
		bodySprites=new Image[2];
		tailSprites=new Image[4];
		joinSprites=new Image[4];
		segs= new ArrayList<>();
		segs.add(new Cell(10,10));
		for (int i = 0; i < 3; i++) {
			segs.add(new Cell(10-i-1,10));
		}
		dir = Directions.RIGHT;
		loadSprites();
	}

	private void loadSprites() {
		for (int i = 0; i < 4; i++) {
			headSprites[i] = ImageLoader.load("head (" + (i + 1) + ").png");
		}
		for (int i = 0; i < 4; i++) {
			tailSprites[i] = ImageLoader.load("tail (" + (i + 1) + ").png");
		}
		for (int i = 0; i < 4; i++) {
			joinSprites[i] = ImageLoader.load("join (" + (i + 1) + ").png");
		}
		for (int i = 0; i < 2; i++) {
			bodySprites[i]=ImageLoader.load("body ("+(i+1)+").png");
		}
	}
	public boolean eat(Apple a){
		if(segs.get(0).equals(a.getPos())){
			eaten=true;
			return true;
		}
		return false;
	}
	public boolean die(){
		Cell head=segs.get(0);
		if(head.x<0||head.x==Config.BOARD_WIDTH||head.y<0||head.y==Config.BOARD_HEIGHT){
			return true;
		}
		for (int i = 1; i < segs.size(); i++) {
			if(head.equals(segs.get(i))){
				return true;
			}
		}
		return false;
	}
	public void draw(Graphics g) {
		int n=segs.size();
		Cell seg=segs.get(0);
		Image sprite=headSprites[dir.ordinal()];
		g.drawImage(sprite,seg.x,seg.y,null);
		for (int i = 1; i < n-1; i++) {
			seg=segs.get(i);
			if(seg.x==segs.get(i-1).x){
				sprite=bodySprites[0];
			} else {
				sprite = bodySprites[1];
			}
			Cell nseg=segs.get(i+1);
			Cell pseg=segs.get(i-1);
			if(seg.x<nseg.x&&seg.y<pseg.y||seg.x<pseg.x&&seg.y<nseg.y){
				sprite=joinSprites[0];
			} else if(seg.x>pseg.x&&seg.y<nseg.y||seg.x>nseg.x&&seg.y<pseg.y){
				sprite=joinSprites[1];
			} else if(seg.x<pseg.x&&seg.y>nseg.y||seg.x<nseg.x&&seg.y>pseg.y){
				sprite=joinSprites[2];
			} else if(seg.x>nseg.x&&seg.y>pseg.y||seg.x>pseg.x&&seg.y>nseg.y){
				sprite=joinSprites[3];
			}
			g.drawImage(sprite,seg.x,seg.y,null);
		}
		seg=segs.get(n-1);
		if(seg.y>segs.get(n-2).y){
			sprite=tailSprites[0];
		} else if (seg.x<segs.get(n-2).x){
			sprite=tailSprites[1];
		} else if (seg.x>segs.get(n-2).x){
			sprite=tailSprites[2];
		} else{
			sprite=tailSprites[3];
		}
		g.drawImage(sprite,seg.x,seg.y,null);
	}

	public void move() {
		Cell seg=segs.get(0);
		int x=seg.x;
		int y=seg.y;
		switch (dir) {
		case UP:
			seg.translate(Directions.UP);
			break;
		case DOWN:
			seg.translate(Directions.DOWN);
			break;
		case LEFT:
			seg.translate(Directions.LEFT);
			break;
		case RIGHT:
			seg.translate(Directions.RIGHT);
			break;
		}
		for (int i = 1; i < segs.size(); i++) {
			seg=segs.get(i);
			int tmpX=seg.x;
			int tmpY=seg.y;
			seg.x=x;
			seg.y=y;
			x=tmpX;
			y=tmpY;
		}
		if(eaten){
			segs.add(new Cell(x/Config.BOX_SIZE,y/Config.BOX_SIZE));
			eaten=false;
		}
	}
}
//end
























//end