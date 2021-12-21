package SimuladorDeCorrida;


// Autor: Jose Guilherme Alves

public final class Bicicleta extends Veiculo {

    public static final int BLOCKS_PERMOVEMENT = 2;
    public static final String VEHICLE_TYPE = "Bicicleta";

    public Bicicleta(int ID) {
        super(ID, 2);
    }

    @Override
    public void AddFuel(double fuel) { // Abastecer veiculo (neste caso eh vazia)
    }

    @Override
    public String getVehicleType() { // Retorna o tipo de veiculo
        return Bicicleta.VEHICLE_TYPE;
    }

    @Override
    public boolean[] Move(int movimentos) { // Move o veiculo
        boolean[] BinaryMove = new boolean[1];

        if (Tire_isAllCalibrated() == true) {
            addDistanciaPercorrida(movimentos * 2);
            BinaryMove[0] = true; // Movimento ocorreu
        } else {
            BinaryMove[0] = false; // Movimento nao ocorreu
        }
        return BinaryMove;
    }

    // Retorna a quantidade de blocos por movimento
    @Override
    public int getBlocks_perMovement() {
        return Bicicleta.BLOCKS_PERMOVEMENT;
    }

    // Retorna a quantidade de combustivel gasto por bloco
    @Override
    public double getFuelCost_perBlock() {
        return 0; // Neste caso, nao ha gasto de combustivel
    }

    @Override
    public String toString() { // Retorna uma string com informacoes do veiculo
        String string = String.format("Bicicleta | ID: %d | "
                + "Distancia Percorrida: %d blocos | Rodas: %d%d",
                 getID(), getDistanciaPercorrida(),
                getCalibragem(0) == true ? 1 : 0, getCalibragem(1) == true ? 1 : 0);
        return string;
    }
}
