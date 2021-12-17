package SimuladorDeCorrida;

import java.util.Arrays;

/*
    Aluno: José Guilherme Alves dos Santos
    RA: 2157187
 */
public abstract class VeiculoMotorizado extends Veiculo {

    private double fuel;
    private double IPVA;

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
    public final boolean Fuel_isEnough(int movimentos,
            int blocks_perMovement,
            double fuelCost_perBlock) {
        // (movimentos*blocos*gasto_por_bloco) = n Litros de combustivel gasto
        return (fuel - (movimentos * blocks_perMovement * fuelCost_perBlock)) >= 0;
    }

    @Override
    public void AddFuel(double fuel) { // Abastece veiculo
        if (fuel >= 0) {
            this.fuel += fuel;
        }
    }

    /* 
        A funcao Move retorna um vetor de boolean de tamanho 3, 
        verificando cada condicao na ordem: 
        Calibragem de todos os pneus, IPVA e Combustivel suficiente.
     */
    @Override
    public boolean[] Move(int movimentos) { // Movimenta o veiculo
        boolean[] BinaryMove = new boolean[3];
        boolean[] Was_Moved = {true, true, true};

        BinaryMove[0] = Tire_isAllCalibrated(); // Todos os pneus calibrados
        BinaryMove[1] = IPVA == 0; // IPVA pago
        BinaryMove[2] = Fuel_isEnough(movimentos, getBlocks_perMovement(), getFuelCost_perBlock()); // Combustivel suficiente
        if (Arrays.equals(BinaryMove, Was_Moved)) // Movimento pode ocorrer
        {
            addDistanciaPercorrida(movimentos * getBlocks_perMovement());
            RemoveFuel(movimentos * getBlocks_perMovement() * getFuelCost_perBlock());
        }
        return BinaryMove;
    }

    public final double getIPVA() {
        return IPVA;
    }

    public final void setIPVA(double IPVA) {
        this.IPVA = IPVA;
    }
}
