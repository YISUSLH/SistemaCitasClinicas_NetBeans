public class Administrador {
    private String identificador;
    private String contrasena;

    public Administrador(String identificador, String contrasena) {
        this.identificador = validar(identificador, "identificador");
        this.contrasena = validar(contrasena, "contrasena");
    }

    private String validar(String valor, String campo) {
        if (valor == null || valor.trim().isEmpty() || valor.contains(",")) {
            throw new IllegalArgumentException("El campo " + campo + " no es válido.");
        }

        return valor.trim();
    }

    public boolean validarAcceso(String identificadorIngresado, String contrasenaIngresada) {
        return identificador.equals(identificadorIngresado) && contrasena.equals(contrasenaIngresada);
    }

    public String convertirACSV() {
        return identificador + "," + contrasena;
    }

    public static Administrador crearDesdeCSV(String linea) {
        String[] datos = linea.split(",", -1);

        if (datos.length != 2) {
            throw new IllegalArgumentException("El registro de administrador no tiene el formato correcto.");
        }

        return new Administrador(datos[0], datos[1]);
    }
}
