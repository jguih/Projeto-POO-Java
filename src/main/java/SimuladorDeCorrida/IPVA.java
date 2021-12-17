package SimuladorDeCorrida;

/*
    Aluno: José Guilherme Alves dos Santos
    RA: 2157187
 */
public interface IPVA {

    double CONS_CARRO_POPULAR = 1.3;
    double CONS_MOTOCICLETA = 0.75;
    double CONS_FERRARI = 3.75;
    double BASEVALUE = 500;

    double CalcularIPVA();
}
