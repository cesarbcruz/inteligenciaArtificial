/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cesar.inteligenciaartificial.controller;

import com.cesar.inteligenciaartificial.view.JogoVelhaUI;
import java.awt.event.ActionEvent;
import java.util.Arrays;
import java.util.LinkedList;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

/**
 *
 * @author cesar
 */
public class JogoVelhaCtrl {

    private JogoVelhaUI view;
    private Jogo jogo;
    
    public JogoVelhaCtrl() {
        view = new JogoVelhaUI(this);
        habilitarCampos(false);
        configuraCaracteresvalidos();
        jogo = new Jogo(getCampos());
    }

    public void iniciar() {
        view.setVisible(true);
    }

    public void euComeco() {
        try {
            habilitarCampos(true);
            jogo.inicia(false);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(view, ex.getMessage());
        }
    }

    public void maquinaComeca() {
        try {
            habilitarCampos(true);
            jogo.inicia(true);
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(view, ex.getMessage());
        }
    }

    public void euDesisto() {
        habilitarCampos(false);
    }

    private void habilitarCampos(boolean b) {
        view.getCampo1().setEnabled(b);
        view.getCampo2().setEnabled(b);
        view.getCampo3().setEnabled(b);
        view.getCampo4().setEnabled(b);
        view.getCampo5().setEnabled(b);
        view.getCampo6().setEnabled(b);
        view.getCampo7().setEnabled(b);
        view.getCampo8().setEnabled(b);
        view.getCampo9().setEnabled(b);

        view.getBotaoEuComeco().setEnabled(!b);
        view.getBotaoMaqComecao().setEnabled(!b);
        view.getBotaoDesistir().setEnabled(b);

        limparCampos();
    }

    private void limparCampos() {
        view.getCampo1().setText("");
        view.getCampo2().setText("");
        view.getCampo3().setText("");
        view.getCampo4().setText("");
        view.getCampo5().setText("");
        view.getCampo6().setText("");
        view.getCampo7().setText("");
        view.getCampo8().setText("");
        view.getCampo9().setText("");
    }

    private void configuraCaracteresvalidos() {
        try {

            Action acao = new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        if (jogo != null && !((JTextField) e.getSource()).getText().isEmpty()) {
                            jogo.validaJogada((JTextField) e.getSource());
                            jogo.registraJogada();
                        }
                    } catch (Exception ex) {                       
                        JOptionPane.showMessageDialog(null, ex.getMessage());
                        euDesisto();
                    }
                }
            };

            view.getCampo1().setDocument(new FiltroCampo(view.getCampo1(), acao));
            view.getCampo2().setDocument(new FiltroCampo(view.getCampo2(), acao));
            view.getCampo3().setDocument(new FiltroCampo(view.getCampo3(), acao));
            view.getCampo4().setDocument(new FiltroCampo(view.getCampo4(), acao));
            view.getCampo5().setDocument(new FiltroCampo(view.getCampo5(), acao));
            view.getCampo6().setDocument(new FiltroCampo(view.getCampo6(), acao));
            view.getCampo7().setDocument(new FiltroCampo(view.getCampo7(), acao));
            view.getCampo8().setDocument(new FiltroCampo(view.getCampo8(), acao));
            view.getCampo9().setDocument(new FiltroCampo(view.getCampo9(), acao));
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(view, ex.getMessage());
        }
    }

    private LinkedList getCampos() {
        return new LinkedList<JTextField>(Arrays.asList(view.getCampo1(), view.getCampo2(), view.getCampo3(), view.getCampo4(), view.getCampo5(), view.getCampo6(), view.getCampo7(), view.getCampo8(), view.getCampo9()));
    }
}
