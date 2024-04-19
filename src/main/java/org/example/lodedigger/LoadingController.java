package org.example.lodedigger;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.util.Duration;
import vinnsla.Points;

public class LoadingController {
    @FXML
    private MenuController menuController;
    private MainController mainController;

    @FXML
    private Label pointsLabel;

    public void initialize() {
        pointsLabel.setText("Points: " + Points.getPoints());
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(5000), e -> {
            ViewSwitcher.switchTo(View.PLAY);
        }));
        timeline.setCycleCount(0);
        timeline.play();
    }
}
