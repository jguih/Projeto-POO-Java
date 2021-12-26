package SimuladorDeCorrida;

import SimuladorDeCorrida_Exceptions.*;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

// Autor: Jose Guilherme Alves

public final class Simulador {
    private static class VehicleController { // Classe de manipulacao de veiculos
        private static class ID_Class { // Classe responsavel por gerenciar os ID's dos veiculos
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
        private final Scanner input = new Scanner(System.in);
        // ArrayList para armazenar todos os veiculos
        private final ArrayList<Veiculo> VehicleArrayList = new ArrayList<>();
        // Objeto da classe ID_Class para manipular ID's
        private final ID_Class ID = new ID_Class();
        private static final int MAX_VEHICLES = 20;
        
        // Retorna o tipo de veiculo a partir do ID
        public String getVehicleType(int type_id) throws InvalidIDException {
            int index = ID.isValid(type_id);
            return VehicleArrayList.get(index).getVehicleType();
        }

        // Insere um veiculo na ArrayList
        public void InsertVehicle(String VehicleType) throws MaxVehicleCapacityReachedException {
            Veiculo v_new = null;
            int index;
            
            if(VehicleArrayList.size() < MAX_VEHICLES) {
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
        public void RemoveVehicle(int type_id) 
                throws VehicleArrayListIsEmptyException, InvalidIDException {
            if(!VehicleArrayList.isEmpty()) {
                String VehicleType;
                int index = ID.isValid(type_id); // InvalidIDException
                VehicleType = VehicleArrayList.get(index).getVehicleType();

                // Veiculo eh removido
                VehicleArrayList.remove(index);
                // ID eh removido da lista de ID's validos
                ID.Remove(type_id); // InvalidIDException

                ClearScreen();
                System.out.printf("Veiculo(%s,ID:%d) removido!\n", VehicleType, type_id);
            } else { throw new VehicleArrayListIsEmptyException(); }
        }

        // Abastece o veiculo
        public void FuelVehicle(int type_id) throws InvalidIDException {
            int index = ID.isValid(type_id);
            Veiculo v = VehicleArrayList.get(index);
            double fuel;

            if (v instanceof Bicicleta) {
                ClearScreen();
                System.out.println("Nao eh possivel abastecer bicicleta");
            } else {
                // Recebe e valida a quantidade de combustivel
                ClearScreen();
                do {
                    System.out.println("Informe a quantidade de combustivel em Litros: ");
                    try {
                        fuel = input.nextFloat(); // Exception
                        if (fuel <= 0) {
                            ClearScreen();
                            System.out.println("Input Invalido");
                        }
                    } catch (InputMismatchException e1) {
                        fuel = -1;
                        input.nextLine();
                        ClearScreen();
                        System.out.println("Input Invalido");
                    }
                } while (fuel <= 0);

                // Veiculo eh abastecido e isto eh informado ao usuario
                v.AddFuel(fuel);
                ClearScreen();
                System.out.printf("%s(ID:%d): Abastecido com %.2fL!\n",
                        v.getVehicleType(),
                        type_id, fuel);
                System.out.println(v.toString());
            }
        }

        // Imprime os dados de todos os veiculos
        public boolean PrintAllVehicles() throws VehicleArrayListIsEmptyException {
            
            if(VehicleArrayList.isEmpty()) {
                throw new VehicleArrayListIsEmptyException();
            }
            
            ClearScreen();
            System.out.println("Veiculos disponiveis: ");
            for (int i = 0; i < VehicleArrayList.size(); i++) {
                System.out.println(VehicleArrayList.get(i).toString());
            }
            return true;
        }

        // Imprime dados de todos os veiculos por tipo
        public void PrintAllVehicles_perType(String VehicleType) { 
            class Verify { // Verifica se existe o tipo de veiculo na ArrayList
                public int VehicleArray_containsVehicleType(String VehicleType) {
                    for (int i = 0; i < VehicleArrayList.size(); i++) {
                        if (VehicleType.equals(VehicleArrayList.get(i).getVehicleType())) {
                            return i;
                        }
                    }
                    return -1;
                }
            }
            
            Verify verify=new Verify();
            Veiculo v;
            int verify_return = verify.VehicleArray_containsVehicleType(VehicleType);
            
            if (verify_return>-1) {
                ClearScreen();
                System.out.printf("Veiculos(%s) disponiveis: \n", VehicleType);
                for (int i = verify_return; i < VehicleArrayList.size(); i++) {
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
        public void EmptyTire(int type_id) throws InvalidIDException {
            int index_tire;
            int index = ID.isValid(type_id);
            Veiculo v = VehicleArrayList.get(index);
            boolean was_emptied; // Verifica se o penu foi esvaziado

            // Verifica qual tipo de veiculo
            if (v instanceof Bicicleta || v instanceof Motocicleta) {
                ClearScreen();
                System.out.println(v.toString());
                do {
                    System.out.println("Informe o index do pneu (0 ou 1): ");
                    try {
                        index_tire = input.nextInt(); // Exception
                        if (index_tire < 0 || index_tire > 1) {
                            ClearScreen();
                            System.out.println("Input Invalido");
                        }
                    } catch (InputMismatchException e1) {
                        index_tire = -1;
                        input.nextLine();
                        ClearScreen();
                        System.out.println("Input Invalido");
                    }
                } while (index_tire < 0 || index_tire > 1);
            } else { // Carro popular ou ferrari
                ClearScreen();
                System.out.println(v.toString());
                do {
                    System.out.println("Informe o index do pneu (0,1,2 ou 3): ");
                    try {
                        index_tire = input.nextInt(); // Exception
                        if (index_tire < 0 || index_tire > 3) {
                            ClearScreen();
                            System.out.println("Input Invalido");
                        }
                    } catch (InputMismatchException e1) {
                        index_tire = -1;
                        input.nextLine();
                        ClearScreen();
                        System.out.println("Input Invalido");
                    }
                } while (index_tire < 0 || index_tire > 3);
            }
            // O pneu eh evaziado
            was_emptied = v.EmptyTire(index_tire);

            ClearScreen();
            if (was_emptied) {
                System.out.printf("(%s | ID:%d): Pneu %d esvaziado!\n",
                        v.getVehicleType(), type_id, index_tire);
                System.out.println(v.toString());
            } else {
                System.out.printf("Pneu %d jah estava vazio\n", index_tire);
            }
        }

        // Calibra um pneu n a partir do ID
        public void CalibrateTire(int type_id) throws InvalidIDException {
            int index_tire;
            int index = ID.isValid(type_id);
            Veiculo v = VehicleArrayList.get(index);
            boolean was_calibrated; // Verifica se o pneu foi calibrado
            
            if (v instanceof Bicicleta || v instanceof Motocicleta) {
                ClearScreen();
                System.out.println(v.toString());
                System.out.println("Informe o index do pneu (0 ou 1): ");
                do {
                    try {
                        index_tire = input.nextInt(); // Exception
                        if (index_tire < 0 || index_tire > 1) {
                            ClearScreen();
                            System.out.println("Input Invalido");
                        }
                    } catch (InputMismatchException e1) {
                        index_tire = -1;
                        input.nextLine();
                        ClearScreen();
                        System.out.println("Input Invalido");
                    }
                } while (index_tire < 0 || index_tire > 1);
            } else { // Carro popular ou Ferrari
                ClearScreen();
                System.out.println(v.toString());
                System.out.println("Informe o index do pneu (0,1,2 ou 3): ");
                do {
                    try {
                        index_tire = input.nextInt(); // Exception
                        if (index_tire < 0 || index_tire > 3) {
                            ClearScreen();
                            System.out.println("Input Invalido");
                        }
                    } catch (InputMismatchException e1) {
                        index_tire = -1;
                        input.nextLine();
                        ClearScreen();
                        System.out.println("Input Invalido");
                    }
                } while (index_tire < 0 || index_tire > 3);
            }
            was_calibrated = v.CalibrateTire(index_tire);
            ClearScreen();
            if (was_calibrated == true) {
                System.out.printf("(%s | ID:%d): Pneu %d calibrado!\n",
                        v.getVehicleType(),
                        type_id, index_tire);
                System.out.println(v.toString());
            } else {
                System.out.printf("(%s | ID:%d): Pneu %d jah estava calibrado\n",
                        v.getVehicleType(),
                        type_id, index_tire);
            }
        }

        // Calibra todos os pneus de um veiculo a partir do ID
        public void CalibrateAllTires(int type_id) throws InvalidIDException {
            int index = ID.isValid(type_id);
            Veiculo v = VehicleArrayList.get(index);
            
            v.CalibrateAllTires();
            ClearScreen();
            System.out.printf("(%s | ID:%d): Todos os pneus foram calibrados!\n",
                    v.getVehicleType(), type_id);
            System.out.println(v.toString());
        }

        // Move um veiculo a partir do ID
        public void MoveVehicle(int type_id) 
                throws InvalidIDException, VehicleArrayListIsEmptyException {
            int moves;
            boolean[] condition; // Vetor booleano para verificar se o movimento ocorreu
            int index = ID.isValid(type_id); // InvalidIDException
            Veiculo v = VehicleArrayList.get(index);
            
            if (VehicleArrayList.isEmpty()) {
                throw new VehicleArrayListIsEmptyException();
            }
            
            // Recebe e valida a quantidade de moves
            ClearScreen();
            System.out.println("Cada unidade de movimento equivale a: ");
            System.out.printf("%d Blocos para %s\n",
                    v.getBlocks_perMovement(),
                    v.getVehicleType());
            System.out.println("Informe a quantidade de unidades de movimentos: ");
            do {
                try {
                    moves = input.nextInt(); // Exception
                    if (moves < 0) {
                        ClearScreen();
                        System.out.println("Input Invalido");
                    }
                } catch (InputMismatchException e) {
                    moves = -1;
                    input.nextLine();
                    ClearScreen();
                    System.out.println("Input Invalido");
                }
                
            } while (moves < 0);

            // Armazena o vetor de boolean de retorno da funcao Move
            condition = v.Move(moves * v.getBlocks_perMovement());

            ClearScreen();
            if (v instanceof Bicicleta) {
                // Verifica se o movimento ocorreu
                if (condition[0]) {
                    System.out.printf("(%s | ID:%d): Movido em %d blocos\n",
                            v.getVehicleType(),
                            v.getID(),
                            moves * v.getBlocks_perMovement());
                } else {
                    System.out.printf("(%s | ID:%d): Pneus nao calibrados\n",
                            v.getVehicleType(),
                            v.getID());
                }
            } else { // Ferrari, Carro Popular e Motocicleta
                System.out.printf("(%s | ID:%d): ",
                        v.getVehicleType(),
                        v.getID());

                // Verifica o vetor de boolean para mostrar quais os problemas
                if (condition[0]) { // Movimento ocorreu
                    System.out.printf("Movido em %d blocos!",
                            moves * v.getBlocks_perMovement());
                } else {
                    if (!condition[1]) {
                        System.out.print("Pneus nao calibrados");
                        if(!condition[2] || !condition[3]) {
                            System.out.print(" | ");
                        }
                    }
                    if (!condition[2]) {
                        System.out.print("IPVA nao pago ");
                        if(!condition[3]) {
                            System.out.print(" | ");
                        }
                    }
                    if (!condition[3]) {
                        System.out.print("Combustivel insuficiente ");
                    }
                }
                System.out.print("\n");
            }
        }

        // Move todos os veiculos
        public void MoveAllVehicles() throws VehicleArrayListIsEmptyException {
            int moves = -1;
            boolean[] condition;
            Veiculo v;

            if(VehicleArrayList.isEmpty()) {
                throw new VehicleArrayListIsEmptyException();
            }
            
            // Recebe e valida a quantidade de moves
            ClearScreen();
            do {
                System.out.println("""
                                   Veiculos se movimentam em blocos,
                                   Cada unidade de movimento equivale a:
                                   2 Blocos para Bicicleta
                                   3 Blocos para Motocicleta
                                   5 Blocos para Carro Popular
                                   10 Blocos para Ferrari
                                   """);
                System.out.println("Informe a quantidade de movimentos: ");
                try {
                    moves = input.nextInt();
                    if (moves <= 0) {
                        ClearScreen();
                        System.out.println("Input Invalido");
                    }
                } catch (InputMismatchException e1) {
                    input.nextLine();
                    ClearScreen();
                    System.out.println("Input Invalido");
                }
            } while (moves <= 0);

            ClearScreen();
            for (int i = 0; i < VehicleArrayList.size(); i++) {
                v = VehicleArrayList.get(i); // Pega o veiculo
                condition = v.Move(moves); // Tenta realizar o movimento
                if (v instanceof Bicicleta) {
                    // Verifica se o movimento ocorreu
                    if (condition[0]) {
                        System.out.printf("%s(ID:%d): Movido em %d blocos\n",
                                v.getVehicleType(),
                                v.getID(),
                                moves * v.getBlocks_perMovement());
                    } else {
                        System.out.printf("%s(ID:%d): Pneus nao calibrados\n",
                                v.getVehicleType(),
                                v.getID());
                    }
                } else { // Ferrari, Carro Popular e Motocicleta
                    System.out.printf("%s(ID:%d): ",
                            v.getVehicleType(),
                            v.getID());

                    // Verifica o vetor de boolean para mostrar quais os problemas
                    if (condition[0]) { // Movimento ocorreu
                        System.out.printf("Movido em %d blocos!",
                                moves * v.getBlocks_perMovement());
                    } else { // contition[0] == false
                        if (!condition[1]) {
                            System.out.print("Pneus nao calibrados");
                            if (!condition[2] || !condition[3]) {
                                System.out.print(" | ");
                            }
                        }
                        if (!condition[2]) {
                            System.out.print("IPVA nao pago ");
                            if (!condition[3]) {
                                System.out.print(" | ");
                            }
                        }
                        if (!condition[3]) {
                            System.out.print("Combustivel insuficiente ");
                        }
                    }
                    System.out.print("\n");
                }
            }
        }

        // Imprime a pista de corrida
        public void PrintRoad() throws VehicleArrayListIsEmptyException {
            int d;
            
            if(VehicleArrayList.isEmpty()) {
                throw new VehicleArrayListIsEmptyException();
            }
            
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
            for (int i = 0; i <= 30; ++i) {
                System.out.println();
            }
        }
    }
    
    public static void main(String arg[]) {
        VehicleController Simulador = new VehicleController();
        Scanner input = new Scanner(System.in);
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
            try {
                menu = input.nextInt(); // Exception
            } catch (InputMismatchException e) { 
                menu = 100;
                input.nextLine();
            }
            
            switch (menu) {
                case 1 -> { // Inserir veiculo
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
                            try {
                                option = input.nextInt(); // Exception
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
                                        Simulador.ClearScreen();
                                        System.out.println("Opcao Invalida");
                                    }
                                }
                            } catch (MaxVehicleCapacityReachedException e1) {
                                Simulador.ClearScreen();
                                System.out.println(e1);
                            } catch (InputMismatchException e2) {
                                option = 3;
                                input.nextLine();
                                Simulador.ClearScreen();
                                System.out.println("Opcao Invalida");
                            }
                        } else if (option != 2) {
                            Simulador.ClearScreen();
                            System.out.println("Opcao Invalida");
                        }

                        System.out.println("""
                                           Opcoes:
                                           |1| Incluir outro veiculo
                                           |2| Voltar para o menu
                                           """);
                        System.out.println("Digite a opcao na proxima linha: ");
                        try {
                            option = input.nextInt(); // Exception
                        } catch (InputMismatchException e1) {
                            option = 3;
                            input.nextLine();
                        }
                    } while (option != 2);
                }

                case 2 -> { // Remover veiculo
                    option = 1;
                    do {
                        if (option == 1) { // Remover
                            try {
                                Simulador.PrintAllVehicles(); // Exception
                                System.out.println("Informe o ID na proxima linha: ");
                                type_id = input.nextInt(); // Exception
                                Simulador.RemoveVehicle(type_id); // Exception
                            } catch (InputMismatchException e1) {
                                input.nextLine(); // Limpa o buffer
                                Simulador.ClearScreen();
                                System.out.println("Input Invalido");
                            } catch (InvalidIDException | VehicleArrayListIsEmptyException e2) {
                                Simulador.ClearScreen();
                                System.out.println(e2);
                            }
                        } else if (option == 2) { // Imprimir dados de todos os veiculos
                            try {
                                Simulador.PrintAllVehicles();
                            } catch (VehicleArrayListIsEmptyException e1) {
                                Simulador.ClearScreen();
                                System.out.println(e1);
                            }
                        } else if (option != 3) {
                            Simulador.ClearScreen();
                            System.out.println("Opcao Invalida");
                        }

                        System.out.println("""
                                           Opcoes:
                                           |1| Remover outro veiculo
                                           |2| Imprimir dados de todos os veiculos
                                           |3| Voltar para o menu
                                           """);
                        System.out.println("Digite a opcao na proxima linha: ");
                        try {
                            option = input.nextInt(); // Exception
                        } catch (InputMismatchException e1) {
                            option = 4;
                            input.nextLine();
                        }
                    } while (option != 3);
                }

                case 3 -> { //Abastecer veiculo
                    type_id = -1; // Valor padrao
                    option = 1;
                    do {
                        if (option == 1 || (option == 2 && type_id == -1)) { // Abastecer
                            try {
                                Simulador.PrintAllVehicles(); // Exception
                                System.out.println("Informe o ID do veiculo: ");
                                type_id = input.nextInt(); // Exception
                                Simulador.FuelVehicle(type_id); // Exception
                            } catch (InputMismatchException e1) {
                                input.nextLine();
                                type_id = -1;
                                Simulador.ClearScreen();
                                System.out.println("Input Invalido");
                            } catch (InvalidIDException e2) {
                                type_id = -1;
                                Simulador.ClearScreen();
                                System.out.println(e2);
                            } catch (VehicleArrayListIsEmptyException e3) {
                                Simulador.ClearScreen();
                                System.out.println(e3);
                            }
                        } else if (option == 2) { // Abastecer mesmo veiculo novamente
                            try {
                                Simulador.FuelVehicle(type_id);
                            } catch (InvalidIDException e1) {
                                Simulador.ClearScreen();
                                System.out.println(e1);
                            }
                        } else if (option == 3) { // Imprimir dados de todos os veiculos
                            try {
                                Simulador.PrintAllVehicles();
                            } catch (VehicleArrayListIsEmptyException e1) {
                                Simulador.ClearScreen();
                                System.out.println(e1);
                            }
                        } else if (option != 4) {
                            Simulador.ClearScreen();
                            System.out.println("Opcao Invalida");
                        }
                        
                        System.out.println("Opcoes: ");
                        System.out.println("|1| Abastecer outro veiculo");
                        if(type_id != -1) {
                            String vehicleType=new String();
                            try {
                                vehicleType = Simulador.getVehicleType(type_id);
                            } catch (InvalidIDException e1) {}
                            System.out.printf ("|2| Abastecer o mesmo veiculo(%s,ID:%d)\n",
                                    vehicleType, type_id);
                        } else {
                            System.out.printf ("|2| Abastecer o mesmo veiculo\n");
                        }
                        System.out.println("|3| Imprimir dados de todos os veiculos");
                        System.out.println("|4| Voltar para o menu");
                        System.out.println("Digite a opcao na proxima linha: ");
                        try {
                            option = input.nextInt(); // Exception
                        } catch (InputMismatchException e1) {
                            option = 5;
                            input.nextLine();
                        }
                    } while (option != 4);
                }

                case 4 -> { // Mover um veiculo
                    type_id = -1;
                    option = 1;
                    do {
                        if (option == 1 || (option == 2 && type_id == -1)) { // Mover
                            try {
                                Simulador.PrintAllVehicles(); // Exception
                                System.out.println("Informe o ID do veiculo: ");
                                type_id = input.nextInt(); // Exception
                                Simulador.MoveVehicle(type_id); // Exception
                            } catch (InputMismatchException e1) {
                                input.nextLine();
                                type_id = -1;
                                Simulador.ClearScreen();
                                System.out.println("Input Invalido");
                            } catch (InvalidIDException e2) {
                                type_id = -1;
                                Simulador.ClearScreen();
                                System.out.println(e2);
                            } catch (VehicleArrayListIsEmptyException e3) {
                                Simulador.ClearScreen();
                                System.out.println(e3);
                            }
                        } else if (option == 2) { // Mover mesmo veiculo novamente
                            try {
                                Simulador.MoveVehicle(type_id);
                            } catch (InvalidIDException | VehicleArrayListIsEmptyException e1) {
                                Simulador.ClearScreen();
                                System.out.println(e1);
                            }
                        } else if (option == 3) { // Imprimir dados de todos os veiculos
                            try {
                                Simulador.PrintAllVehicles();
                            } catch (VehicleArrayListIsEmptyException e1) {
                                Simulador.ClearScreen();
                                System.out.println(e1);
                            }
                        } else if (option != 4) {
                            Simulador.ClearScreen();
                            System.out.println("Opcao Invalida");
                        }

                        System.out.println("Opcoes:");
                        System.out.println("|1| Mover outro veiculo");
                        if(type_id != -1) {
                            String vehicleType=new String();
                            try {
                                vehicleType = Simulador.getVehicleType(type_id);
                            } catch (InvalidIDException e1) {}
                            System.out.printf("|2| Mover o mesmo veiculo(%s,ID:%d)\n",
                                    vehicleType, type_id);
                        } else {
                            System.out.printf("|2| Mover o mesmo veiculo\n");
                        }
                        System.out.println("|3| Imprimir dados de todos os veiculos");
                        System.out.println("|4| Voltar para o menu");
                        System.out.println("Digite a opcao na proxima linha: ");
                        try {
                            option = input.nextInt(); // Exception
                        } catch (InputMismatchException e1) {
                            option = 5;
                            input.nextLine();
                        }
                    } while (option != 4);
                }

                case 5 -> { // Mover todos os veiculos
                    option = 1;
                    do {
                        if (option == 1) { // Move todos os veiculos
                            Simulador.ClearScreen();
                            try {
                                Simulador.MoveAllVehicles();
                            } catch (VehicleArrayListIsEmptyException e1) {
                                Simulador.ClearScreen();
                                System.out.println(e1);
                            }
                        } else if (option == 2) { // Imprime dados de todos os veiculos
                            try {
                                Simulador.PrintAllVehicles();
                            } catch (VehicleArrayListIsEmptyException e1) {
                                Simulador.ClearScreen();
                                System.out.println(e1);
                            }
                        } else if (option != 3) {
                            Simulador.ClearScreen();
                            System.out.println("Opcao invalida");
                        }
                        System.out.println("Opcoes: ");
                        System.out.println("|1| Mover todos os veiculos novamente ");
                        System.out.println("|2| Imprimir dados de todos os veiculos ");
                        System.out.println("|3| Voltar para o menu ");
                        System.out.println("Digite a opcao na proxima linha: ");
                        try {
                            option = input.nextInt(); // Exception
                        } catch (InputMismatchException e1) {
                            option = 4;
                            input.nextLine();
                        }
                    } while (option != 3);
                }

                case 6 -> { // Imprimir dados de todos os veiculos
                    option = 1;
                    do {
                        if (option == 1) {
                            try {
                                Simulador.PrintAllVehicles();
                            } catch (VehicleArrayListIsEmptyException e1) {
                                Simulador.ClearScreen();
                                System.out.println(e1);
                            }
                        } else if (option != 2) {
                            Simulador.ClearScreen();
                            System.out.println("Opcao Invalida");
                        }
                        System.out.println("Opcoes: ");
                        System.out.println("|1| Imprimir novamente ");
                        System.out.println("|2| Voltar para o menu ");
                        System.out.println("Digite a opcao na proxima linha: ");
                        try {
                            option = input.nextInt(); // Exception
                        } catch (InputMismatchException e1) {
                            option = 4;
                            input.nextLine();
                        }
                    } while (option != 2);
                }

                case 7 -> { // Imprimir dados de veiculos por tipo
                    option = 1;
                    do {
                        if (option == 1) {
                            Simulador.ClearScreen();
                            System.out.println("""
                                               Informe o tipo de veiculo:
                                               |1| Bicicleta
                                               |2| Carro Popular
                                               |3| Motocicleta
                                               |4| Ferrari
                                               """);
                            System.out.println("Digite a opcao na proxima linha: ");
                            try {
                                option = input.nextInt(); // Exception
                            } catch (InputMismatchException e1) {
                                option = 5;
                                input.nextLine();
                            }
                            switch (option) {
                                case 1 -> // Bicicleta
                                    Simulador.PrintAllVehicles_perType("Bicicleta");
                                case 2 -> // Carro Popular
                                    Simulador.PrintAllVehicles_perType("CarroPopular");
                                case 3 -> // Motocicleta
                                    Simulador.PrintAllVehicles_perType("Motocicleta");
                                case 4 -> // Ferrari
                                    Simulador.PrintAllVehicles_perType("Ferrari");
                                default -> {
                                    Simulador.ClearScreen();
                                    System.out.println("Opcao Invalida");
                                }
                            }
                        } else if (option != 2) {
                            Simulador.ClearScreen();
                            System.out.println("Opcao Invalida");
                        }

                        System.out.println("""
                                           Opcoes:
                                           |1| Imprimir novamente
                                           |2| Voltar para o menu
                                           """);
                        System.out.println("Digite a opcao na proxima linha: ");
                        try {
                            option = input.nextInt(); // Exception
                        } catch (InputMismatchException e1) {
                            option = 3;
                            input.nextLine();
                        }
                    } while (option != 2);
                }

                case 8 -> { // Esvaziar um pneu especifico
                    option = 1;
                    type_id = -1;
                    do {
                        if (option == 1 || (option == 2 && type_id == -1)) { // Esvaziar pneu
                            try {
                                Simulador.PrintAllVehicles(); // Exception
                                System.out.println("Informe o ID do veiculo: ");
                                type_id = input.nextInt(); // Exception
                                Simulador.EmptyTire(type_id); // Exception
                            } catch (InputMismatchException e1) {
                                input.nextLine();
                                type_id = -1;
                                Simulador.ClearScreen();
                                System.out.println("Input Invalido");
                            } catch (InvalidIDException e2) {
                                type_id = -1;
                                Simulador.ClearScreen();
                                System.out.println(e2);
                            } catch (VehicleArrayListIsEmptyException e3) {
                                Simulador.ClearScreen();
                                System.out.println(e3);
                            }
                        } else if (option == 2) { // Esvaziar outro pneu do mesmo veiculo
                            try {
                                Simulador.EmptyTire(type_id);
                            } catch (InvalidIDException e2) {
                                type_id = -1;
                                Simulador.ClearScreen();
                                System.out.println(e2);
                            }
                        } else if (option == 3) { // Imprimir dados de todos os veiculos
                            try {
                                Simulador.PrintAllVehicles();
                            } catch (VehicleArrayListIsEmptyException e1) {
                                Simulador.ClearScreen();
                                System.out.println(e1);
                            }
                        } else if (option != 4) {
                            Simulador.ClearScreen();
                            System.out.println("Opcao Invalida");
                        }

                        System.out.println("Opcoes: ");
                        System.out.println("|1| Esvaziar pneu de outro veiculo");
                        if(type_id != -1) {
                            String vehicleType=new String();
                            try {
                                vehicleType = Simulador.getVehicleType(type_id);
                            } catch (InvalidIDException e1) {};
                            System.out.printf("|2| Esvaziar pneu do mesmo veiculo(%s,ID:%d)\n",
                                    vehicleType, type_id);
                        }
                        else {
                            System.out.printf("|2| Esvaziar pneu do mesmo veiculo\n");
                        }
                        System.out.println("|3| Imprimir dados de todos os veiculos");
                        System.out.println("|4| Voltar para o menu");
                        System.out.println("Digite a opcao na proxima linha: ");
                        try {
                            option = input.nextInt(); // Exception
                        } catch (InputMismatchException e1) {
                            option = 5;
                            input.nextLine();
                        }
                    } while (option != 4);
                }

                case 9 -> { // Calibrar um pneu especifico
                    option = 1;
                    type_id = -1;
                    do {
                        if (option == 1 || (option == 2 && type_id == -1)) { // Calibrar pneu
                            try {
                                Simulador.PrintAllVehicles(); // Exception
                                System.out.println("Informe o ID do veiculo: ");
                                type_id = input.nextInt(); // Exception
                                Simulador.CalibrateTire(type_id); // Exception
                            } catch (InputMismatchException e1) {
                                input.nextLine();
                                type_id = -1;
                                Simulador.ClearScreen();
                                System.out.println("Input Invalido");
                            } catch (InvalidIDException e2) {
                                type_id = -1;
                                Simulador.ClearScreen();
                                System.out.println(e2);
                            } catch (VehicleArrayListIsEmptyException e3) {
                                Simulador.ClearScreen();
                                System.out.println(e3);
                            }
                        } else if (option == 2) { // Calibrar pneu do mesmo veiculo
                            try {
                                Simulador.CalibrateTire(type_id);
                            } catch (InvalidIDException e1) {
                                Simulador.ClearScreen();
                                System.out.println(e1);
                            }
                        } else if (option == 3) { // Imprimir dados de todos os veiculos
                            try {
                                Simulador.PrintAllVehicles();
                            } catch (VehicleArrayListIsEmptyException e1) {
                                Simulador.ClearScreen();
                                System.out.println(e1);
                            }
                        } else if (option != 4) {
                            Simulador.ClearScreen();
                            System.out.println("Opcao Invalida");
                        }

                        System.out.println("Opcoes: ");
                        System.out.println("|1| Calibrar pneu de outro veiculo ");
                        if(type_id != -1) {
                            String vehicleType=new String();
                            try {
                                vehicleType = Simulador.getVehicleType(type_id);
                            } catch (InvalidIDException e1) {}
                            System.out.printf("|2| Calibrar pneu do mesmo veiculo(%s,ID:%d)\n",
                                    vehicleType, type_id);
                        } else {
                            System.out.printf("|2| Calibrar pneu do mesmo veiculo\n");
                        }
                        System.out.println("|3| Imprimir dados de todos os veiculos ");
                        System.out.println("|4| Voltar para o menu ");
                        System.out.println("Digite a opcao na proxima linha: ");
                        try {
                            option = input.nextInt(); // Exception
                        } catch (InputMismatchException e1) {
                            option = 5;
                            input.nextLine();
                        }
                    } while (option != 4);
                }

                case 10 -> { // Calibrar todos os pneus
                    option = 1;
                    do {
                        if (option == 1) { // Calibra todos os pneus
                            try {
                                Simulador.PrintAllVehicles(); // Exception
                                System.out.println("Informe o ID do veiculo: ");
                                type_id = input.nextInt(); // Exception
                                Simulador.CalibrateAllTires(type_id); // Exception
                            } catch (InputMismatchException e1) {
                                input.nextLine();
                                Simulador.ClearScreen();
                                System.out.println("Input Invalido");
                            } catch (InvalidIDException | VehicleArrayListIsEmptyException e2) {
                                Simulador.ClearScreen();
                                System.out.println(e2);
                            }
                        } else if (option == 2) { // Imprimi dados de todos os veiculos
                            try {
                                Simulador.PrintAllVehicles();
                            } catch (VehicleArrayListIsEmptyException e1) {
                                Simulador.ClearScreen();
                                System.out.println(e1);
                            }
                        } else if (option != 3) {
                            Simulador.ClearScreen();
                            System.out.println("Opcao invalida");
                        }
                        System.out.println("""
                                           Opcoes:
                                           |1| Calibrar pneus de outro veiculo
                                           |2| Imprimir dados de todos os veiculos
                                           |3| Voltar para o menu
                                           """);
                        System.out.println("Digite a opcao na proxima linha: ");
                        try {
                            option = input.nextInt(); // Exception
                        } catch (InputMismatchException e1) {
                            option = 5;
                            input.nextLine();
                        }
                    } while (option != 3);
                }

                case 11 -> { // Imprimir pista de corrida
                    option = 1;
                    do {
                        if (option == 1) {
                            Simulador.ClearScreen();
                            try {
                                Simulador.PrintRoad(); // Exception
                            } catch (VehicleArrayListIsEmptyException e1) {
                                Simulador.ClearScreen();
                                System.out.println(e1);
                            }
                        } else if (option != 2) {
                            Simulador.ClearScreen();
                            System.out.println("Opcao Invalida");
                        }
                        System.out.println("""
                                           Opcoes:
                                           |1| Imprimir novamente
                                           |2| Voltar para o menu
                                           """);
                        System.out.println("Digite a opcao na proxima linha: ");
                        try {
                            option = input.nextInt(); // Exception
                        } catch (InputMismatchException e1) {
                            option = 5;
                            input.nextLine();
                        }
                    } while (option != 2);
                }
            }
            Simulador.ClearScreen();
        } while (menu != 12);
    }
}
