import java.util.ArrayList;
import java.util.Scanner;

public class SistemaCitas implements Almacenable {
    private ArrayList<Doctor> doctores;
    private ArrayList<Paciente> pacientes;
    private ArrayList<Cita> citas;
    private ArrayList<Administrador> administradores;
    private GestorArchivosCSV gestorCSV;

    public SistemaCitas() {
        doctores = new ArrayList<>();
        pacientes = new ArrayList<>();
        citas = new ArrayList<>();
        administradores = new ArrayList<>();

        gestorCSV = new GestorArchivosCSV();
        gestorCSV.prepararArchivos();
        cargarDatos();
    }

    @Override
    public void cargarDatos() {
        cargarAdministradores();
        cargarDoctores();
        cargarPacientes();
        cargarCitas();
    }

    private void cargarAdministradores() {
        for (String linea : gestorCSV.leerLineas("administradores")) {
            try {
                administradores.add(Administrador.crearDesdeCSV(linea));
            } catch (IllegalArgumentException e) {
                System.out.println("Administrador omitido: " + e.getMessage());
            }
        }
    }

    private void cargarDoctores() {
        for (String linea : gestorCSV.leerLineas("doctores")) {
            try {
                Doctor doctor = Doctor.crearDesdeCSV(linea);
                doctores.add(doctor);
            } catch (IllegalArgumentException e) {
                System.out.println("Doctor omitido: " + e.getMessage());
            }
        }
    }

    private void cargarPacientes() {
        for (String linea : gestorCSV.leerLineas("pacientes")) {
            try {
                Paciente paciente = Paciente.crearDesdeCSV(linea);
                pacientes.add(paciente);
            } catch (IllegalArgumentException e) {
                System.out.println("Paciente omitido: " + e.getMessage());
            }
        }
    }

    private void cargarCitas() {
        for (String linea : gestorCSV.leerLineas("citas")) {
            try {
                Cita cita = Cita.crearDesdeCSV(linea, this);
                citas.add(cita);
            } catch (IllegalArgumentException e) {
                System.out.println("Cita omitida: " + e.getMessage());
            }
        }
    }

    @Override
    public void guardarDatos() {
        ArrayList<String> lineasDoctores = new ArrayList<>();
        for (Doctor doctor : doctores) {
            lineasDoctores.add(doctor.convertirACSV());
        }

        ArrayList<String> lineasPacientes = new ArrayList<>();
        for (Paciente paciente : pacientes) {
            lineasPacientes.add(paciente.convertirACSV());
        }

        ArrayList<String> lineasCitas = new ArrayList<>();
        for (Cita cita : citas) {
            lineasCitas.add(cita.convertirACSV());
        }

        gestorCSV.guardarLineas("doctores", "idDoctor,nombreCompleto,especialidad", lineasDoctores);
        gestorCSV.guardarLineas("pacientes", "idPaciente,nombreCompleto", lineasPacientes);
        gestorCSV.guardarLineas("citas", "idCita,fecha,hora,motivo,idDoctor,idPaciente", lineasCitas);
    }

    public boolean validarAdministrador(String usuario, String contrasena) {
        for (Administrador administrador : administradores) {
            if (administrador.validarAcceso(usuario, contrasena)) {
                return true;
            }
        }

        return false;
    }

    public void registrarDoctor(Scanner scanner) {
        try {
            System.out.println("\n--- Registro de Doctor ---");

            System.out.print("Identificador único: ");
            String id = scanner.nextLine();

            if (buscarDoctor(id) != null) {
                System.out.println("Ya existe un doctor con ese identificador.");
                return;
            }

            System.out.print("Nombre completo: ");
            String nombre = scanner.nextLine();

            System.out.print("Especialidad: ");
            String especialidad = scanner.nextLine();

            Doctor doctor = new Doctor(id, nombre, especialidad);
            doctores.add(doctor);
            guardarDatos();

            System.out.println("Doctor registrado correctamente.");

        } catch (IllegalArgumentException e) {
            System.out.println("Error al registrar doctor: " + e.getMessage());
        }
    }

