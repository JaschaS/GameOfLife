package de.gameoflife.application;

import de.gameoflife.connection.rmi.GameHandler;
import java.io.IOException;
import java.net.URL;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MultipleSelectionModel;
import javafx.scene.layout.StackPane;
import rmi.data.rules.Evaluable;
import rmi.data.rules.NumericRule;
import rmi.data.rules.RulePattern;

/**
 * FXML Controller class
 *
 * @author JScholz
 */
public abstract class RulesController implements Initializable {

    @FXML
    protected Button close;
    @FXML
    protected Button up;
    @FXML
    protected Button down;
    @FXML
    protected ListView rules;
    @FXML
    protected StackPane displayRules;

    protected final ObservableList<ListItems> list = FXCollections.observableArrayList();
    protected final GameHandler gameHandler = GameHandler.getInstance();
    protected GameTab parent;
    protected Parent numericPattern;
    protected Parent rulePattern;
    protected RulePatternController rulePatternController;
    protected NumericPatternController numericPatternController;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        try {

            FXMLLoader rulePatternLoader = new FXMLLoader(getClass().getResource("FXML/RulePattern.fxml"));

            rulePattern = (Parent) rulePatternLoader.load();
            rulePatternController = rulePatternLoader.getController();
            rulePattern.setVisible(false);

            FXMLLoader numericPatternLoader = new FXMLLoader(getClass().getResource("FXML/NumericPattern.fxml"));

            numericPattern = (Parent) numericPatternLoader.load();
            numericPatternController = numericPatternLoader.getController();
            numericPattern.setVisible(false);

            displayRules.getChildren().addAll(rulePattern, numericPattern);

        } catch (IOException ex) {
            Logger.getLogger(RulesController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void showNumericPattern() {

        numericPattern.toFront();
        numericPattern.setVisible(true);

        rulePattern.toBack();
        rulePattern.setVisible(false);
    }

    private void showRulePattern() {

        numericPattern.toBack();
        numericPattern.setVisible(false);

        rulePattern.toFront();
        rulePattern.setVisible(true);
    }

    public void closeActionEvent(EventHandler<ActionEvent> event) {
        close.setOnAction(event);
    }

    public void setParent(GameTab parent) {
        this.parent = parent;
    }

    @FXML
    protected abstract void numericRule(ActionEvent event) throws IOException;

    @FXML
    protected abstract void patternRule(ActionEvent event) throws IOException;

    @FXML
    protected void up(ActionEvent event) throws IOException {

        int index = rules.getSelectionModel().getSelectedIndex();

        if (index > 0) {

            ListItems first = list.remove(index);
            ListItems tmp = list.remove(index - 1);

            list.add(index - 1, first);
            list.add(index, tmp);

            rules.getSelectionModel().select(index - 1);

        }

    }

    @FXML
    protected void down(ActionEvent event) throws IOException {

        int index = rules.getSelectionModel().getSelectedIndex();

        if (index < list.size() - 1) {

            ListItems first = list.remove(index);

            list.add(index + 1, first);

            rules.getSelectionModel().select(index + 1);

        }

    }

    @FXML
    protected void remove(ActionEvent event) throws IOException {

        MultipleSelectionModel model = rules.getSelectionModel();

        int index = model.getSelectedIndex();

        if (index > -1) {

            list.remove(index);

            if (list.size() == 0) {
                hidePattern();
            } else {

                index = model.getSelectedIndex();

                if (index > -1) {
                    showPattern(list.get(index).getRule());
                }

            }

        }

    }

    public void hidePattern() {
        numericPattern.setVisible(false);
        rulePattern.setVisible(false);
    }

    public void addItems(List<Evaluable> items) {

        if (items != null) {

            list.clear();

            Iterator<Evaluable> it = items.iterator();

            while (it.hasNext()) {

                Evaluable e = it.next();

                if (e instanceof NumericRule) {
                    list.add(new ListItems("Numeric Rule", e));
                } else {
                    if (e instanceof RulePattern) {
                        list.add(new ListItems("Rule Pattern", e));
                    }
                }

            }
        }

    }

    protected void showPattern(Evaluable e) {

        if (e instanceof NumericRule) {

            showNumericPattern();
            try {
                numericPatternController.setItems(((NumericRule) e).getConfigurationAsArray());
            } catch (IOException ex) {
                Logger.getLogger(RulesController.class.getName()).log(Level.SEVERE, null, ex);
            }

        } else {
            if (e instanceof RulePattern) {

                showRulePattern();

                rulePatternController.setGrid(((RulePattern) e).getPattern());

            }
        }
    }

    public class ListItems {

        private final Evaluable rule;
        private final Label label;

        public ListItems(String text, Evaluable rule) {
            this.rule = rule;
            label = new Label(text);
        }

        public String getText() {

            return label.getText();

        }

        public Evaluable getRule() {
            return rule;
        }

        @Override
        public String toString() {
            return label.getText();
        }

    }

}
