package SimuladorDeCorrida;

import SimuladorDeCorrida_Exceptions.*;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

/*
    Autor: José Guilherme Alves dos Santos

    - A validacao de ID's ocorre apenas dentro da classe MenuFunctions 
      e nao deve ocorrer fora dela.
    - MenuFunctions eh uma private inner Class, pois ela soh deve existir se a 
      classe Simulador existir e nao deve ser usada fora dela. Isto tambem
      evita dubla validacao de variaveis dentro do Simulador.
    - Somente a classe Simulador deve imprir no terminal.
 */

public final class Simulador {
    private static class MenuFunctions {
        // Classe responsavel pelas funcoes do menu
        private static class ID_Class {
            // Classe responsavel por gerenciar os ID's dos veiculos
            
            private final ArrayList<Integer> ID_ArrayList = new ArrayList<>();

            public int Create() { // Gera um ID valido
                int new_id;
                boolean valid_id;

                if (ID_ArrayList.isEmpty()) { // Se nenhum valor foi inserido no array
                    // Gera um ID aleatorio entre 10 e 100
                    new_id = (int) (Math.random() * (100 - 10 + 1) + 10);
                    ID_ArrayList.add(new_id);
                } else { 
                    // Gera um ID aleatorio ateh que ele seja valido
                    do {
                        new_id = (int) (Math.random() * (100 - 10 + 1) + 10);

                        if (ID_ArrayList.contains(new_id)) {
                            valid_id = false;
                        } else {
                            ID_ArrayList.add(new_id);
                            valid_id = true;
                        }
                    } while (valid_id == false);
                }
                return new_id;
            }

            // Remove um ID da lista de ID's válidos
            public void Remove (int ID) throws InvalidIDException {
                if(isValid(ID) > -1) {
                    ID_ArrayList.remove(ID_ArrayList.indexOf(ID));
                } else {
                    throw new InvalidIDException();
                }
            }

            // Verifica se o ID eh valido e retorna o index
            public int isValid(int ID) throws InvalidIDException {
                for (int i = 0; i < ID_ArrayList.size(); i++) {
                    if (ID_ArrayList.get(i) == ID) {
                        return i;
                    }
                }
                throw new InvalidIDException();
            }
        }

        // Scanner
        private final Scanner scanner = new Scanner(System.in);
        // ArrayList para armazenar todos os veiculos
        private final ArrayList<Veiculo> VehicleArrayList = new ArrayList<>();
        // Objeto da classe ID_Class para manipular ID's
        private final ID_Class ID = new ID_Class();
        private final int MAX_VEHICLES = 20;
        
        // Retorna o tipo de veiculo a partir do ID
        // Esta funcao nao deve ser usada dentro desta classe
        public String getVehicleType(int type_id) {
            try {
                int index = ID.isValid(type_id);
                return VehicleArrayList.get(index).getVehicleType();
            } catch (InvalidIDException e) { return e.toString(); }
        }

        // Verifica se existe o tipo de veiculo na ArrayList
        public boolean VehicleArray_containsVehicleType(String VehicleType) {
            for (int i = 0; i < VehicleArrayList.size(); i++) {
                if (VehicleType.equals(VehicleArrayList.get(i).getVehicleType())) {
                    return true;
                }
            }
            return false;
        }

        // Insere um veiculo na ArrayList
        public void InsertVehicle(String VehicleType) throws MaxVehicleCapacityReachedException {
            Veiculo v_new = null;
            int index;
            
            if(VehicleArrayList.size() < 20) {
                switch (VehicleType) {
                    case "Bicicleta" -> {
                        v_new = new Bicicleta(ID.Create());
                        VehicleArrayList.add(v_new);
                    }
                    case "Motocicleta" -> {
                        v_new = new Motocicleta(ID.Create());
                        VehicleArrayList.add(v_new);
                    }
                    case "CarroPopular" -> {
                        v_new = new CarroPopular(ID.Create());
                        VehicleArrayList.add(v_new);
                    }
                    case "Ferrari" -> {
                        v_new = new Ferrari(ID.Create());
                        VehicleArrayList.add(v_new);
                    }
                }
                ClearScreen();
                index = VehicleArrayList.indexOf(v_new);
                System.out.printf("Veiculo %d inserido:\n", index + 1);
                System.out.println(VehicleArrayList.get(index).toString());
            } else { throw new MaxVehicleCapacityReachedException(); }
        }

        // Remove um veiculo da arraylist
        public void RemoveVehicle(int type_id) {
            try {
                String VehicleType;
                int index = ID.isValid(type_id);
                VehicleType = VehicleArrayList.get(index).getVehicleType();

                // Veiculo eh removido
                VehicleArrayList.remove(index);
                // ID eh removido da lista de ID's validos
                ID.Remove(type_id);

                ClearScreen();
                System.out.printf("Veiculo(%s,ID:%d) removido!\n", VehicleType, type_id);
            } catch (InvalidIDException e) {}
        }

