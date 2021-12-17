package SimuladorDeCorrida;

import java.util.Random;

/*
    Aluno: José Guilherme Alves dos Santos
    RA: 2157187
 */
public final class Motocicleta extends VeiculoMotorizado implements IPVA {

    public static final int BLOCKS_PERMOVEMENT = 3;
    public static final double FUELCOST_PERBLOCKS = 0.25;
    public static final String VEHICLETYPE = "Motocicleta";

    public Motocicleta(int ID) {
        super(ID, 2);
        // IPVA
        boolean test;
        Random rand = new Random();
        test = rand.nextBoolean();
        if (test == true) {
            setIPVA(CalcularIPVA()); // Nao pago
        } else {
            setIPVA(0); // Pago
        }
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
                + " Combustivel: %.2fL | IPVA: %s | Rodas: %d%d ", getID(), getDistanciaPercorrida(),
                getFuel(), getIPVA() == 0 ? "Pago" : String.format("%.2f", getIPVA()),
                getCalibragem(0) == true ? 1 : 0, getCalibragem(1) == true ? 1 : 0);
        return string;
    }

    @Override
    public double CalcularIPVA() {
        return BASEVALUE * CONS_MOTOCICLETA;
    }
}
