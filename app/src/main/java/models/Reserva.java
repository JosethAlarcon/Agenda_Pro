package models;

public class Reserva {
    private String direccionInicio;
    private String direccionDestino;
    private String fecha;
    private String horario;
    private String metodoPago;

    public Reserva(String direccionInicio, String direccionDestino, String fecha, String horario, String metodoPago) {
        this.direccionInicio = direccionInicio;
        this.direccionDestino = direccionDestino;
        this.fecha = fecha;
        this.horario = horario;
        this.metodoPago = metodoPago;
    }

    // Getterss
    public String getDireccionInicio() {
        return direccionInicio;
    }

    public String getDireccionDestino() {
        return direccionDestino;
    }

    public String getFecha() {
        return fecha;
    }

    public String getHorario() {
        return horario;
    }

    public void setDireccionInicio(String direccionInicio) {
        this.direccionInicio = direccionInicio;
    }

    public void setDireccionDestino(String direccionDestino) {
        this.direccionDestino = direccionDestino;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }

    public void setMetodoPago(String metodoPago) {
        this.metodoPago = metodoPago;
    }

    public String getMetodoPago() {
        return metodoPago;
    }
}
