package ua.org.s4code.intellicalc.analyser.exception;

import android.util.Log;
import android.widget.EditText;

/**
 * Created by Serhii on 8/16/2015.
 */
public class ExprException extends Exception {

    protected int selectionStart;
    protected int selectionEnd;

    public ExprException(int start, int end, String message) {
        super(message);

        selectionStart = start;
        selectionEnd = end;
    }

    public int selectionStart() {
        return selectionStart;
    }

    public int selectionEnd() {
        return selectionEnd;
    }

    /**
     * Trying to avoid exceptions while selecting text in EditText
     */
    public static void selectText(EditText editText, int start, int end) {
        int localStart = start;
        int localEnd = end;

        if (localStart < 0) {
            localStart = 0;
        }
        int textLength = editText.length();
        if (textLength < localEnd) {
            localEnd = textLength;
        }

        if (localStart >= localEnd) {
            localStart = localEnd;
        }

        editText.clearFocus(); // give an ability to select text
        editText.setSelection(localStart, localEnd);
    }

    public void selectText(EditText editText) {
        ExprException.selectText(editText, selectionStart, selectionEnd);
    }

}
