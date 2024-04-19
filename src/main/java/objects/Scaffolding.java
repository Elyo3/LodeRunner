package objects;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

import java.io.IOException;

public class Scaffolding extends ImageView implements Block {

		public Scaffolding() throws IOException {
				FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/lodedigger/scaffolding-view.fxml"));
				loader.setRoot(this);
				loader.load();
		}

		public void breakBlock() {
				this.setDisable(true);
				this.setOpacity(0.0);
				Timeline breakTimeline = new Timeline(new KeyFrame(Duration.millis(4000), e -> {
						this.setDisable(false);
						this.setOpacity(1.0);
				}));
				breakTimeline.setCycleCount(0);
				breakTimeline.play();
		}
}
