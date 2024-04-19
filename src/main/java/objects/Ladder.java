package objects;

import javafx.fxml.FXMLLoader;
import javafx.scene.image.ImageView;

import java.io.IOException;

public class Ladder extends ImageView implements Climbable {
		public Ladder() throws IOException {
				FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/lodedigger/ladder-view.fxml"));
				loader.setRoot(this);
				loader.load();
		}
}
