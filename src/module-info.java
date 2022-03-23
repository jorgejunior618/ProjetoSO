module TremCarga {
	requires javafx.controls;
	requires javafx.fxml;
	requires javafx.base;
	requires javafx.graphics;
	
	opens threads.trem to javafx.graphics, javafx.fxml;
}
