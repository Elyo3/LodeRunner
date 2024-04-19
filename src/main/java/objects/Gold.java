package objects;

import javafx.fxml.FXMLLoader;
import javafx.scene.image.ImageView;

import java.io.IOException;

public class Gold extends ImageView {
    public Gold() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/lodedigger/gold-view.fxml"));
        loader.setRoot(this);
        loader.load();
    }
}
