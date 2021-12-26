package SimuladorDeCorrida;

// Autor: Jose Guilherme Alves

public interface IPVA {

    double CONS_CARRO_POPULAR = 1.3;
    double CONS_MOTOCICLETA = 0.75;
    double CONS_FERRARI = 3.75;
    double BASEVALUE = 500;

    void CalcularIPVA(); // Calcular e armazena o falor do IPVA
    void setIPVA_condition(boolean condition); // Seta se o IPVA foi pago ou nao
}
