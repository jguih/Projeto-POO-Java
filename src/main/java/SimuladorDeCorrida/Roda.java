/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SimuladorDeCorrida;

import java.util.Random;

/**
 *
 * Aluno: José Guilherme Alves dos Santos RA: 2157187
 */
public class Roda {

    private boolean calibragemPneu;

    public Roda() {
        Random rand = new Random();
        int j = rand.nextInt(100);
        calibragemPneu = j % 2 == 0;
    }

    public void setCalibragem(boolean calibragemPneu) {
        this.calibragemPneu = calibragemPneu;
    }

    public boolean getCalibragem() {
        return calibragemPneu;
    }
}
