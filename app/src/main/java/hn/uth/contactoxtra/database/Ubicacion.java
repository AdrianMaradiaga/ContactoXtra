package hn.uth.contactoxtra.database;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "ubicacion_table")
public class Ubicacion implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "id")
    private long ubicacionId;

    @NonNull
    @ColumnInfo(name = "persona")
    private String persona;

    @NonNull
    @ColumnInfo(name = "categoria")
    private String categoria;

    @ColumnInfo(name = "latitud")
    private double latitud;

    @ColumnInfo(name = "longitud")
    private double longitud;

    public Ubicacion(@NonNull String persona, @NonNull String categoria, double latitud, double longitud) {
        this.persona = persona;
        this.categoria = categoria;
        this.latitud = latitud;
        this.longitud = longitud;
    }

    public long getUbicacionId() {
        return ubicacionId;
    }

    public void setUbicacionId(long ubicacionId) {
        this.ubicacionId = ubicacionId;
    }

    @NonNull
    public String getPersona() {
        return persona;
    }

    public void setPersona(@NonNull String persona) {
        this.persona = persona;
    }

    @NonNull
    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(@NonNull String categoria) {
        this.categoria = categoria;
    }

    public double getLatitud() {
        return latitud;
    }

    public void setLatitud(double latitud) {
        this.latitud = latitud;
    }

    public double getLongitud() {
        return longitud;
    }

    public void setLongitud(double longitud) {
        this.longitud = longitud;
    }

    protected Ubicacion(Parcel in) {
        ubicacionId = in.readLong();
        persona = in.readString();
        categoria = in.readString();
        latitud = in.readDouble();
        longitud = in.readDouble();
    }

    public static final Creator<Ubicacion> CREATOR = new Creator<Ubicacion>() {
        @Override
        public Ubicacion createFromParcel(Parcel in) {
            return new Ubicacion(in);
        }

        @Override
        public Ubicacion[] newArray(int size) {
            return new Ubicacion[size];
        }
    };

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(ubicacionId);
        dest.writeString(persona);
        dest.writeString(categoria);
        dest.writeDouble(latitud);
        dest.writeDouble(longitud);
    }

    @Override
    public int describeContents() {
        return 0;
    }
}
