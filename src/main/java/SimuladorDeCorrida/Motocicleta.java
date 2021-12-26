package SimuladorDeCorrida;

import java.util.Random;

// Autor: Jose Guilherme Alves

public final class Motocicleta extends VeiculoMotorizado implements IPVA {

    public static final int BLOCKS_PERMOVEMENT = 3;
    public static final double FUELCOST_PERBLOCKS = 0.25;
    public static final String VEHICLETYPE = "Motocicleta";

    public Motocicleta(int ID) {
        super(ID, 2);
        // IPVA
        CalcularIPVA();
        Random rand = new Random();
        setIPVA_condition(rand.nextBoolean());
    }

    @Override
    public int getBlocks_perMovement() {
        return Motocicleta.BLOCKS_PERMOVEMENT;
    }

    @Override
    public double getFuelCost_perBlock() {
        return Motocicleta.FUELCOST_PERBLOCKS;
    }

    @Override
    public String getVehicleType() { // Retorna o tipo de veiculo
        return Motocicleta.VEHICLETYPE;
    }

    @Override
    public String toString() { // Retorna uma string com informacoes do veiculo
        String string = String.format("Motocicleta | ID: %d | Distancia Percorrida: %d blocos |"
                + " Combustivel: %.2fL | IPVA: %s | Rodas: [%d%d] ", getID(), getDistanciaPercorrida(),
                getFuel(), ipva_condition ? "Pago" : "Nao Pago",
                getCalibragem(0) == true ? 1 : 0, getCalibragem(1) == true ? 1 : 0);
        return string;
    }

    @Override
    public void CalcularIPVA() {
        ipva = BASEVALUE * CONS_CARRO_POPULAR;
    }

    @Override
    public void setIPVA_condition(boolean condition) {
        ipva_condition = condition;
    }
}
