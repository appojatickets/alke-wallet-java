public class CuentaTest {

    public static void main(String[] args) {

        System.out.println("===== PRUEBAS UNITARIAS =====");

        // Crear cuenta
        Cuenta cuenta = new Cuenta(1000);

        // Test depósito
        cuenta.depositar(500);

        if (cuenta.getSaldo() == 1500) {

            System.out.println("TEST DEPÓSITO: CORRECTO");

        } else {

            System.out.println("TEST DEPÓSITO: FALLÓ");
        }

        // Test retiro
        cuenta.retirar(300);

        if (cuenta.getSaldo() == 1200) {

            System.out.println("TEST RETIRO: CORRECTO");

        } else {

            System.out.println("TEST RETIRO: FALLÓ");
        }

        // Test retiro inválido
        cuenta.retirar(5000);

        if (cuenta.getSaldo() == 1200) {

            System.out.println("TEST SALDO INSUFICIENTE: CORRECTO");

        } else {

            System.out.println("TEST SALDO INSUFICIENTE: FALLÓ");
        }
    }
}