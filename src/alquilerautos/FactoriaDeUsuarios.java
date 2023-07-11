package alquilerautos;

public class FactoriaDeUsuarios {

    private FactoriaDeUsuarios() {
    }

    private static FactoriaDeUsuarios fu = null;

    public static FactoriaDeUsuarios obtenerInstancia() {
        if (fu == null) {
            fu = new FactoriaDeUsuarios();
        }
        return fu;
    }

    public Usuario crearUsuario(String usuarioID, int dni, String nombre, String direccion, int telefono, String usuario, String clave) {
        Usuario u = null;

        try {
            u = (Usuario) Class.forName(fu.getClass().getPackage().getName() + "."
                    + usuarioID).getDeclaredConstructor().newInstance();
        } catch (Exception ex) {
            System.err.println(ex);
        }
        if (u == null) {
            throw new IllegalArgumentException(usuarioID);
        }
        u.setDni(dni);
        u.setNombre(nombre);
        u.setDireccion(direccion);
        u.setTelefono(telefono);
        u.setUsuario(usuario);
        u.setClave(clave);   //observar que hay setear codunico del cliente
        return u;
    }
}