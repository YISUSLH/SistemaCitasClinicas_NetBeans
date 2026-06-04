public class Doctor extends Persona {
    private String especialidad;

    public Doctor(String id, String nombreCompleto, String especialidad) {
        super(id, nombreCompleto);
        this.especialidad = validarTexto(especialidad, "especialidad");
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public String convertirACSV() {
        return id + "," + nombreCompleto + "," + especialidad;
    }

    public static Doctor crearDesdeCSV(String linea) {
        String[] datos = linea.split(",", -1);

        if (datos.length != 3) {
            throw new IllegalArgumentException("El registro de doctor no tiene el formato correcto.");
        }

        return new Doctor(datos[0], datos[1], datos[2]);
    }

    @Override
    public void mostrarInformacion() {
        System.out.println("ID: " + id);
        System.out.println("Nombre completo: " + nombreCompleto);
        System.out.println("Especialidad: " + especialidad);
    }
}
