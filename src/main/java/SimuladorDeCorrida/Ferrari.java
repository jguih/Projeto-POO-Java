package SimuladorDeCorrida;

import java.util.Random;

// Autor: Jose Guilherme Alves

public final class Ferrari extends VeiculoMotorizado implements IPVA {

    public static final int BLOCKS_PERMOVEMENT = 10;
    public static final double FUELCOST_PERBLOCKS = 2.3;
    public static final String VEHICLETYPE = "Ferrari";

    public Ferrari(int ID) {
        super(ID, 4);
        // IPVA
        CalcularIPVA();
        Random rand = new Random();
        setIPVA_condition(rand.nextBoolean());
    }

    @Override
    public int getBlocks_perMovement() {
        return Ferrari.BLOCKS_PERMOVEMENT;
    }

    @Override
    public double getFuelCost_perBlock() {
        return Ferrari.FUELCOST_PERBLOCKS;
    }

    @Override
    public String getVehicleType() {
        return Ferrari.VEHICLETYPE;
    }

    @Override
    public String toString() { // Retorna uma string com informacoes do veiculo
        String string = String.format("Ferrari | ID: %d | Distancia Percorrida: %d blocos |"
                + " Combustivel: %.2fL | IPVA: %s | Rodas: %d%d%d%d ", getID(), getDistanciaPercorrida(),
                getFuel(), ipva_condition ? "Pago" : "Nao Pago",
                getCalibragem(0) == true ? 1 : 0, getCalibragem(1) == true ? 1 : 0,
                getCalibragem(2) == true ? 1 : 0, getCalibragem(3) == true ? 1 : 0);
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

    @Override
    public boolean getIPVA_condition() {
        return ipva_condition;
    }
}
