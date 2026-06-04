public class Paciente extends Persona {

    public Paciente(String id, String nombreCompleto) {
        super(id, nombreCompleto);
    }

    public String convertirACSV() {
        return id + "," + nombreCompleto;
    }

    public static Paciente crearDesdeCSV(String linea) {
        String[] datos = linea.split(",", -1);

        if (datos.length != 2) {
            throw new IllegalArgumentException("El registro de paciente no tiene el formato correcto.");
        }

        return new Paciente(datos[0], datos[1]);
    }

    @Override
    public void mostrarInformacion() {
        System.out.println("ID: " + id);
        System.out.println("Nombre completo: " + nombreCompleto);
    }
}
