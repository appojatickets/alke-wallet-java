import java.util.ArrayList;

public class Cuenta {

    // Saldo actual de la cuenta
    private double saldo;

    // Lista para guardar historial de movimientos
    private ArrayList<String> historial = new ArrayList<>();


    // Constructor
    public Cuenta(double saldoInicial) {

        this.saldo = saldoInicial;

        // Guardar saldo inicial en historial
        historial.add("Cuenta creada con saldo inicial: " + saldoInicial);
    }


    // Mostrar saldo
    public double getSaldo() {

        return saldo;
    }


    // Mostrar historial de movimientos
    public void mostrarHistorial() {

        System.out.println("\n===== HISTORIAL DE TRANSACCIONES =====");

        // Validar si el historial está vacío
        if (historial.isEmpty()) {

            System.out.println("No existen movimientos.");

        } else {

            // Recorrer historial
            for (String movimiento : historial) {

                System.out.println(movimiento);
            }
        }
    }


    // Depositar dinero
    public void depositar(double monto) {

        if (monto > 0) {

            saldo += monto;

            // Guardar movimiento
            historial.add("Depósito realizado: +" + monto);

            System.out.println("Depósito realizado correctamente.");

        } else {

            System.out.println("Monto inválido.");
        }
    }


    // Retirar dinero
    public void retirar(double monto) {

        if (monto <= saldo && monto > 0) {

            saldo -= monto;

            // Guardar movimiento
            historial.add("Retiro realizado: -" + monto);

            System.out.println("Retiro realizado correctamente.");

        } else {

            System.out.println("Saldo insuficiente o monto inválido.");
        }
    }



    /*
    ============================================
    VERSION ANTERIOR (RESPALDO)
    ============================================

    public class Cuenta {

        private double saldo;

        // Constructor
        public Cuenta(double saldoInicial) {
            this.saldo = saldoInicial;
        }

        // Mostrar saldo
        public double getSaldo() {
            return saldo;
        }

        // Depositar dinero
        public void depositar(double monto) {

            if (monto > 0) {
                saldo += monto;
                System.out.println("Depósito realizado correctamente.");
            } else {
                System.out.println("Monto inválido.");
            }
        }

        // Retirar dinero
        public void retirar(double monto) {

            if (monto <= saldo && monto > 0) {

                saldo -= monto;
                System.out.println("Retiro realizado correctamente.");

            } else {

                System.out.println("Saldo insuficiente o monto inválido.");
            }
        }
    }

    ============================================
    FIN RESPALDO
    ============================================
    */

}