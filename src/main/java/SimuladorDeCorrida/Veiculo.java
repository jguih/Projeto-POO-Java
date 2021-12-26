package SimuladorDeCorrida;

// Autor: Jose Guilherme Alves

public abstract class Veiculo {

    private final int id;
    private int distanciaPercorrida;
    private final int roda_quantidade;
    private final Roda[] rodas;

    public Veiculo(int id, int roda_quantidade) {
        this.id = id;
        distanciaPercorrida = 0;
        this.roda_quantidade = roda_quantidade;
        rodas = new Roda[roda_quantidade];

        // Inicia as rodas
        for (int i = 0; i < roda_quantidade; i++) {
            rodas[i] = new Roda();
        }
    }

    public final int getID() {
        return id;
    }

    public final int getDistanciaPercorrida() {
        return distanciaPercorrida;
    }

    public final int getQtdRodas() {
        return roda_quantidade;
    }

    public final boolean getCalibragem(int index) {
        return rodas[index].getCalibragem();
    }

    public final void setDistanciaPercorrida(int distanciaPercorrida) {
        this.distanciaPercorrida = distanciaPercorrida;
    }

    public final void addDistanciaPercorrida(int distanciaPercorrida) {
        this.distanciaPercorrida += distanciaPercorrida;
    }

    public final boolean Tire_isAllCalibrated() // Verifica se todos os pneus estao calibrados
    {
        for (int i = 0; i < roda_quantidade; i++) {
            if (rodas[i].getCalibragem() == false) {
                return false;
            }
        }
        return true;
    }

    public final void CalibrateAllTires() // Calibra todos os pneus
    {
        for (int i = 0; i < roda_quantidade; i++) {
            if (rodas[i].getCalibragem() == false) {
                rodas[i].setCalibragem(true);
            }
        }
    }

    public final boolean CalibrateTire(int n) // Calibra um pneu n
    {
        if (n >= 0 && n < roda_quantidade) {
            if (getCalibragem(n) == false) {
                rodas[n].setCalibragem(true);
                return true; // Pneu calibrado
            } else {
                return false; // Pneu nao calibrado
            }
        } else {
            return false;
        }
    }

    public final void EmptyAllTires() { // Esvazia todos os pneus
        for (int i = 0; i < roda_quantidade; i++) {
            if (rodas[i].getCalibragem() == true) {
                rodas[i].setCalibragem(false);
            }
        }
    }

    public final boolean EmptyTire(int n) // Esvazia um pneu n
    {
        if (n >= 0 && n < roda_quantidade) {
            if (getCalibragem(n) == true) {
                rodas[n].setCalibragem(false);
                return true; // Pneu esvaziado
            } else {
                return false; // Pneu n jah esvaziado
            }
        } else {
            return false;
        }
    }

    public abstract boolean[] Move(int movimentos); // Move o veiculo

    @Override
    public abstract String toString(); // Retorna uma string com informacoes do veiculo

    public abstract void AddFuel(double fuel); // Abastece o veiculo

    public abstract int getBlocks_perMovement(); // Retorna a quantidade de blocos por movimento

    public abstract double getFuelCost_perBlock(); // Retorna a quantidade de combustivel gasto por bloco

    public abstract String getVehicleType(); // Retorna o tipo de veiculo
}
