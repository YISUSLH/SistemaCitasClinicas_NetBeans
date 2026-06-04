import java.util.Scanner;

public class Principal {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        SistemaCitas sistema = new SistemaCitas();

        System.out.println("======================================");
        System.out.println(" Sistema de Administración de Citas");
        System.out.println("======================================");

        boolean accesoPermitido = false;

        while (!accesoPermitido) {
            System.out.print("Usuario administrador: ");
            String usuario = scanner.nextLine();

            System.out.print("Contraseña: ");
            String contrasena = scanner.nextLine();

            if (sistema.validarAdministrador(usuario, contrasena)) {
                accesoPermitido = true;
                System.out.println("Acceso autorizado.");
            } else {
                System.out.println("Acceso denegado. Intente nuevamente.\n");
            }
        }

        int opcion = 0;

        do {
            try {
                System.out.println("\n--- MENÚ PRINCIPAL ---");
                System.out.println("1. Dar de alta doctor");
                System.out.println("2. Dar de alta paciente");
                System.out.println("3. Crear cita");
                System.out.println("4. Listar doctores");
                System.out.println("5. Listar pacientes");
                System.out.println("6. Listar citas");
                System.out.println("7. Salir");
                System.out.print("Selecciona una opción: ");

                opcion = Integer.parseInt(scanner.nextLine());

                switch (opcion) {
                    case 1:
                        sistema.registrarDoctor(scanner);
                        break;
                    case 2:
                        sistema.registrarPaciente(scanner);
                        break;
                    case 3:
                        sistema.crearCita(scanner);
                        break;
                    case 4:
                        sistema.listarDoctores();
                        break;
                    case 5:
                        sistema.listarPacientes();
                        break;
                    case 6:
                        sistema.listarCitas();
                        break;
                    case 7:
                        System.out.println("Saliendo del sistema...");
                        break;
                    default:
                        System.out.println("Opción inválida.");
                }

            } catch (NumberFormatException e) {
                System.out.println("Error: debe ingresar un número válido.");
            }

        } while (opcion != 7);

        scanner.close();
    }
}
