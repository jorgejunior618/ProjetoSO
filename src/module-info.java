module TremCarga {
   requires javafx.controls;
   requires javafx.fxml;
   requires javafx.base;
   requires javafx.graphics;
requires java.desktop;

   opens threads.trem to javafx.graphics, javafx.fxml;
}