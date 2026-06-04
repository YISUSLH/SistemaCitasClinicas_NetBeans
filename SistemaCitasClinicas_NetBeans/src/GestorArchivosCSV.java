import java.io.*;
import java.util.ArrayList;

public class GestorArchivosCSV {
    private File carpetaDB;
    private File archivoDoctores;
    private File archivoPacientes;
    private File archivoCitas;
    private File archivoAdministradores;

    public GestorArchivosCSV() {
        carpetaDB = new File("db");
        archivoDoctores = new File(carpetaDB, "doctores.csv");
        archivoPacientes = new File(carpetaDB, "pacientes.csv");
        archivoCitas = new File(carpetaDB, "citas.csv");
        archivoAdministradores = new File(carpetaDB, "administradores.csv");
    }

    public void prepararArchivos() {
        try {
            if (!carpetaDB.exists()) {
                carpetaDB.mkdir();
            }

            crearArchivoSiNoExiste(archivoDoctores, "idDoctor,nombreCompleto,especialidad");
            crearArchivoSiNoExiste(archivoPacientes, "idPaciente,nombreCompleto");
            crearArchivoSiNoExiste(archivoCitas, "idCita,fecha,hora,motivo,idDoctor,idPaciente");
            crearArchivoSiNoExiste(archivoAdministradores, "identificador,contrasena\nadmin,1234");

        } catch (IOException e) {
            System.out.println("Error al preparar archivos CSV: " + e.getMessage());
        }
    }

    private void crearArchivoSiNoExiste(File archivo, String encabezado) throws IOException {
        if (!archivo.exists()) {
            PrintWriter writer = new PrintWriter(new FileWriter(archivo));
            writer.println(encabezado);
            writer.close();
        }
    }

    public ArrayList<String> leerLineas(String nombreArchivo) {
        ArrayList<String> lineas = new ArrayList<>();
        File archivo = obtenerArchivo(nombreArchivo);

        try (BufferedReader reader = new BufferedReader(new FileReader(archivo))) {
            String linea;
            boolean primeraLinea = true;

            while ((linea = reader.readLine()) != null) {
                if (primeraLinea) {
                    primeraLinea = false;
                    continue;
                }

                if (!linea.trim().isEmpty()) {
                    lineas.add(linea);
                }
            }
        } catch (IOException e) {
            System.out.println("Error al leer archivo " + nombreArchivo + ": " + e.getMessage());
        }

        return lineas;
    }

    public void guardarLineas(String nombreArchivo, String encabezado, ArrayList<String> lineas) {
        File archivo = obtenerArchivo(nombreArchivo);

        try (PrintWriter writer = new PrintWriter(new FileWriter(archivo))) {
            writer.println(encabezado);

            for (String linea : lineas) {
                writer.println(linea);
            }
        } catch (IOException e) {
            System.out.println("Error al guardar archivo " + nombreArchivo + ": " + e.getMessage());
        }
    }

    private File obtenerArchivo(String nombreArchivo) {
        switch (nombreArchivo) {
            case "doctores":
                return archivoDoctores;
            case "pacientes":
                return archivoPacientes;
            case "citas":
                return archivoCitas;
            case "administradores":
                return archivoAdministradores;
            default:
                throw new IllegalArgumentException("Archivo no reconocido.");
        }
    }
}