        // Abastece o veiculo
        public void FuelVehicle(int type_id) {
            try {
                int index = ID.isValid(type_id);
                double fuel;
                
                if (VehicleArrayList.get(index) instanceof Bicicleta) {
                    ClearScreen();
                    System.out.println("Nao eh possivel abastecer bicicleta");
                } else {
                    // Recebe e valida a quantidade de combustivel
                    ClearScreen();
                    do {
                        System.out.printf("Informe a quantidade de combustivel em Litros: ");
                        fuel = scanner.nextFloat();
                        if (fuel < 0) {
                            System.out.println("Valor invalido");
                        }
                    } while (fuel < 0);

                    // Veiculo eh abastecido e isto eh informado ao usuario
                    VehicleArrayList.get(index).AddFuel(fuel);
                    ClearScreen();
                    System.out.printf("%s(ID:%d): Abastecido com %.2fL!\n",
                            VehicleArrayList.get(index).getVehicleType(),
                            type_id, fuel);
                    System.out.println(VehicleArrayList.get(index).toString());
                }
                
            } catch (InvalidIDException | InputMismatchException e) {}
        }

        // Imprime os dados de todos os veiculos
        public void PrintAllVehicles() {
            ClearScreen();
            System.out.println("Veiculos disponiveis: ");
            for (int i = 0; i < VehicleArrayList.size(); i++) {
                System.out.println(VehicleArrayList.get(i).toString());
            }
        }

        // Imprime dados de todos os veiculos por tipo
        public void PrintAllVehicles_perType(String VehicleType) {
            Veiculo v;

            if (VehicleArray_containsVehicleType(VehicleType)) {
                ClearScreen();
                System.out.printf("Veiculos(%s) disponiveis: \n", VehicleType);
                for (int i = 0; i < VehicleArrayList.size(); i++) {
                    v = VehicleArrayList.get(i);
                    if (v.getVehicleType().equals(VehicleType)) {
                        System.out.println(v.toString());
                    }
                }
            } else {
                System.out.printf("Nenhum veiculo(%s) econtrado\n", VehicleType);
            }
        }

        // Esvazia um pneu a partir do ID
        public void EmptyTire(int type_id) {
            int index_tire;
            int index = ID.isValid(type_id);
            boolean was_emptied; // Verifica se o penu foi esvaziado

            if (index > -1) {
                // Verifica qual tipo de veiculo
                if (VehicleArrayList.get(index) instanceof Bicicleta
                        || VehicleArrayList.get(index) instanceof Motocicleta) {
                    ClearScreen();
                    System.out.println(VehicleArrayList.get(index).toString());
                    do {
                        System.out.printf("Informe o index do pneu a ser esvaziado (0 ou 1): ");
                        index_tire = scanner.nextInt();
                        if (index_tire < 0 || index_tire > 1) {
                            System.out.println("Valor invalido");
                        }
                    } while (index_tire < 0 || index_tire > 1);
                } else { // Carro popular ou ferrari
                    ClearScreen();
                    System.out.println(VehicleArrayList.get(index).toString());
                    do {
                        System.out.printf("Informe o index do pneu a ser esvaziado (0,1,2 ou 3): ");
                        index_tire = scanner.nextInt();
                        if (index_tire < 0 || index_tire > 3) {
                            System.out.println("Valor invalido");
                        }
                    } while (index_tire < 0 || index_tire > 3);
                }
                was_emptied = VehicleArrayList.get(index).EmptyTire(index_tire);
                ClearScreen();
                if (was_emptied == true) {
                    System.out.printf("%s(ID:%d): Pneu %d esvaziado!\n",
                            VehicleArrayList.get(index).getVehicleType(), type_id, index_tire);
                    System.out.println(VehicleArrayList.get(index).toString());
                } else {
                    System.out.printf("Pneu %d jah estava vazio\n", index_tire);
                }
            } else {
                ClearScreen();
                System.out.println("ID nao encontrado");
            }
        }

