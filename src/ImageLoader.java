import java.awt.Image;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

public class ImageLoader {
	public static Image load(String name){
		Image img=null;
		try {
			img=ImageIO.read(ImageLoader.class.getResource(name));
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null,"Can't load resources!","Error",JOptionPane.ERROR_MESSAGE);
			System.exit(0);
		}
		return img;
	}
}
