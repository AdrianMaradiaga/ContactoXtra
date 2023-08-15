package hn.uth.contactoxtra.database;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
@Entity(tableName = "usuarios_table")
public class Usuario implements Parcelable {

    @NonNull
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "usuario_id")
    private long UsuarioId;

    @NonNull
    @ColumnInfo(name = "nombre_usuario")
    private String nombre;

    @NonNull
    @ColumnInfo(name = "apellido_usuario")
    private String apellido;


    @ColumnInfo(name = "correo_usuario")
    private String correo;

    @NonNull
    @ColumnInfo(name = "telefono_usuario")
    private String telefono;

    @ColumnInfo(name = "fecha_cumple")
    private String fechaCumple;

    @ColumnInfo(name = "latitud_usuario")
    private double latitudusuario;

    @ColumnInfo(name = "longitud_usuario")
    private double longitudusuario;

    @ColumnInfo(name = "latitud_trabajo")
    private double latitudTrabajo;

    @ColumnInfo(name = "longitud_trabajo")
    private double longitudTrabajo;

    public Usuario(@NonNull String nombre, @NonNull String apellido, String correo, @NonNull String telefono, String fechaCumple, double latitudusuario, double longitudusuario, double latitudTrabajo, double longitudTrabajo) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.correo = correo;
        this.telefono = telefono;
        this.fechaCumple = fechaCumple;
        this.latitudusuario = latitudusuario;
        this.longitudusuario = longitudusuario;
        this.latitudTrabajo = latitudTrabajo;
        this.longitudTrabajo = longitudTrabajo;
    }

    public long getUsuarioId() {
        return UsuarioId;
    }

    public void setUsuarioId(long usuarioId) {
        UsuarioId = usuarioId;
    }

    @NonNull
    public String getNombre() {
        return nombre;
    }

    public void setNombre(@NonNull String nombre) {
        this.nombre = nombre;
    }

    @NonNull
    public String getApellido() {
        return apellido;
    }

    public void setApellido(@NonNull String apellido) {
        this.apellido = apellido;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    @NonNull
    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(@NonNull String telefono) {
        this.telefono = telefono;
    }

    public String getFechaCumple() {
        return fechaCumple;
    }

    public void setFechaCumple(String fechaCumple) {
        this.fechaCumple = fechaCumple;
    }

    public double getLatitudusuario() {
        return latitudusuario;
    }

    public void setLatitudusuario(double latitudusuario) {
        this.latitudusuario = latitudusuario;
    }

    public double getLongitudusuario() {
        return longitudusuario;
    }

    public void setLongitudusuario(double longitudusuario) {
        this.longitudusuario = longitudusuario;
    }

    public double getLatitudTrabajo() {
        return latitudTrabajo;
    }

    public void setLatitudTrabajo(double latitudTrabajo) {
        this.latitudTrabajo = latitudTrabajo;
    }

    public double getLongitudTrabajo() {
        return longitudTrabajo;
    }

    public void setLongitudTrabajo(double longitudTrabajo) {
        this.longitudTrabajo = longitudTrabajo;
    }

    protected Usuario(Parcel in) {
        UsuarioId = in.readLong();
        nombre = in.readString();
        apellido = in.readString();
        correo = in.readString();
        telefono = in.readString();
        fechaCumple = in.readString();
        latitudusuario = in.readDouble();
        longitudusuario = in.readDouble();
        latitudTrabajo = in.readDouble();
        longitudTrabajo = in.readDouble();
    }

    public static final Creator<Usuario> CREATOR = new Creator<Usuario>() {
        @Override
        public Usuario createFromParcel(Parcel in) {
            return new Usuario(in);
        }

        @Override
        public Usuario[] newArray(int size) {
            return new Usuario[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeLong(UsuarioId);
        dest.writeString(nombre);
        dest.writeString(apellido);
        dest.writeString(correo);
        dest.writeString(telefono);
        dest.writeString(fechaCumple);
        dest.writeDouble(latitudusuario);
        dest.writeDouble(longitudusuario);
        dest.writeDouble(latitudTrabajo);
        dest.writeDouble(longitudTrabajo);
    }
}


