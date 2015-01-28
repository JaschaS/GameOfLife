
package de.gameoflife.application;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import rmi.data.GameUI;

/**
 * FXML Controller class
 *
 * @author JScholz
 */
public class DeleteGameController extends ListViewWindow implements Initializable {

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        name.setCellValueFactory((TableColumn.CellDataFeatures<GameUI, String> param) -> new SimpleStringProperty( param.getValue().getGameName() ) );
        date.setCellValueFactory((TableColumn.CellDataFeatures<GameUI, String> param) -> new SimpleStringProperty( param.getValue().getCreationDate().toString() ) );
        
    }    
    
}
