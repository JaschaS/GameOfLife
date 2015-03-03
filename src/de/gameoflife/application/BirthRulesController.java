package de.gameoflife.application;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.MultipleSelectionModel;
import javafx.scene.input.MouseEvent;
import rmi.data.rules.NumericRule;
import rmi.data.rules.RulePattern;

/**
 * FXML Controller class
 *
 * @author JScholz
 */
public final class BirthRulesController extends RulesController implements Initializable {

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        super.initialize(url, rb);

        numericPatternController.setFirstText("Birth at ");

        rulePatternController.selectCheckBox11(true);

        rules.setItems(list);

        rules.setOnMouseClicked((MouseEvent event) -> {

            MultipleSelectionModel selection = rules.getSelectionModel();

            int index = selection.getSelectedIndex();

            if (index >= 0 && index < list.size()) {

                showPattern(list.get(index).getRule());

            }

        });

    }

    @Override
    public void numericRule(ActionEvent event) throws IOException {

        int gameId = parent.getGameId();

        gameHandler.addBirthRule(gameId, new NumericRule());
        addItems(gameHandler.getBirthRules(gameId));

        numeric.setDisable(true);

    }

    @Override
    public void patternRule(ActionEvent event) throws IOException {

        int gameId = parent.getGameId();

        gameHandler.addBirthRule(gameId, new RulePattern(new boolean[8]));
        addItems(gameHandler.getBirthRules(gameId));

    }

    @Override
    protected void removeItem(int index) {
        gameHandler.removeBirthRule(parent.getGameId(), index);
    }

}
