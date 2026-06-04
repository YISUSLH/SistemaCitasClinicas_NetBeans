public abstract class Persona {
    protected String id;
    protected String nombreCompleto;

    public Persona(String id, String nombreCompleto) {
        this.id = validarTexto(id, "identificador");
        this.nombreCompleto = validarTexto(nombreCompleto, "nombre completo");
    }

    public String getId() {
        return id;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    protected static String validarTexto(String valor, String campo) {
        if (valor == null || valor.trim().isEmpty()) {
            throw new IllegalArgumentException("El campo " + campo + " no puede estar vacío.");
        }

        if (valor.contains(",")) {
            throw new IllegalArgumentException("El campo " + campo + " no debe contener comas.");
        }

        return valor.trim();
    }

    public abstract void mostrarInformacion();
}
