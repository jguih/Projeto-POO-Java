package SimuladorDeCorrida_Exceptions;

// Autor: Jose Guilherme Alves

public class MaxVehicleCapacityReachedException extends Exception {
    @Override
    public String toString() {
        return "Capacidade Maxima de Veiculos Atingida";
    }
}