    public void registrarPaciente(Scanner scanner) {
        try {
            System.out.println("\n--- Registro de Paciente ---");

            System.out.print("Identificador único: ");
            String id = scanner.nextLine();

            if (buscarPaciente(id) != null) {
                System.out.println("Ya existe un paciente con ese identificador.");
                return;
            }

            System.out.print("Nombre completo: ");
            String nombre = scanner.nextLine();

            Paciente paciente = new Paciente(id, nombre);
            pacientes.add(paciente);
            guardarDatos();

            System.out.println("Paciente registrado correctamente.");

        } catch (IllegalArgumentException e) {
            System.out.println("Error al registrar paciente: " + e.getMessage());
        }
    }

    public void crearCita(Scanner scanner) {
        try {
            System.out.println("\n--- Crear Cita ---");

            System.out.print("Identificador único de la cita: ");
            String idCita = scanner.nextLine();

            if (buscarCita(idCita) != null) {
                System.out.println("Ya existe una cita con ese identificador.");
                return;
            }

            System.out.print("Fecha de la cita (dd/mm/aaaa): ");
            String fecha = scanner.nextLine();

            System.out.print("Hora de la cita (hh:mm): ");
            String hora = scanner.nextLine();

            System.out.print("Motivo de la cita: ");
            String motivo = scanner.nextLine();

            System.out.print("ID del doctor: ");
            String idDoctor = scanner.nextLine();

            System.out.print("ID del paciente: ");
            String idPaciente = scanner.nextLine();

            Doctor doctor = buscarDoctor(idDoctor);
            Paciente paciente = buscarPaciente(idPaciente);

            if (doctor == null) {
                System.out.println("No se encontró un doctor con ese ID.");
                return;
            }

            if (paciente == null) {
                System.out.println("No se encontró un paciente con ese ID.");
                return;
            }

            Cita cita = new Cita(idCita, fecha, hora, motivo, doctor, paciente);
            citas.add(cita);
            guardarDatos();

            System.out.println("Cita creada correctamente.");

        } catch (IllegalArgumentException e) {
            System.out.println("Error al crear cita: " + e.getMessage());
        }
    }

    public Doctor buscarDoctor(String id) {
        for (Doctor doctor : doctores) {
            if (doctor.getId().equals(id.trim())) {
                return doctor;
            }
        }

        return null;
    }

    public Paciente buscarPaciente(String id) {
        for (Paciente paciente : pacientes) {
            if (paciente.getId().equals(id.trim())) {
                return paciente;
            }
        }

        return null;
    }

    public Cita buscarCita(String idCita) {
        for (Cita cita : citas) {
            if (cita.getIdCita().equals(idCita.trim())) {
                return cita;
            }
        }

        return null;
    }

    public void listarDoctores() {
        System.out.println("\n--- Lista de Doctores ---");

        if (doctores.isEmpty()) {
            System.out.println("No hay doctores registrados.");
            return;
        }

        for (Doctor doctor : doctores) {
            doctor.mostrarInformacion();
            System.out.println("------------------------");
        }
    }

    public void listarPacientes() {
        System.out.println("\n--- Lista de Pacientes ---");

        if (pacientes.isEmpty()) {
            System.out.println("No hay pacientes registrados.");
            return;
        }

        for (Paciente paciente : pacientes) {
            paciente.mostrarInformacion();
            System.out.println("------------------------");
        }
    }

    public void listarCitas() {
        System.out.println("\n--- Lista de Citas ---");

        if (citas.isEmpty()) {
            System.out.println("No hay citas registradas.");
            return;
        }

        for (Cita cita : citas) {
            cita.mostrarInformacion();
            System.out.println("------------------------");
        }
    }
}