        // Calibra um pneu n a partir do ID
        public void CalibrateTire(int type_id) {
            int index_tire;
            int index = ID.isValid(type_id);
            boolean was_calibrated; // Verifica se o pneu foi calibrado

            if (index > -1) {
                if (VehicleArrayList.get(index) instanceof Bicicleta
                        || VehicleArrayList.get(index) instanceof Motocicleta) {
                    ClearScreen();
                    System.out.println(VehicleArrayList.get(index).toString());
                    do {
                        System.out.printf("Informe o index do pneu a ser calibrado (0 ou 1): ");
                        index_tire = scanner.nextInt();
                        if (index_tire < 0 || index_tire > 1) {
                            System.out.println("Valor invalido");
                        }
                    } while (index_tire < 0 || index_tire > 1);
                } else { // Carro popular ou Ferrari
                    ClearScreen();
                    System.out.println(VehicleArrayList.get(index).toString());
                    do {
                        System.out.printf("Informe o index do pneu a ser calibrado (0,1,2 ou 3): ");
                        index_tire = scanner.nextInt();
                        if (index_tire < 0 || index_tire > 3) {
                            System.out.println("Valor invalido");
                        }
                    } while (index_tire < 0 || index_tire > 3);
                }

                was_calibrated = VehicleArrayList.get(index).CalibrateTire(index_tire);
                ClearScreen();
                if (was_calibrated == true) {
                    System.out.printf("%s(ID:%d): Pneu %d calibrado!\n",
                            VehicleArrayList.get(index).getVehicleType(),
                            type_id, index_tire);
                    System.out.println(VehicleArrayList.get(index).toString());
                } else {
                    System.out.printf("%s(ID:%d): Pneu %d jah estava calibrado\n",
                            VehicleArrayList.get(index).getVehicleType(),
                            type_id, index_tire);
                }
            } else {
                ClearScreen();
                System.out.println("ID nao encontrado");
            }
        }

        // Calibra todos os pneus de um veiculo a partir do ID
        public void CalibrateAllTires(int type_id) {
            int index = ID.isValid(type_id);

            if (index > -1) {
                VehicleArrayList.get(index).CalibrateAllTires();
                ClearScreen();
                System.out.printf("%s(ID:%d): Todos os pneus foram calibrados!\n",
                        VehicleArrayList.get(index).getVehicleType(), type_id);
                System.out.println(VehicleArrayList.get(index).toString());
            } else {
                System.out.println("ID nao encontrado");
            }
        }

        // Move um veiculo a partir do ID
        public void MoveVehicle(int type_id) {
            int movimentos;
            int counter = 0; // Conta situacoes favoraveis ao movimento do veiculo
            boolean[] condition; // Vetor booleano para verificar se o movimento ocorreu
            int index = ID.isValid(type_id);

            if (index > -1) {
                // Recebe e valida a quantidade de movimentos
                do {
                    ClearScreen();
                    System.out.println("Cada movimento equivalem a: ");
                    System.out.printf("%d blocos para %s\n",
                            VehicleArrayList.get(index).getBlocks_perMovement(),
                            VehicleArrayList.get(index).getVehicleType());
                    System.out.printf("Informe a quantidade de movimentos: ");
                    movimentos = scanner.nextInt();
                    if (movimentos < 0) {
                        System.out.println("Valor invalido");
                    }
                } while (movimentos < 0);

                // Armazena o vetor de boolean de retorno da funcao Move
                condition = VehicleArrayList.get(index).Move(movimentos);

                ClearScreen();
                if (VehicleArrayList.get(index) instanceof Bicicleta) {
                    // Verifica se o movimento ocorreu
                    if (condition[0]) {
                        System.out.printf("%s(ID:%d): Movido em %d blocos\n",
                                VehicleArrayList.get(index).getVehicleType(),
                                VehicleArrayList.get(index).getID(),
                                movimentos * VehicleArrayList.get(index).getBlocks_perMovement());
                    } else {
                        System.out.printf("%s(ID:%d): Pneus nao calibrados\n",
                                VehicleArrayList.get(index).getVehicleType(),
                                VehicleArrayList.get(index).getID());
                    }
                } else { // Ferrari, Carro Popular e Motocicleta
                    System.out.printf("%s(ID:%d): ",
                            VehicleArrayList.get(index).getVehicleType(),
                            VehicleArrayList.get(index).getID());

                    // Verifica o vetor de boolean para mostrar quais os problemas
                    if (!condition[0]) {
                        System.out.print("Pneus nao calibrados. ");
                    } else {
                        counter++;
                    }
                    if (!condition[1]) {
                        System.out.print("IPVA nao pago. ");
                    } else {
                        counter++;
                    }
                    if (!condition[2]) {
                        System.out.print("Combustivel insuficiente. ");
                    } else {
                        counter++;
                    }

                    if (counter == 3) { // Movimento ocorreu
                        System.out.printf("Movido em %d blocos!",
                                movimentos * VehicleArrayList.get(index).getBlocks_perMovement());
                    }

                    System.out.print("\n");
                }
            } else {
                ClearScreen();
                System.out.println("ID nao encontrado");
            }
        }

