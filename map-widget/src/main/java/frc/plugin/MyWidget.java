package frc.plugin;

import edu.wpi.first.shuffleboard.api.data.types.NoneType;
import edu.wpi.first.shuffleboard.api.widget.Description;
import edu.wpi.first.shuffleboard.api.widget.ParametrizedController;
import edu.wpi.first.shuffleboard.api.widget.SimpleAnnotatedWidget;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

@Description(dataTypes = {NoneType.class}, name = "Experimental Widget")
@ParametrizedController(value = "MyWidget.fxml")
public class MyWidget extends SimpleAnnotatedWidget {
    
    @FXML
    protected Pane mPane;
    @FXML
    protected Label _negNum;
    @FXML
    protected Label _posNum;

    int _neg = 0;
    int _pos = 0;
    
    public Pane getView() {
        return mPane;
    }

    @FXML
    protected void onButtonPress(ActionEvent e) {
        _neg--;
        _pos++;

        _negNum.setText(Integer.toString(_neg));
        _posNum.setText(Integer.toString(_pos));
    }
}
