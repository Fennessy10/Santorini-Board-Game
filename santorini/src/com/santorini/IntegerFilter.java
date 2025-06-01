package com.santorini;
import javax.swing.text.DocumentFilter;
import javax.swing.text.BadLocationException;


class IntegerFilter extends DocumentFilter {
    @Override
    public void insertString(FilterBypass fb, int offset, String string, javax.swing.text.AttributeSet attr)
            throws BadLocationException {
        if (string.matches("\\d+")) {
            super.insertString(fb, offset, string, attr);
        }
    }

    @Override
    public void replace(FilterBypass fb, int offset, int length, String string, javax.swing.text.AttributeSet attr)
            throws BadLocationException {
        if (string.matches("\\d*")) {
            super.replace(fb, offset, length, string, attr);
        }
    }
}
