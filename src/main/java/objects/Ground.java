package objects;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXMLLoader;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.io.IOException;

public class Ground extends Rectangle implements Block {
		public Ground() throws IOException {
				FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/lodedigger/ground-view.fxml"));
				loader.setRoot(this);
				loader.load();
		}

		public void breakBlock() {
				this.setDisable(true);
				this.setOpacity(0.0);
				Timeline breakTimeline = new Timeline(new KeyFrame(Duration.millis(1000), e -> {
						this.setDisable(false);
						this.setOpacity(1.0);
				}));
				breakTimeline.setCycleCount(0);
				breakTimeline.play();
		}
}
