package entities;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.util.Duration;
import objects.*;
import org.example.lodedigger.Area;
import org.example.lodedigger.Area.Direction;
import org.example.lodedigger.View;
import org.example.lodedigger.ViewSwitcher;

import java.io.IOException;

public class Player extends ImageView implements Entity {

		private Area area;

		private GridPane map;

		private Status status = Status.STANDING;

		private Timeline fallingTimeline;

		private Direction lastDirection = Direction.RIGHT;
		private Area.Facing facing = Area.Facing.RIGHT;

		private enum Status {
				STANDING, FALLING, CLIMBING;
		}

		public Player() throws IOException {
				FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/lodedigger/player-view.fxml"));
				loader.setRoot(this);
				loader.load();
		}

		public void setMap(Area area) {
				this.area = area;
				reloadMap();
		}

		public void reloadMap() {
				map = area.getMap();
		}


		/***********************
		 * Movement
		 * *********************/

		public void move(Direction direction) {
				boolean changed = lastDirection != direction;
				switch (direction) {
						case UP:
								tryMoveU(this.getLayoutX(), this.getLayoutY()-1);
								break;
						case DOWN:
								tryMoveD(this.getLayoutX(), this.getLayoutY()+1);
								break;
						case LEFT:
								tryMoveLR(this.getLayoutX()-1, this.getLayoutY());
								if (changed) setUrl("playerRunLeft.gif");
								facing = Area.Facing.LEFT;
								break;
						case RIGHT:
								tryMoveLR(this.getLayoutX()+1, this.getLayoutY());
								if (changed) setUrl("playerRunRight.gif");
								facing = Area.Facing.RIGHT;
								break;
						default: {
							if (changed) {
								switch (facing) {
									case LEFT:
										setUrl("playerLeft.png");
										break;
									case RIGHT:
										setUrl("playerRight.png");
										break;
								}
							}
							break;
						}
				}
				lastDirection = direction;
				isGold();
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
						if (y + 50 <= 0) {
							ViewSwitcher.switchTo(View.LOADING);
							this.setLayoutX(1000);
							this.setLayoutY(1000);
						} else {
							this.setLayoutX(x);
							this.setLayoutY(y);
							if (findCorrespondingTile(this.getLayoutX() + 25, this.getLayoutY() + 45) == null) {
								status = Status.STANDING;
								shiftToNearestTile();
							} else if (lastDirection != Direction.UP) setUrl("playerClimb.gif");
						}
				}
				else if (findCorrespondingTile(this.getLayoutX()+25, this.getLayoutY()+25) instanceof Climbable) {
						status = Status.CLIMBING;
						setUrl("playerClimb.gif");
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
								} else if (lastDirection != Direction.UP) setUrl("playerClimb.gif");
								break;
						case STANDING:
								if (hasLadderBelow(x,y)) {
										status = Status.CLIMBING;
										setUrl("playerClimb.gif");
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
				if (x + 50 > 600) return false;
				if (x < 0) return false;

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
				if (l != null && l.getClass() == Gold.class) l = null;
				if (r != null && r.getClass() == Gold.class) r = null;
				if (l != null && r != null) return !(l.isDisabled() && r.isDisabled());
				if (l != null) return !l.isDisabled();
				if (r != null) return !r.isDisabled();
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
				isGold();
		}

		public double[] whatIfShiftToNearestTile() {
				return new double[] {roundToNearestTileLayout(this.getLayoutX()), roundToNearestTileLayout(this.getLayoutY())};
		}

		private double roundToNearestTileLayout(double a) {
				return a%(600.0/12.0) < (600.0/12.0)/2.0 ? a-a%(600.0/12.0) : a-a%(600.0/12.0)+(600.0/12.0);
		}

		/****************************
		 * Digging
		 ****************************/

		public Node dig(int i) {
				if (status != Status.FALLING) {
						Timeline digTimeline = new Timeline(new KeyFrame(Duration.millis(200), e -> {
							switch (facing) {
								case LEFT:
									setUrl("playerLeft.png");
									break;
								case RIGHT:
									setUrl("playerRight.png");
									break;
							}
						}));
						digTimeline.setCycleCount(0);
						digTimeline.play();
						switch (facing) {
							case LEFT:
								setUrl("playerDigLeft.png");
								break;
							case RIGHT:
								setUrl("playerDigRight.png");
								break;
						}
						double whatIfCoords[] = whatIfShiftToNearestTile();
						Node n = findCorrespondingTile(whatIfCoords[0] + i, whatIfCoords[1] + 55);
						Node m = findCorrespondingTile(whatIfCoords[0] + 1, whatIfCoords[1]);
						if (n == null) return null;
                    	return n.getClass() == Scaffolding.class ? n : null;
				}
				return null;
		}

		/****************************
		 * Misc
		 ****************************/

		private void isGold() {
			Node current = findCurrentTile();
			if (current == null) return;
			if (current.getClass() == Gold.class) {
				area.digGold((Gold) current);
			}
		}

		private void setUrl(String imageName) {
			this.setImage(new Image(String.valueOf(getClass().getResource("/sprites/" + imageName))));
		}
}
