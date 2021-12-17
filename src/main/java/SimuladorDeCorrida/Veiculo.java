package SimuladorDeCorrida;

/*
    Aluno: José Guilherme Alves dos Santos
    RA: 2157187
 */
public abstract class Veiculo {

    private final int ID;
    private int distanciaPercorrida;
    private final int RODAS_AMOUNT;
    private final Roda[] RODAS;

    public Veiculo(int ID, int RODAS_AMOUNT) {
        this.ID = ID;
        distanciaPercorrida = 0;
        this.RODAS_AMOUNT = RODAS_AMOUNT;
        RODAS = new Roda[RODAS_AMOUNT];

        // Inicia as rodas
        for (int i = 0; i < RODAS_AMOUNT; i++) {
            RODAS[i] = new Roda();
        }
    }

    public final int getID() {
        return ID;
    }

    public final int getDistanciaPercorrida() {
        return distanciaPercorrida;
    }

    public final int getQtdRodas() {
        return RODAS_AMOUNT;
    }

    public final boolean getCalibragem(int index) {
        return RODAS[index].getCalibragem();
    }

    public final void setDistanciaPercorrida(int distanciaPercorrida) {
        this.distanciaPercorrida = distanciaPercorrida;
    }

    public final void addDistanciaPercorrida(int distanciaPercorrida) {
        this.distanciaPercorrida += distanciaPercorrida;
    }

    public final boolean Tire_isAllCalibrated() // Verifica se todos os pneus estao calibrados
    {
        for (int i = 0; i < RODAS_AMOUNT; i++) {
            if (RODAS[i].getCalibragem() == false) {
                return false;
            }
        }
        return true;
    }

    public final void CalibrateAllTires() // Calibra todos os pneus
    {
        for (int i = 0; i < RODAS_AMOUNT; i++) {
            if (RODAS[i].getCalibragem() == false) {
                RODAS[i].setCalibragem(true);
            }
        }
    }

    public final boolean CalibrateTire(int n) // Calibra um pneu n
    {
        if (n >= 0 && n < RODAS_AMOUNT) {
            if (getCalibragem(n) == false) {
                RODAS[n].setCalibragem(true);
                return true; // Pneu calibrado
            } else {
                return false; // Pneu nao calibrado
            }
        } else {
            return false;
        }
    }

    public final void EmptyAllTires() { // Esvazia todos os pneus
        for (int i = 0; i < RODAS_AMOUNT; i++) {
            if (RODAS[i].getCalibragem() == true) {
                RODAS[i].setCalibragem(false);
            }
        }
    }

    public final boolean EmptyTire(int n) // Esvazia um pneu n
    {
        if (n >= 0 && n < RODAS_AMOUNT) {
            if (getCalibragem(n) == true) {
                RODAS[n].setCalibragem(false);
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
