/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package SimuladorDeCorrida_Exceptions;

/**
 *
 * @author guikc
 */
public class MaxVehicleCapacityReachedException extends Exception {
    @Override
    public String toString() {
        return "Capacidade maxima de veiculos atingida";
    }
}