        // Move todos os veiculos
        public void MoveAllVehicles() {
            int movimentos;
            boolean[] condition;
            int counter;

            // Recebe e valida a quantidade de movimentos
            do {
                System.out.println("Cada movimento equivalem a: ");
                System.out.println("2 blocos para Bicicleta");
                System.out.println("3 blocos para Motocicleta");
                System.out.println("5 blocos para Carro Popular");
                System.out.println("10 blocos para Ferrari");
                System.out.printf("Informe a quantidade de movimentos: ");
                movimentos = scanner.nextInt();
                if (movimentos < 0) {
                    System.out.println("Valor invalido");
                }
            } while (movimentos < 0);

            ClearScreen();
            for (int i = 0; i < VehicleArrayList.size(); i++) {
                counter = 0; // Conta situacoes favoraveis ao movimento do veiculo
                condition = VehicleArrayList.get(i).Move(movimentos);
                if (VehicleArrayList.get(i) instanceof Bicicleta) {
                    // Verifica se o movimento ocorreu
                    if (condition[0]) {
                        System.out.printf("%s(ID:%d): Movido em %d blocos\n",
                                VehicleArrayList.get(i).getVehicleType(),
                                VehicleArrayList.get(i).getID(),
                                movimentos * VehicleArrayList.get(i).getBlocks_perMovement());
                    } else {
                        System.out.printf("%s(ID:%d): Pneus nao calibrados\n",
                                VehicleArrayList.get(i).getVehicleType(),
                                VehicleArrayList.get(i).getID());
                    }
                } else { // Ferrari, Carro Popular e Motocicleta
                    System.out.printf("%s(ID:%d): ",
                            VehicleArrayList.get(i).getVehicleType(),
                            VehicleArrayList.get(i).getID());

                    // Verifica o vetor de boolean para mostrar quais os problemas
                    if (!condition[0]) {
                        System.out.print("Pneus nao calibrados. ");
                    } else {
                        counter++;
                    }
                    if (!condition[1]) {
                        System.out.print("IPVA nao pago. ");
                    } else {
                        counter++;
                    }
                    if (!condition[2]) {
                        System.out.print("Combustivel insuficiente.");
                    } else {
                        counter++;
                    }

                    if (counter == 3) { // Movimento ocorreu
                        System.out.printf("Movido em %d blocos!",
                                movimentos * VehicleArrayList.get(i).getBlocks_perMovement());
                    }
                    System.out.print("\n");
                }
            }
        }

        // Imprime a pista de corrida
        public void PrintRoad() {
            int d;
            for (int i = 0; i < VehicleArrayList.size(); i++) {
                d = VehicleArrayList.get(i).getDistanciaPercorrida();
                switch (VehicleArrayList.get(i).getVehicleType()) {
                    case "Bicicleta" -> {
                        PrintSpaces(d);
                        System.out.print("   __o\n");
                        PrintSpaces(d);
                        System.out.print(" _`\\<,_ \n");
                        PrintSpaces(d);
                        System.out.print("(*)/ (*)\n");
                    }
                    case "CarroPopular" -> {
                        PrintSpaces(d);
                        System.out.print("    ____\n");
                        PrintSpaces(d);
                        System.out.print(" __/  |_ \\_\n");
                        PrintSpaces(d);
                        System.out.print("|  _     _``-.\n");
                        PrintSpaces(d);
                        System.out.print("'-(_)---(_)--'\n");
                    }
                    case "Motocicleta" -> {
                        PrintSpaces(d);
                        System.out.print("   ,_oo\n");
                        PrintSpaces(d);
                        System.out.print(".-/c-//:: \n");
                        PrintSpaces(d);
                        System.out.print("(_)'==(_)\n");
                    }
                    case "Ferrari" -> {
                        PrintSpaces(d);
                        System.out.print("        __         \n");
                        PrintSpaces(d);
                        System.out.print("      ~( @\\ \\   \n");
                        PrintSpaces(d);
                        System.out.print("   _____]_[_/_>__   \n");
                        PrintSpaces(d);
                        System.out.print("  / __ \\<> |  __ \\      \n");
                        PrintSpaces(d);
                        System.out.print("=\\_/__\\_\\__|_/__\\_D   \n");
                        PrintSpaces(d);
                        System.out.print("   (__)      (__)    \n");
                    }
                }
            }
        }

        // Imprime espacos de acordo com a distancia percorrida pelo veiculo
        public void PrintSpaces(int distance) {
            for (int i = 0; i < distance; i++) {
                System.out.printf(" ");
            }
        }

        // Funcao para limpar o teminal
        public void ClearScreen() {
            for (int i = 0; i <= 20; ++i) {
                System.out.println();
            }
        }
    }
    
