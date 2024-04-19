package entities;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.util.Duration;
import objects.*;
import org.example.lodedigger.Area;
import vinnsla.Pathfinding;

import java.io.IOException;

public class Enemy extends ImageView {
		private Area area;

		private GridPane map;

		private Status status = Status.STANDING;

		private boolean isDying=false;

		public ObservableList<Integer> currentPosTight = FXCollections.observableArrayList(0, 0);

		private Pathfinding pathfinding;

		private Timeline fallingTimeline;

		private Area.Direction lastDirection = Area.Direction.LEFT;
		private Area.Facing facing = Area.Facing.LEFT;

		private enum Status {
			STANDING, FALLING, CLIMBING;
		}

		public Enemy() throws IOException {
				FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/lodedigger/enemy-view.fxml"));
				loader.setRoot(this);
				loader.load();
		}

		public void setMapAndPlayer(Area area, Player player) {
			this.area = area;
			reloadMap();
			pathfinding = new Pathfinding(this, map, player);
		}

		public void reloadMap() {
			map = area.getMap();
		}

		public Pathfinding getPathfinding() {
			return pathfinding;
		}

		/***********************
		 * Movement
		 * *********************/

		public void move(Area.Direction direction) {
			boolean changed = lastDirection != direction;
				switch (direction) {
					case UP:
						tryMoveU(this.getLayoutX(), this.getLayoutY() - 1);
						break;
					case DOWN:
						tryMoveD(this.getLayoutX(), this.getLayoutY() + 1);
						break;
					case LEFT:
						tryMoveLR(this.getLayoutX() - 1, this.getLayoutY());
						if (changed) {
							setUrl("enemyRunLeft.gif");
							facing = Area.Facing.LEFT;
						}
						break;
					case RIGHT:
						tryMoveLR(this.getLayoutX() + 1, this.getLayoutY());
						if (changed) {
							setUrl("enemyRunRight.gif");
							facing = Area.Facing.RIGHT;
						}
						break;
					default:
						if (changed) {
							switch (facing) {
								case LEFT:
									setUrl("enemyLeft.png");
									break;
								case RIGHT:
									setUrl("enemyRight.png");
									break;
							}
							shiftToNearestTile();
						}
						break;
				}
				updateTightPos();
				if (area.enemyInPlayer()) {
					area.gameOver();
				}
				lastDirection = direction;
		}

		public void tryMoveLR(double x, double y) {
			if (status != Status.FALLING && isLegal(x,y)) {
				this.setLayoutX(x);
				this.setLayoutY(y);
				if (!hasGround(this.getLayoutX(), this.getLayoutY())) fall();
			}
		}

		public void tryMoveU(double x, double y) {
			if (status == Status.CLIMBING) {
				this.setLayoutX(x);
				this.setLayoutY(y);
				if (findCorrespondingTile(this.getLayoutX()+25, this.getLayoutY()+45) == null) {
					status = Status.STANDING;
					shiftToNearestTile();
				}
			}
			else if (findCorrespondingTile(this.getLayoutX()+25, this.getLayoutY()+25) instanceof Climbable) {
				status = Status.CLIMBING;
				setUrl("enemyClimb.gif");
				shiftToNearestTile();
			}
		}

		public void tryMoveD(double x, double y) {
			switch (status) {
				case CLIMBING:
					this.setLayoutX(x);
					this.setLayoutY(y);
					if (hasFloor(x, y)) {
						shiftToNearestTile();
						status = Status.STANDING;
					}
					break;
				case STANDING:
					if (hasLadderBelow(x,y)) {
						status = Status.CLIMBING;
						setUrl("enemyClimb.gif");
						shiftToNearestTile();
					} break;
				default: break;
			}
		}

		private boolean isLegal(double x, double y) {
			Node tileL = findCorrespondingTile(x,y);
			Node tileR = findCorrespondingTile(x+50,y);
			Node tileLD = findCorrespondingTile(x, y+49);
			Node tileRD = findCorrespondingTile(x+50, y+49);

			boolean left = tileL == null ? true : tileL instanceof Block ? tileL.isDisable() : true;
			boolean right = tileR == null ? true : tileR instanceof Block ? tileR.isDisable() : true;
			boolean leftD = tileLD == null ? true : tileLD instanceof Block ? tileLD.isDisable() : true;
			boolean rightD = tileRD == null ? true : tileRD instanceof Block ? tileRD.isDisable() : true;
			return left && right && leftD && rightD;
		}

		private boolean isLadder(double x, double y) {
			Node n = findCorrespondingTile(x,y);
			return n != null && n.getClass() == Ladder.class;
		}

		private boolean isBlock(double x, double y) {
			Node n = findCorrespondingTile(x,y);
			return n != null && n instanceof Block;
		}

		private boolean hasLanding(double x, double y) {
			Node n = findCorrespondingTile(x+25, y+55);
			if (n != null) {
				if (n.getClass() == Gold.class) return false;
				return !(n instanceof Block && n.isDisabled() == true);
			}
			return false;
		}

