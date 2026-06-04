public class Cita {
    private String idCita;
    private String fecha;
    private String hora;
    private String motivo;
    private Doctor doctor;
    private Paciente paciente;

    public Cita(String idCita, String fecha, String hora, String motivo, Doctor doctor, Paciente paciente) {
        this.idCita = Persona.validarTexto(idCita, "identificador de cita");
        this.fecha = Persona.validarTexto(fecha, "fecha");
        this.hora = Persona.validarTexto(hora, "hora");
        this.motivo = Persona.validarTexto(motivo, "motivo");

        if (doctor == null) {
            throw new IllegalArgumentException("La cita debe tener un doctor asignado.");
        }

        if (paciente == null) {
            throw new IllegalArgumentException("La cita debe tener un paciente asignado.");
        }

        this.doctor = doctor;
        this.paciente = paciente;
    }

    public String getIdCita() {
        return idCita;
    }

    public String convertirACSV() {
        return idCita + "," + fecha + "," + hora + "," + motivo + "," + doctor.getId() + "," + paciente.getId();
    }

    public static Cita crearDesdeCSV(String linea, SistemaCitas sistema) {
        String[] datos = linea.split(",", -1);

        if (datos.length != 6) {
            throw new IllegalArgumentException("El registro de cita no tiene el formato correcto.");
        }

        Doctor doctor = sistema.buscarDoctor(datos[4]);
        Paciente paciente = sistema.buscarPaciente(datos[5]);

        if (doctor == null || paciente == null) {
            throw new IllegalArgumentException("No se pudo relacionar la cita con doctor o paciente.");
        }

        return new Cita(datos[0], datos[1], datos[2], datos[3], doctor, paciente);
    }

    public void mostrarInformacion() {
        System.out.println("ID de cita: " + idCita);
        System.out.println("Fecha: " + fecha);
        System.out.println("Hora: " + hora);
        System.out.println("Motivo: " + motivo);
        System.out.println("Doctor: " + doctor.getNombreCompleto());
        System.out.println("Paciente: " + paciente.getNombreCompleto());
    }
}
