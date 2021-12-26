package SimuladorDeCorrida;

import java.util.Random;

// Autor: Jose Guilherme Alves

public final class CarroPopular extends VeiculoMotorizado implements IPVA {

    public static final int BLOCKS_PERMOVEMENT = 5;
    public static final double FUELCOST_PERBLOCKS = 0.75;
    public static final String VEHICLETYPE = "CarroPopular";

    public CarroPopular(int ID) {
        super(ID, 4);
        // IPVA
        CalcularIPVA();
        Random rand = new Random();
        setIPVA_condition(rand.nextBoolean());
    }

    // Retorna a quantidade de blocos por movimento
    @Override
    public int getBlocks_perMovement() {
        return CarroPopular.BLOCKS_PERMOVEMENT;
    }

    // Retorna a quantidade de combustivel gasto por bloco
    @Override
    public double getFuelCost_perBlock() {
        return CarroPopular.FUELCOST_PERBLOCKS;
    }

    @Override
    public String getVehicleType() { // Retorna o tipo de veiculo
        return CarroPopular.VEHICLETYPE;
    }

    @Override
    public String toString() { // Retorna uma string com informacoes do veiculo
        String string = String.format("CarroPopular | ID: %d | Distancia Percorrida: %d blocos |"
                + " Combustivel: %.2fL | IPVA: %s | Rodas: [%d%d%d%d] ", getID(), getDistanciaPercorrida(),
                getFuel(), getIPVA_condition() ? "Pago" : "Nao Pago",
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
