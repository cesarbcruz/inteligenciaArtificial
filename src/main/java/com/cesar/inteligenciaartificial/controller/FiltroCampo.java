/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cesar.inteligenciaartificial.controller;

import static com.sun.xml.internal.fastinfoset.alphabet.BuiltInRestrictedAlphabets.table;
import java.awt.Color;
import java.awt.event.ActionEvent;
import javax.swing.Action;
import javax.swing.JTextField;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

public class FiltroCampo extends PlainDocument {

    private JTextField campo;
    private Action action;

    FiltroCampo(JTextField campos, Action action) {
        super();
        this.campo = campos;
        this.action = action;
    }

    public void insertString(int offset, String str, AttributeSet attr) throws BadLocationException {
        if (str == null || (!str.toUpperCase().equals("X") && !str.toUpperCase().equals("O"))) {
            return;
        }
        if ((getLength() + str.length()) <= 1) {
            super.insertString(offset, str.toUpperCase(), attr);
            campo.setEnabled(false);
            if(str.toUpperCase().equals("X")){
                campo.setDisabledTextColor(Color.red);
            }else{
                campo.setDisabledTextColor(Color.black);
            }
            campo.transferFocus();
            executarAcao(campo);
        }
    }

    private void executarAcao(JTextField campo) {
        ActionEvent event = new ActionEvent(
                campo,
                ActionEvent.ACTION_PERFORMED, "");
        action.actionPerformed(event);
    }
}
