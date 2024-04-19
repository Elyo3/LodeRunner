module org.example.lodedigger {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.example.lodedigger to javafx.fxml;
    exports org.example.lodedigger;
    exports objects;
    opens objects to javafx.fxml;
    exports entities;
    opens entities to javafx.fxml;
}