		private boolean hasGround(double x, double y) {
			Node l = findCorrespondingTile(x + 2, y + 51);
			Node r = findCorrespondingTile(x + 48, y + 51);
			if (l != null && r != null) return !(l.isDisabled() == true && r.isDisabled() == true);
			if (l != null && r == null) return l.isDisabled() == false;
			if (l == null && r != null) return r.isDisabled() == false;
			return false;
		}

		private boolean hasFloor(double x, double y) {
			return isBlock(x + 2, y + 55) || isBlock(x + 48, y + 55);
		}

		private boolean hasLadderBelow(double x, double y) {
			return isLadder(x+10, y+55) && isLadder(x+40, y+55);
		}

		private Node findCorrespondingTile(double x, double y) {
			int tileX = (int) (x/600.0 * map.getColumnCount());
			int tileY = (int) (y/600.0 * map.getRowCount());
			for (Node n : map.getChildren()) {
				if (GridPane.getColumnIndex(n) == tileX && GridPane.getRowIndex(n) == tileY) {
					return n;
				}
			}
			return null;
		}

		public Node findCurrentTile() {
			return findCorrespondingTile(this.getLayoutX()+25, this.getLayoutY()+25);
		}

		public int[] findCurrentCoords() {
			int x = (int) ((this.getLayoutX() + 25) / 10) / 5;
			int y = (int) ((this.getLayoutY() + 25) / 10) / 5;
			return new int[]{x, y};
		}

		private void fall() {
			shiftToNearestTile();
			status = Status.FALLING;
			fallingTimeline = new Timeline(new KeyFrame(new Duration(7), e -> moveFall()));
			fallingTimeline.setCycleCount(Animation.INDEFINITE);
			fallingTimeline.play();
		}

		private void moveFall() {
			this.setLayoutY(this.getLayoutY()+1);
			if (hasLanding(this.getLayoutX(), this.getLayoutY())) {
				shiftToNearestTile();
				status = Status.STANDING;
				fallingTimeline.stop();
				fallingTimeline = null;
			}
		}

		public void shiftToNearestTile() {
			this.setLayoutX(roundToNearestTileLayout(this.getLayoutX()));
			this.setLayoutY(roundToNearestTileLayout(this.getLayoutY()));
		}

		private double roundToNearestTileLayout(double a) {
			return a%(600.0/12.0) < (600.0/12.0)/2.0 ? a-a%(600.0/12.0) : a-a%(600.0/12.0)+(600.0/12.0);
		}

		private void updateTightPos() {
			if (Math.abs(this.getLayoutX() - roundToNearestTileLayout(this.getLayoutX())) < 1 && Math.abs(this.getLayoutY() - roundToNearestTileLayout(this.getLayoutY())) < 1) {
				int[] current = findCurrentCoords();
				if (currentPosTight.get(0) != current[0]) {
					currentPosTight.set(0, current[0]);
				}
				if (currentPosTight.get(1) != current[1]) {
					currentPosTight.set(1, current[1]);
				}
			}
		}
		private void setTightPos() {
			int[] current = findCurrentCoords();
			currentPosTight.set(0, current[0]);
			currentPosTight.set(1, current[1]);
		}

		private void setUrl(String imageName) {
			this.setImage(new Image(String.valueOf(getClass().getResource("/sprites/" + imageName))));
		}

		public void kill() {
				pathfinding.stop();
				Timeline moveUpTimeline = new Timeline(new KeyFrame(Duration.millis(7), e -> {
					this.setLayoutY(this.getLayoutY() - 1);
				}));
				Timeline moveRLTimeline = new Timeline(new KeyFrame(Duration.millis(7), e -> {
					switch (facing) {
						case LEFT:
							this.setLayoutX(this.getLayoutX() - 1);
							break;
						case RIGHT:
							this.setLayoutX(this.getLayoutX() + 1);
							break;
						default: break;
					}
				}));
				moveRLTimeline.setCycleCount(50);
				moveUpTimeline.setCycleCount(50);
				moveRLTimeline.setOnFinished(e -> {
					setTightPos();
					pathfinding.play();
				});
				moveUpTimeline.setOnFinished(e -> {
					moveRLTimeline.play();
				});
				this.setTranslateX(4);
				Timeline escapeTimeline = new Timeline();
				escapeTimeline.getKeyFrames().add(new KeyFrame(Duration.millis(100), e -> {
					this.setTranslateX(-this.getTranslateX());
					if (!findCurrentTile().isDisable()) {
						area.kill(this);
						escapeTimeline.stop();
					}
				}));
				escapeTimeline.setCycleCount(15);
				escapeTimeline.setOnFinished(e -> {
					this.setTranslateX(0);
					moveUpTimeline.play();
				});
				escapeTimeline.play();
		}
	}
