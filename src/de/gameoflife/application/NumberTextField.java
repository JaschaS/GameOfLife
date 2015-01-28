
package de.gameoflife.application;

import javafx.beans.value.ObservableValue;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

/**
 *
 * @author JScholz
 */
public class NumberTextField extends TextField {

    protected NumberTextListener listener;
    protected final int min;
    protected final int max;

    public NumberTextField(double width, int min, int max, String startValue) {

        super(startValue + "");

        setPrefWidth(width);

        this.min = min;
        this.max = max;

        focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
            if (!newValue) {

                int value = 0;

                if (getText().equals("")) {

                    setText(min + "");

                    value = min;

                } else {

                    value = Integer.parseInt(getText());

                    if (value > max) {

                        setText(max + "");
                        value = max;

                    } else {

                        if (value < min) {

                            setText(min + "");
                            value = min;

                        }

                    }

                }

                if (listener != null) {
                    listener.inputHasChanged(value);
                }

            }
        });

        setOnKeyPressed((KeyEvent event) -> {
            if (event.getCode() == KeyCode.ENTER) {

                setFocused(false);
                getParent().requestFocus();

            }
        });

    }

    @Override
    public void replaceText(int start, int end, String text) {

        if (validate(text)) {

            //Text equals "" means the user used the backspace
            if (text.equals("")) {

                text = getText(0, getText().length() - 1);

                super.setText(text);

            } else {

                super.replaceText(start, end, text);

            }

        }

    }

    @Override
    public void replaceSelection(String text) {

        if (validate(text)) {
            super.replaceSelection(text);
        }

    }

    public void setListener(NumberTextListener listener) {

        this.listener = listener;

    }

    public void removeListener() {

        this.listener = null;

    }

    protected boolean validate(String text) {

        return text.equals("") || text.matches("[0-9]");

    }
    
    public interface NumberTextListener {

        public void inputHasChanged(int value);

    }

}
