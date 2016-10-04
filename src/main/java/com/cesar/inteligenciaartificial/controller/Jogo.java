/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cesar.inteligenciaartificial.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import javax.swing.JTextField;

/**
 *
 * @author cesar
 */
public class Jogo {

    private Jogador homem = new Jogador("X", TipoJogador.HOMEM);
    private Jogador maquina = new Jogador("O", TipoJogador.MAQUINA);
    private LinkedList<JTextField> campos = null;
    private boolean maquinaInicia;
    private int contNumeroJogada = 0;
    List<List<Integer>> jogadasVitoria = new ArrayList<List<Integer>>();

    public Jogo(LinkedList<JTextField> campos) {
        this.campos = campos;
        jogadasVitoria.add(Arrays.asList(1, 2, 3));
        jogadasVitoria.add(Arrays.asList(4, 5, 6));
        jogadasVitoria.add(Arrays.asList(7, 8, 9));

        jogadasVitoria.add(Arrays.asList(1, 4, 7));
        jogadasVitoria.add(Arrays.asList(2, 5, 8));
        jogadasVitoria.add(Arrays.asList(3, 6, 9));

        jogadasVitoria.add(Arrays.asList(1, 5, 9));
        jogadasVitoria.add(Arrays.asList(7, 5, 3));
    }

    public void inicia(boolean maquinaInicia) throws Exception {
        contNumeroJogada = 0;
        this.maquinaInicia = maquinaInicia;
        verificaVezMaquina();
    }

    private void verificarGanhador() throws Exception {
        if (contNumeroJogada == campos.size()) {
            throw new Exception("o jogo terminou empatado!");
        } else if (ganhou(homem, clonarCampos())) {
            throw new Exception("Você ganhou!");
        } else if (ganhou(maquina, clonarCampos())) {
            throw new Exception("Você perdeu!");
        }
    }

    private void verificaVezMaquina() throws Exception {
        if (vezMaquina()) {
            maquinaJoga();
        }
    }

    private boolean vezMaquina() {
        return (maquinaInicia && contNumeroJogada % 2 == 0) || (!maquinaInicia && contNumeroJogada % 2 > 0);
    }

    private void maquinaJoga() throws Exception {

        List<Integer> camposDisponiveis = new ArrayList<>();

        for (int i = 0; i < campos.size(); i++) {
            JTextField jogada = campos.get(i);
            if (jogada.getText().equals("")) {
                camposDisponiveis.add(i);
            }
        }

        int melhorIndiceCampo = inteligenciaArtificial(camposDisponiveis);
        campos.get(melhorIndiceCampo).grabFocus();
        campos.get(melhorIndiceCampo).setText(maquina.getSimbolo());
    }

    private int inteligenciaArtificial(List<Integer> indiceCamposDisponiveis) {

        for (Integer indiceCampo : indiceCamposDisponiveis) {
            String[] cloneCampos = clonarCampos();
            cloneCampos[indiceCampo] = maquina.getSimbolo();
            if (ganhou(maquina, cloneCampos)) {
                return indiceCampo;
            }
        }

        for (Integer indiceCampo : indiceCamposDisponiveis) {
            String[] cloneCampos = clonarCampos();
            cloneCampos[indiceCampo] = homem.getSimbolo();
            if (ganhou(homem, cloneCampos)) {
                return indiceCampo;
            }
        }

        List<Integer> indiceCamposLateriais = new ArrayList<>();

        for (Integer indiceCampo : indiceCamposDisponiveis) {
            if (Arrays.asList(0, 2, 6, 8).contains(indiceCampo)) {
                indiceCamposLateriais.add(indiceCampo);
            }
        }

        if (!indiceCamposLateriais.isEmpty()) {
            return getRandom(indiceCamposLateriais);
        }

        return getRandom(indiceCamposDisponiveis);
    }

    static public <T> T getRandom(List<T> list) {
        if (list == null || list.isEmpty()) {
            return null;
        } else {
            return list.get(new Random().nextInt(list.size()));
        }
    }

    private String[] clonarCampos() {
        String[] cloneCampos = new String[campos.size()];
        int indice = 0;
        for (JTextField campo : campos) {
            cloneCampos[indice] = campo.getText();
            indice++;
        }
        return cloneCampos;
    }

    public void registraJogada() throws Exception {
        contNumeroJogada++;
        verificarGanhador();
        verificaVezMaquina();
    }

    public void validaJogada(JTextField campo) throws Exception {
        if (!vezMaquina() && !campo.getText().equals(homem.getSimbolo())) {
            throw new Exception("Seu símbolo é o (X), Ladrão!");
        }
    }

    private boolean ganhou(Jogador jogador, String[] camposJogo) {

        for (List<Integer> jogada : jogadasVitoria) {
            if (verificaJogada(camposJogo, jogada, jogador.getSimbolo())) {
                return true;
            }
        }
        return false;
    }

    private boolean verificaJogada(String[] camposJogo, List<Integer> indiceJogada, String simbolo) {
        for (int indice : indiceJogada) {
            if (!camposJogo[indice - 1].equals(simbolo)) {
                return false;
            }
        }
        return true;
    }

}
