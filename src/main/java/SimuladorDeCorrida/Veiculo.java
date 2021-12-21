package SimuladorDeCorrida;

// Autor: Jose Guilherme Alves

public abstract class Veiculo {

    private final int id;
    private int distanciaPercorrida;
    private final int wheel_amount;
    private final Roda[] wheels;

    public Veiculo(int ID, int RODAS_AMOUNT) {
        this.id = ID;
        distanciaPercorrida = 0;
        this.wheel_amount = RODAS_AMOUNT;
        wheels = new Roda[RODAS_AMOUNT];

        // Inicia as rodas
        for (int i = 0; i < RODAS_AMOUNT; i++) {
            wheels[i] = new Roda();
        }
    }

    public final int getID() {
        return id;
    }

    public final int getDistanciaPercorrida() {
        return distanciaPercorrida;
    }

    public final int getQtdRodas() {
        return wheel_amount;
    }

    public final boolean getCalibragem(int index) {
        return wheels[index].getCalibragem();
    }

    public final void setDistanciaPercorrida(int distanciaPercorrida) {
        this.distanciaPercorrida = distanciaPercorrida;
    }

    public final void addDistanciaPercorrida(int distanciaPercorrida) {
        this.distanciaPercorrida += distanciaPercorrida;
    }

    public final boolean Tire_isAllCalibrated() // Verifica se todos os pneus estao calibrados
    {
        for (int i = 0; i < wheel_amount; i++) {
            if (wheels[i].getCalibragem() == false) {
                return false;
            }
        }
        return true;
    }

    public final void CalibrateAllTires() // Calibra todos os pneus
    {
        for (int i = 0; i < wheel_amount; i++) {
            if (wheels[i].getCalibragem() == false) {
                wheels[i].setCalibragem(true);
            }
        }
    }

    public final boolean CalibrateTire(int n) // Calibra um pneu n
    {
        if (n >= 0 && n < wheel_amount) {
            if (getCalibragem(n) == false) {
                wheels[n].setCalibragem(true);
                return true; // Pneu calibrado
            } else {
                return false; // Pneu nao calibrado
            }
        } else {
            return false;
        }
    }

    public final void EmptyAllTires() { // Esvazia todos os pneus
        for (int i = 0; i < wheel_amount; i++) {
            if (wheels[i].getCalibragem() == true) {
                wheels[i].setCalibragem(false);
            }
        }
    }

    public final boolean EmptyTire(int n) // Esvazia um pneu n
    {
        if (n >= 0 && n < wheel_amount) {
            if (getCalibragem(n) == true) {
                wheels[n].setCalibragem(false);
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
