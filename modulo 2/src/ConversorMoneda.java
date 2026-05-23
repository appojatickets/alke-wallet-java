public class ConversorMoneda implements IConversor {

    private double tasaCambio;

    // Constructor
    public ConversorMoneda(double tasaCambio) {
        this.tasaCambio = tasaCambio;
    }

    // Implementación de la interfaz
    @Override
    public double convertir(double monto) {

        return monto * tasaCambio;

    }
}