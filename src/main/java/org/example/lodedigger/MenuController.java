package org.example.lodedigger;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.RadioMenuItem;

public class MenuController {
    @FXML
    private MainController mainController;

    private int selectedMap = 1;

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    public MainController getMainController() {
        return mainController;
    }

    @FXML
    private void onNewGame() {
        mainController.startGame();
    }

    @FXML
    private void onClose() {
        Alert closeConfirmation = new Alert(Alert.AlertType.CONFIRMATION);
        closeConfirmation.setContentText("Viltu hætta?");
        closeConfirmation.showAndWait();
        if (closeConfirmation.getResult() == ButtonType.OK) {
            System.exit(0);
        }
    }

    @FXML
    private void setMap(ActionEvent actionEvent) {
        selectedMap = Integer.parseInt(((RadioMenuItem) actionEvent.getSource()).getId());
    }

    public int getMap() {
        return selectedMap;
    }

    @FXML
    private void onAbout() {
        Alert aboutWindow = new Alert(Alert.AlertType.INFORMATION);
        aboutWindow.setTitle("Um leikinn");
        aboutWindow.setHeaderText("Um leikinn");
        aboutWindow.setContentText("1. Hægt er að byrja nýjan leik í gegnum 'file' --> 'new game'\n" +
                "2. Stjórna karli með WASD, einnig er hægt að grafa með SPACE\n" +
                "3. Einnig er hægt að grafa á ská niður, ekki við hliðina á þér og ekki fyrir neðan þig\n" +
                "4. Markmið leiksinns er að ná öllu gullinu í borðinu\n" +
                "5. Eftir að búið er að ná öllu gullinu þá birtist stigi sem tekur þig í næsta borð\n" +
                "6. Passaðu þig samt á óvinum! Þeir eru að reyna að ná þér!\n" +
                "7. Kláraðu leikinn með því að komast í gegnum öll borðin án þess að deyja");
        aboutWindow.showAndWait();
    }
}
