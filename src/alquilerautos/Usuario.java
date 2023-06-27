package alquilerautos;

import java.util.Collection;

//public abstract class Usuario implements Coleccionable<Usuario>{ 
public abstract class Usuario<T> implements Coleccionable<T, String> {

    private int dni;
    private String nombre;
    private String direccion;
    private int telefono;
    private String usuario;
    private String clave;

    public Usuario(int dni, String nombre, String direccion, int telefono, String usuario, String clave) {
        this.dni = dni;
        this.nombre = nombre;
        this.direccion = direccion;
        this.telefono = telefono;
        this.usuario = usuario;
        this.clave = clave;
    }

    public int getDni() {
        return dni;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public int getTelefono() {
        return telefono;
    }

    public String getUsuario() {
        return usuario;
    }

    public String getClave() {
        return clave;
    }

//agrego los setters de los atributos(direccion,telef,clave) que sí se pueden modificar posterior a crear un usuario.
//pero se usa el patron Factory con Recflection, se necesita todos los setters para instaciar usuiario concretos(adm, cliente, vendedor)
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public void setTelefono(int telefono) {
        this.telefono = telefono;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public void setDni(int dni) {
        this.dni = dni;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public boolean verificarUsuario(String usuario, String clave) {
        return (this.usuario.equals(usuario) && this.clave.equals(clave));
    }

    // este método lo implementa cada tipo de usuario de forma distinta, es "abstract"
    public abstract boolean ingresar();

    @Override
    public abstract void agregarSiEs(String tipoUsuario, Collection<T> listadoUsuarios);

    @Override
    public boolean verificarSiEsta(String usuario) {
        return (this.usuario == usuario);
    }

    @Override
    public abstract void mostrar();

    @Override // Usuario es abstracta, no se puede tener una listada de usuarios concretos.
    public abstract boolean buscar(String codigo);
}
