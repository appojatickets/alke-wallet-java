import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        /*
        ================================
        VERSION ANTERIOR (RESPALDO)
        ================================

        System.out.println("===== ALKE WALLET =====");

        // Crear cuenta
        Cuenta cuenta = new Cuenta(100000);

        // Mostrar saldo inicial
        System.out.println("Saldo inicial: " + cuenta.getSaldo());

        // Depositar dinero
        cuenta.depositar(50000);

        // Mostrar saldo actualizado
        System.out.println("Saldo después del depósito: " + cuenta.getSaldo());

        // Retirar dinero
        cuenta.retirar(30000);

        // Mostrar saldo final
        System.out.println("Saldo final: " + cuenta.getSaldo());

        // Conversión CLP a USD
        ConversorMoneda conversorUSD = new ConversorMoneda(0.0011);

        double saldoUSD = conversorUSD.convertir(cuenta.getSaldo());

        System.out.println("Saldo convertido a USD: " + saldoUSD);

        ================================
        FIN RESPALDO
        ================================
        */


        // Scanner para leer datos ingresados por el usuario
        Scanner scanner = new Scanner(System.in);

        // Crear cuenta con saldo inicial
        Cuenta cuenta = new Cuenta(100000);

        int opcion;

        // Menú principal
        do {

            System.out.println("\n===== ALKE WALLET =====");

            System.out.println("1. Ver saldo");
            System.out.println("2. Depositar dinero");
            System.out.println("3. Retirar dinero");
            System.out.println("4. Convertir saldo a USD");
            System.out.println("5. Ver historial");
            System.out.println("6. Salir");

            System.out.print("Seleccione una opción: ");

            opcion = scanner.nextInt();

            switch (opcion) {

                // Mostrar saldo
                case 1:

                    System.out.println("Saldo actual: " + cuenta.getSaldo());

                    break;

                // Depositar dinero
                case 2:

                    System.out.print("Ingrese monto a depositar: ");

                    double deposito = scanner.nextDouble();

                    cuenta.depositar(deposito);

                    break;

                // Retirar dinero
                case 3:

                    System.out.print("Ingrese monto a retirar: ");

                    double retiro = scanner.nextDouble();

                    cuenta.retirar(retiro);

                    break;

                // Conversión de moneda
                case 4:

                    ConversorMoneda conversor = new ConversorMoneda(0.0011);

                    double saldoUSD = conversor.convertir(cuenta.getSaldo());

                    System.out.println("Saldo en USD: " + saldoUSD);

                    break;

               // Mostrar historial
                case 5:

                    cuenta.mostrarHistorial();

                    break;


                // Salir del programa
                case 6:

                    System.out.println("Gracias por usar AlkeWallet.");

                        break;

                // Opción inválida
                default:

                    System.out.println("Opción inválida.");
            }

        } while (opcion != 6);

        // Cerrar scanner
        scanner.close();
    }
}