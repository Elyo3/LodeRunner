package org.example.lodedigger;

public enum View {
    PLAY("main-view.fxml"),
    LOADING("loading-view.fxml");

    private String s;

    View(String s) {
        this.s = s;
    }

    public String getName() {
        return s;
    }
}
