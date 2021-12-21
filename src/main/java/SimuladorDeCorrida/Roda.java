package SimuladorDeCorrida;

import java.util.Random;

// Autor: Jose Guilherme Alves

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
