package org.example.lodedigger;

import entities.Enemy;
import entities.Player;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;
import maps.Maps;
import objects.Gold;
import objects.Scaffolding;
import vinnsla.Points;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Objects;

public class Area extends StackPane {
		private MainController mainController;

		@FXML
		private Player fxPlayer;

		@FXML
		private GridPane gameMap;

		@FXML
		private Pane entityField;

		@FXML
		private Label gameOverLabel;
		@FXML
		private Label victoryLabel;

		private LinkedList<Direction> direction = new LinkedList<>();
		private ArrayList<Enemy> enemies = new ArrayList<>();

		private Facing facing = Facing.LEFT;

		private Timeline playerTimeline;

		private Timeline enemyTimeline;

		public enum Direction {
				STOP, UP, DOWN, RIGHT, LEFT;
		}

		public enum Facing {
				LEFT, RIGHT;
		}

		public Area() throws IOException {
				FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/lodedigger/area-view.fxml"));
				loader.setRoot(this);
				loader.setController(this);
				loader.load();
		}

		public void linkController(MainController mainController) {
			this.mainController = mainController;
		}

		private void makeEscapeLadder() {
			addElements(Maps.getLadder(MainController.currentMap));
		}

		public void makeMap(int i) {
			direction.clear();
			enemies.clear();
			MainController.currentMap = i;
			addElements(Maps.getMap(MainController.currentMap));
			int[] playerPos = new int[]{6, 10};
			fxPlayer.setLayoutX(playerPos[0]*50);
			fxPlayer.setLayoutY(playerPos[1]*50);
		}

		public void addElements(Node[][] map) {
			for (int x = 0; x < Objects.requireNonNull(map).length; x++) {
				for (int y = 0; y < map[x].length; y++) {
					if (map[x][y] != null) {
						if (map[x][y].getClass() == Enemy.class) {
							try {
								Enemy enemy = new Enemy();
								enemy.setLayoutX(50 * x);
								enemy.setLayoutY(50 * y);
								entityField.getChildren().add(enemy);
								enemies.add(enemy);
							} catch (IOException ignored) {}
						}
						else gameMap.add(map[x][y], x, y);
					}
				}
			}
		}

		public void nextMap() {
			if (MainController.currentMap > 5) {
				clear();
				removeEntities();
				victoryLabel.setOpacity(1.0);
			} else {
				Points.resetPoints();
				clear();
				makeMap(MainController.currentMap);
				makeTimeline();
				linkPlayer();
				linkEnemies();
			}
		}

		private void newEnemy(int x, int y) {
			try {
				Enemy enemy = new Enemy();
				enemy.setLayoutX(x * 50);
				enemy.setLayoutY(y * 50);
				enemy.setMapAndPlayer(this, fxPlayer);
				entityField.getChildren().add(enemy);
				enemies.add(enemy);
			} catch (IOException ignored) {}
		}

		public void gameOver() {
			mainController.gameOver();
			gameOverLabel.setOpacity(1.0);
			playerTimeline.stop();
			enemyTimeline.stop();
			Points.resetAll();
			stopPathfinding();
		}

		private void stopPathfinding() {
			for (Node e : entityField.getChildren()) {
				if (e.getClass() == Enemy.class) {
					((Enemy) e).getPathfinding().stop();
				}
			}
		}

		public void resetLabels() {
			gameOverLabel.setOpacity(0.0);
			victoryLabel.setOpacity(0.0);
		}

		public void keyHandler(KeyEvent e) {
				switch (e.getCode()) {
						case W:
								if (direction.contains(Direction.UP)) break;
								direction.addFirst(Direction.UP);
								break;
						case S:
								if (direction.contains(Direction.DOWN)) break;
								direction.addFirst(Direction.DOWN);
								break;
						case A:
								if (direction.contains(Direction.LEFT)) break;
								direction.addFirst(Direction.LEFT);
								break;
						case D:
								if (direction.contains(Direction.RIGHT)) break;
								direction.addFirst(Direction.RIGHT);
								break;
						case SPACE:
								handleDig();
								break;
						default: break;
				}
		}

		public void stopHandler(KeyEvent e) {
			switch (e.getCode()) {
				case W:
					direction.remove(Direction.UP);
					break;
				case S:
					direction.remove(Direction.DOWN);
					break;
				case A:
					direction.remove(Direction.LEFT);
					break;
				case D:
					direction.remove(Direction.RIGHT);
					break;
				default: break;
			}
		}

		private void movePlayer() {
			Direction dir;
			if (direction.isEmpty()) dir = Direction.STOP;
			else dir = direction.getFirst();
			if (dir == Direction.LEFT) facing = Facing.LEFT;
			if (dir == Direction.RIGHT) facing = Facing.RIGHT;
			fxPlayer.move(dir);
		}

		private void handleDig() {
				if (facing == Facing.LEFT) digTile(fxPlayer.dig(-25));
				else if (facing == Facing.RIGHT) digTile(fxPlayer.dig(75));
		}

		public void digTile(Node t) {
				if (t != null && !t.isDisabled()) {
						fxPlayer.shiftToNearestTile();
						((Scaffolding) t).breakBlock();
						fxPlayer.reloadMap();
				}
		}

		public void makeTimeline() {
				playerTimeline = new Timeline(new KeyFrame(new Duration(5), e -> {
						movePlayer();
				}));
				playerTimeline.setCycleCount(Animation.INDEFINITE);
				playerTimeline.play();

				enemyTimeline = new Timeline(new KeyFrame(new Duration(10), e -> {
				}));
				enemyTimeline.setCycleCount(Animation.INDEFINITE);
				enemyTimeline.play();
		}

		public void linkPlayer() {
				fxPlayer.setMap(this);
		}

		public void linkEnemies() {
			for (Node e : entityField.getChildren()) {
				if (e.getClass() == Enemy.class) {
					((Enemy) e).setMapAndPlayer(this, fxPlayer);
				}
			}
		}

		public boolean enemyInPlayer() {
			int[] playerPos = fxPlayer.findCurrentCoords();
            for (Enemy e : enemies) {
				if (Arrays.equals(e.findCurrentCoords(),playerPos)) return true;
			}
			return false;
		}

		public void kill(Enemy enemy) {
			enemy.getPathfinding().stop();
			entityField.getChildren().remove(enemy);
			enemies.remove(enemy);
			Timeline timeline = new Timeline(new KeyFrame(Duration.millis(3000), e -> {
				int[] current = Maps.getRespawn(MainController.currentMap);
				newEnemy(current[0], current[1]);
			}));
			timeline.setCycleCount(0);
			timeline.play();
		}

		public GridPane getMap() {
				return gameMap;
		}

		public void digGold(Gold gold) {
			gameMap.getChildren().remove(gold);
			Points.addPoints(100);
			if (isNoGoldLeft()) makeEscapeLadder();
		}

		private boolean isNoGoldLeft() {
			for (Node e : gameMap.getChildren()) {
				if (e.getClass() == Gold.class) return false;
			}
			return true;
		}

		private void removeEntities() {
			if (playerTimeline != null) {
				playerTimeline.stop();
			} if (enemyTimeline != null) {
				enemyTimeline.stop();
			}
			stopPathfinding();
			fxPlayer.setLayoutX(1000);
			fxPlayer.setLayoutY(1000);
            entityField.getChildren().removeIf(n -> n.getClass() == Enemy.class);
		}

		public void clear() {
			if (playerTimeline != null) {
				playerTimeline.stop();
			} if (enemyTimeline != null) {
				enemyTimeline.stop();
			}
			gameMap.getChildren().clear();
			stopPathfinding();
			removeEntities();
		}
}