    public static void main(String arg[]) {
        MenuFunctions Simulador = new MenuFunctions(); // Objeto da classe de funcoes
        Scanner scanner = new Scanner(System.in); // Scanner
        int option; // Opcao dentro das categorias do menu
        int menu; // Navegador do menu
        int type_id; // ID digitado pelo usuario

        do {
            System.out.print("""
                             --------------------------------------------------
                                                    MENU
                             -------------------------------------------------- 
                             |01| Incluir veiculo 
                             |02| Remover veiculo 
                             |03| Abastecer veiculo 
                             |04| Movimentar veiculo 
                             |05| Movimentar todos os veiculos                
                             |06| Imprimir dados de todos os veiculos         
                             |07| Imprimir dados de todos os veiculos por tipo  
                             |08| Esvaziar pneu 
                             |09| Calibrar pneu 
                             |10| Calibrar todos os pneus 
                             |11| Imprimir pista de corrida 
                             |12| Encerrar aplicacao 
                              """);
            System.out.println("Digite a opcao na proxima linha: ");
            menu = 100;
            try {
                menu = scanner.nextInt();
            } catch (InputMismatchException e) { 
                scanner.nextLine();
            }
            
            switch (menu) {
                case 1 -> {
                    // Inserir veiculo
                    option = 1;
                    do {
                        if (option == 1) { // Inserir
                            Simulador.ClearScreen();
                            System.out.println("""
                                               Tipo de veiculo a ser inserido:
                                               |1| Bicicleta
                                               |2| Carro Popular
                                               |3| Motocicleta
                                               |4| Ferrari
                                                   """);
                            System.out.println("Digite a opcao na proxima linha: ");
                            option = scanner.nextInt();
                            try {
                                // Valida a entrada
                                switch (option) {
                                    case 1 -> // Bicicleta
                                        Simulador.InsertVehicle("Bicicleta");
                                    case 2 -> // Carro popular
                                        Simulador.InsertVehicle("CarroPopular");
                                    case 3 -> // Motocicleta
                                        Simulador.InsertVehicle("Motocicleta");
                                    case 4 -> // Ferrari
                                        Simulador.InsertVehicle("Ferrari");
                                    default -> {
                                        // Opcao invalida
                                        Simulador.ClearScreen();
                                        System.out.println("Opcao invalida");
                                    }
                                }
                            } catch (MaxVehicleCapacityReachedException e) {
                                Simulador.ClearScreen();
                                System.out.println(e);
                                try {
                                    Thread.sleep(2500);
                                } catch (InterruptedException ie) {}
                            }
                        } else if (option != 2) {
                            Simulador.ClearScreen();
                            System.out.println("Opcao invalida");
                        }

                        System.out.println("""
                                           Opcoes:
                                           |1| Incluir outro veiculo
                                           |2| Voltar para o menu
                                               """);
                        System.out.printf("Digite a opcao na proxima linha: ");
                        option = scanner.nextInt();
                    } while (option != 2);
                }

                case 2 -> {
                    // Remover veiculo
                    
                    if (!Simulador.VehicleArrayList.isEmpty()) {
                        option = 1;
                        do {
                            if (option == 1) { // Remover
                                if (!Simulador.VehicleArrayList.isEmpty()) {
                                    try {
                                        Simulador.PrintAllVehicles();
                                        System.out.println("Informe o ID na proxima linha: ");
                                        type_id = scanner.nextInt();
                                        Simulador.RemoveVehicle(type_id);
                                    } catch (InputMismatchException e) {}
                                } else {
                                    Simulador.ClearScreen();
                                    System.out.println("Nenhum veiculo encontrado");
                                }
                            } else if (option == 'b') { // Imprimir dados de todos os veiculos
                                if (!Simulador.VehicleArrayList.isEmpty()) {
                                    Simulador.PrintAllVehicles();
                                } else {
                                    System.out.println("Nenhum veiculo encontrado");
                                }
                            } else if (option != 'c') {
                                Simulador.ClearScreen();
                                System.out.println("Opcao invalida");
                            }

                            System.out.println("Opcoes: ");
                            System.out.println("a: Remover outro veiculo ");
                            System.out.println("b: Imprimir dados de todos os veiculos ");
                            System.out.println("c: Voltar para o menu ");
                            System.out.printf("Opcao: ");
                            option = scanner.next().charAt(0);

                        } while (option != 'c');
                    } else {
                        Simulador.ClearScreen();
                        System.out.println("Nenhum veiculo encontrado");
                        do {
                            System.out.println("Opcoes: ");
                            System.out.println("a: Voltar para o menu ");
                            System.out.printf("Opcao: ");
                            option = scanner.next().charAt(0);
                            if (option != 'a') {
                                Simulador.ClearScreen();
                                System.out.println("Opcao invalida");
                            }
                        } while (option != 'a');
                    }
                }

                case 3 -> {
                    //Abastecer veiculo
                    
                    if (!Simulador.VehicleArrayList.isEmpty()) {
                        type_id = 0;
                        option = 'a';
                        do {
                            if (option == 'a') { // Abastecer
                                Simulador.PrintAllVehicles();
                                System.out.printf("Informe o ID do veiculo: ");
                                type_id = scanner.nextInt();
                                Simulador.FuelVehicle(type_id);
                            } else if (option == 'b') { // Abastecer mesmo veiculo novamente
                                Simulador.FuelVehicle(type_id);
                            } else if (option == 'c') { // Imprimir dados de todos os veiculos
                                Simulador.PrintAllVehicles();
                            } else if (option != 'd') {
                                Simulador.ClearScreen();
                                System.out.println("Opcao invalida");
                            }

                            System.out.println("Opcoes: ");
                            System.out.println("a: Abastecer outro veiculo");
                            System.out.printf("b: Abastecer o mesmo veiculo(%s,ID:%d)\n",
                                    Simulador.getVehicleType(type_id), type_id);
                            System.out.println("c: Imprimir dados de todos os veiculos");
                            System.out.println("d: Voltar para o menu");
                            System.out.printf("Opcao: ");
                            option = scanner.next().charAt(0);
                        } while (option != 'd');
                    } else {
                        Simulador.ClearScreen();
                        System.out.println("Nenhum veiculo encontrado");
                        do {
                            System.out.println("Opcoes: ");
                            System.out.println("a: Voltar para o menu ");
                            System.out.printf("Opcao: ");
                            option = scanner.next().charAt(0);
                            if (option != 'a') {
                                Simulador.ClearScreen();
                                System.out.println("Opcao invalida");
                            }
                        } while (option != 'a');
                    }
                }

                case 4 -> {
                    // Mover um veiculo
                    
                    if (!Simulador.VehicleArrayList.isEmpty()) {
                        type_id = 0;
                        option = 'a';
                        do {
                            if (option == 'a') { // Mover
                                Simulador.PrintAllVehicles();
                                System.out.printf("Informe o ID do veiculo: ");
                                type_id = scanner.nextInt();
                                Simulador.MoveVehicle(type_id);
                            } else if (option == 'b') { // Mover mesmo veiculo novamente
                                Simulador.MoveVehicle(type_id);
                            } else if (option == 'c') { // Imprimir dados de todos os veiculos
                                Simulador.PrintAllVehicles();
                            } else if (option != 'd') {
                                Simulador.ClearScreen();
                                System.out.println("Opcao invalida");
                            }

                            System.out.println("Opcoes:");
                            System.out.println("a: Mover outro veiculo");
                            System.out.printf("b: Mover o mesmo veiculo(%s,ID:%d)\n",
                                    Simulador.getVehicleType(type_id), type_id);
                            System.out.println("c: Imprimir dados de todos os veiculos");
                            System.out.println("d: Voltar para o menu");
                            System.out.printf("Opcao: ");
                            option = scanner.next().charAt(0);

                        } while (option != 'd');
                    } else {
                        Simulador.ClearScreen();
                        System.out.println("Nenhum veiculo encontrado");
                        do {
                            System.out.println("Opcoes: ");
                            System.out.println("a: Voltar para o menu ");
                            System.out.printf("Opcao: ");
                            option = scanner.next().charAt(0);
                            if (option != 'a') {
                                Simulador.ClearScreen();
                                System.out.println("Opcao invalida");
                            }
                        } while (option != 'a');
                    }
                }

                case 5 -> {
                    // Mover todos os veiculos
                    
                    if (!Simulador.VehicleArrayList.isEmpty()) {
                        option = 'a';
                        do {
                            if (option == 'a') { // Move todos os veiculos
                                Simulador.ClearScreen();
                                Simulador.MoveAllVehicles();
                            } else if (option == 'b') { // Imprime dados de todos os veiculos
                                Simulador.PrintAllVehicles();
                            } else if (option != 'c') {
                                Simulador.ClearScreen();
                                System.out.println("Opcao invalida");
                            }

                            System.out.println("Opcoes: ");
                            System.out.println("a: Mover todos os veiculos novamente ");
                            System.out.println("b: Imprimir dados de todos os veiculos ");
                            System.out.println("c: Voltar para o menu ");
                            System.out.printf("Opcao: ");
                            option = scanner.next().charAt(0);

                        } while (option != 'c');
                    } else {
                        Simulador.ClearScreen();
                        System.out.println("Nenhum veiculo encontrado");
                        do {
                            System.out.println("Opcoes: ");
                            System.out.println("a: Voltar para o menu ");
                            System.out.printf("Opcao: ");
                            option = scanner.next().charAt(0);
                            if (option != 'a') {
                                Simulador.ClearScreen();
                                System.out.println("Opcao invalida");
                            }
                        } while (option != 'a');
                    }
                }

                case 6 -> {
                    // Imprimir dados de todos os veiculos
                    
                    option = 'a';
                    do {
                        if (option == 'a') {
                            Simulador.PrintAllVehicles();
                        } else if (option != 'b') {
                            Simulador.ClearScreen();
                            System.out.println("Opcao invalida");
                        }

                        System.out.println("Opcoes: ");
                        System.out.println("a: Imprimir novamente ");
                        System.out.println("b: Voltar para o menu ");
                        System.out.printf("Opcao: ");
                        option = scanner.next().charAt(0);
                    } while (option != 'b');
                }

                case 7 -> {
                    // Imprimir dados de veiculos por tipo
                    
                    if (!Simulador.VehicleArrayList.isEmpty()) {
                        option = 'a';
                        do {
                            if (option == 'a') {
                                Simulador.ClearScreen();
                                System.out.println("Informe o tipo de veiculo: ");
                                System.out.println("a: Bicicleta");
                                System.out.println("b: Carro Popular");
                                System.out.println("c: Motocicleta");
                                System.out.println("d: Ferrari");
                                System.out.printf("Opcao: ");
                                option = scanner.next().charAt(0);

                                switch (option) {
                                    case 'a' -> // Bicicleta
                                        Simulador.PrintAllVehicles_perType("Bicicleta");
                                    case 'b' -> // Carro Popular
                                        Simulador.PrintAllVehicles_perType("CarroPopular");
                                    case 'c' -> // Motocicleta
                                        Simulador.PrintAllVehicles_perType("Motocicleta");
                                    case 'd' -> // Ferrari
                                        Simulador.PrintAllVehicles_perType("Ferrari");
                                    default -> {
                                        Simulador.ClearScreen();
                                        System.out.println("Opcao invalida");
                                    }
                                }
                            } else if (option != 'b') {
                                Simulador.ClearScreen();
                                System.out.println("Opcao invalida");
                            }

                            System.out.println("Opcoes: ");
                            System.out.println("a: Imprimir novamente ");
                            System.out.println("b: Voltar para o menu ");
                            System.out.printf("Opcao: ");
                            option = scanner.next().charAt(0);

                        } while (option != 'b');
                    } else {
                        Simulador.ClearScreen();
                        System.out.println("Nenhum veiculo encontrado");
                        do {
                            System.out.println("Opcoes: ");
                            System.out.println("a: Voltar para o menu ");
                            System.out.printf("Opcao: ");
                            option = scanner.next().charAt(0);
                            if (option != 'a') {
                                Simulador.ClearScreen();
                                System.out.println("Opcao invalida");
                            }
                        } while (option != 'a');
                    }
                }

                case 8 -> {
                    // Esvaziar um pneu especifico
                    
                    if (!Simulador.VehicleArrayList.isEmpty()) {
                        option = 'a';
                        type_id = 0;
                        do {
                            if (option == 'a') { // Esvaziar pneu
                                Simulador.PrintAllVehicles();
                                System.out.printf("Informe o ID do veiculo: ");
                                type_id = scanner.nextInt();
                                Simulador.EmptyTire(type_id);
                            } else if (option == 'b') { // Esvaziar outro pneu do mesmo veiculo
                                Simulador.EmptyTire(type_id);
                            } else if (option == 'c') { // Imprimir dados de todos os veiculos
                                Simulador.PrintAllVehicles();
                            } else if (option != 'd') {
                                Simulador.ClearScreen();
                                System.out.println("Opcao invalida");
                            }

                            System.out.println("Opcoes: ");
                            System.out.println("a: Esvaziar pneu de outro veiculo");
                            System.out.printf("b: Esvaziar pneu do mesmo veiculo(%s,ID:%d)\n",
                                    Simulador.getVehicleType(type_id), type_id);
                            System.out.println("c: Imprimir dados de todos os veiculos");
                            System.out.println("d: Voltar para o menu");
                            System.out.printf("Opcao: ");
                            option = scanner.next().charAt(0);

                        } while (option != 'd');
                    } else {
                        Simulador.ClearScreen();
                        System.out.println("Nenhum veiculo encontrado");
                        do {
                            System.out.println("Opcoes: ");
                            System.out.println("a: Voltar para o menu ");
                            System.out.printf("Opcao: ");
                            option = scanner.next().charAt(0);
                            if (option != 'a') {
                                Simulador.ClearScreen();
                                System.out.println("Opcao invalida");
                            }
                        } while (option != 'a');
                    }
                }

                case 9 -> {
                    // Calibrar um pneu especifico
                    
                    if (!Simulador.VehicleArrayList.isEmpty()) {
                        option = 'a';
                        type_id = 0;
                        do {
                            if (option == 'a') { // Calibrar pneu
                                Simulador.PrintAllVehicles();
                                System.out.printf("Informe o ID do veiculo: ");
                                type_id = scanner.nextInt();
                                Simulador.CalibrateTire(type_id);
                            } else if (option == 'b') { // Calibrar pneu do mesmo veiculo
                                Simulador.CalibrateTire(type_id);
                            } else if (option == 'c') { // Imprimir dados de todos os veiculos
                                Simulador.PrintAllVehicles();
                            } else if (option != 'd') {
                                Simulador.ClearScreen();
                                System.out.println("Opcao invalida");
                            }

                            System.out.println("Opcoes: ");
                            System.out.println("a: Calibrar pneu de outro veiculo ");
                            System.out.printf("b: Calibrar pneu do mesmo veiculo(%s,ID:%d)\n",
                                    Simulador.getVehicleType(type_id), type_id);
                            System.out.println("c: Imprimir dados de todos os veiculos ");
                            System.out.println("d: Voltar para o menu ");
                            System.out.printf("Opcao: ");
                            option = scanner.next().charAt(0);

                        } while (option != 'd');
                    } else {
                        Simulador.ClearScreen();
                        System.out.println("Nenhum veiculo encontrado");
                        do {
                            System.out.println("Opcoes: ");
                            System.out.println("a: Voltar para o menu ");
                            System.out.printf("Opcao: ");
                            option = scanner.next().charAt(0);
                            if (option != 'a') {
                                Simulador.ClearScreen();
                                System.out.println("Opcao invalida");
                            }
                        } while (option != 'a');
                    }
                }

                case 10 -> {
                    // Calibrar todos os pneus
                    
                    if (!Simulador.VehicleArrayList.isEmpty()) {
                        option = 'a';
                        do {
                            if (option == 'a') { // Calibra todos os pneus
                                Simulador.PrintAllVehicles();
                                System.out.printf("Informe o ID do veiculo: ");
                                type_id = scanner.nextInt();
                                Simulador.CalibrateAllTires(type_id);
                            } else if (option == 'b') { // Imprimi dados de todos os veiculos
                                Simulador.PrintAllVehicles();
                            } else if (option != 'c') {
                                Simulador.ClearScreen();
                                System.out.println("Opcao invalida");
                            }

                            System.out.println("Opcoes: ");
                            System.out.println("a: Calibrar pneus de outro veiculo ");
                            System.out.println("b: Imprimir dados de todos os veiculos");
                            System.out.println("c: Voltar para o menu ");
                            System.out.printf("Opcao: ");
                            option = scanner.next().charAt(0);
                        } while (option != 'c');
                    } else {
                        Simulador.ClearScreen();
                        System.out.println("Nenhum veiculo encontrado");
                        do {
                            System.out.println("Opcoes: ");
                            System.out.println("a: Voltar para o menu ");
                            System.out.printf("Opcao: ");
                            option = scanner.next().charAt(0);
                            if (option != 'a') {
                                Simulador.ClearScreen();
                                System.out.println("Opcao invalida");
                            }
                        } while (option != 'a');
                    }
                }

                case 11 -> {
                    // Imprimir pista de corrida
                    
                    if (!Simulador.VehicleArrayList.isEmpty()) {
                        option = 'a';
                        do {
                            if (option == 'a') {
                                Simulador.ClearScreen();
                                Simulador.PrintRoad();
                            } else if (option != 'b') {
                                Simulador.ClearScreen();
                                System.out.println("Opcao invalida");
                            }

                            System.out.println("Opcoes: ");
                            System.out.println("a: Imprimir novamente ");
                            System.out.println("b: Voltar para o menu ");
                            System.out.printf("Opcao: ");
                            option = scanner.next().charAt(0);

                        } while (option != 'b');
                    } else {
                        Simulador.ClearScreen();
                        System.out.println("Nenhum veiculo encontrado");
                        do {
                            System.out.println("Opcoes: ");
                            System.out.println("a: Voltar para o menu ");
                            System.out.printf("Opcao: ");
                            option = scanner.next().charAt(0);
                            if (option != 'a') {
                                Simulador.ClearScreen();
                                System.out.println("Opcao invalida");
                            }
                        } while (option != 'a');
                    }
                }
            }
            Simulador.ClearScreen();
        } while (menu != 12);
    }
}
