package SimuladorDeCorrida;

import java.util.Arrays;

// Autor: Jose Guilherme Alves

public abstract class VeiculoMotorizado extends Veiculo {

    private double fuel;
    protected double ipva;
    protected boolean ipva_condition; // true = pago, false = nao pago

    public VeiculoMotorizado(int ID, int qtdRodas) {
        super(ID, qtdRodas);
        fuel = 3.5;
    }

    public final double getFuel() {
        return fuel;
    }

    public final void RemoveFuel(double removeValue) {
        if ((fuel - removeValue) >= 0) {
            fuel -= removeValue;
        }
    }

    // Verifica se eh possivel mover veiculo pela quantidade de combustivel
    public final boolean Fuel_isEnough(int blocks, double fuelCost_perBlock) {
        // (movimentos*blocos*gasto_por_bloco) = n Litros de combustivel gasto
        return (fuel - (blocks * fuelCost_perBlock)) >= 0;
    }

    @Override
    public void AddFuel(double fuel) { // Abastece veiculo
        if (fuel >= 0) {
            this.fuel += fuel;
        }
    }

    @Override
    public boolean[] Move(int blocks) { // Movimenta o veiculo
        boolean[] BinaryMove = new boolean[4];
        boolean[] Was_Moved = {false, true, true, true};
        
        BinaryMove[0] = false; // Condicao geral do movimento (ocorreu ou nao)
        BinaryMove[1] = Tire_isAllCalibrated(); // Todos os pneus calibrados
        BinaryMove[2] = ipva_condition; // ipva
        BinaryMove[3] = Fuel_isEnough(blocks, getFuelCost_perBlock()); // Combustivel suficiente
        if (Arrays.equals(BinaryMove, Was_Moved)) { // Movimento pode ocorrer
            BinaryMove[0] = true; // Movimento pode ocorrer
            addDistanciaPercorrida(blocks);
            RemoveFuel(blocks * getFuelCost_perBlock());
        }
        return BinaryMove;
    }
}
