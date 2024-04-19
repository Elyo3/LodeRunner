package org.example.lodedigger;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import vinnsla.Points;

public class MainController {
		@FXML
		private Area fxArea;

		@FXML
		private Button button;

		public static int currentMap=0;

		@FXML
		private MenuController menuController;

		public Area getArea() {
			return fxArea;
		}

		public void initialize() {
				menuController.setMainController(this);
				fxArea.linkController(this);
				if (++currentMap > 1) {
					fxArea.nextMap();
					button.setOnKeyPressed(e -> {
						fxArea.keyHandler(e);
					});
					button.setOnKeyReleased(e -> {
						fxArea.stopHandler(e);
					});
				}
		}

		public void startGame() {
			ViewSwitcher.setScene(button.getScene());
			button.setOnKeyPressed(e -> {
				fxArea.keyHandler(e);
			});
			button.setOnKeyReleased(e -> {
				fxArea.stopHandler(e);
			});
			Points.resetAll();
			fxArea.clear();
			fxArea.makeMap(menuController.getMap());
			fxArea.makeTimeline();
			fxArea.linkPlayer();
			fxArea.linkEnemies();
			fxArea.linkController(this);
			fxArea.resetLabels();
		}

		public void gameOver() {
			button.setOnKeyPressed(e -> {});
			button.setOnKeyReleased(e -> {});
		}
}
