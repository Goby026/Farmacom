
package Modelo;

/**
 *
 * @author Marce
 */
public class Caja {
    int idCaja;
    double montoApertura, montoCierre;
    String fecha, hora, observacion;

    public Caja() {
    }

    public Caja(int idCaja, double montoApertura, double montoCierre, String fecha, String hora, String observacion) {
        this.idCaja = idCaja;
        this.montoApertura = montoApertura;
        this.montoCierre = montoCierre;
        this.fecha = fecha;
        this.hora = hora;
        this.observacion = observacion;
    }

    public int getIdCaja() {
        return idCaja;
    }

    public void setIdCaja(int idCaja) {
        this.idCaja = idCaja;
    }

    public double getMontoApertura() {
        return montoApertura;
    }

    public void setMontoApertura(double montoApertura) {
        this.montoApertura = montoApertura;
    }

    public double getMontoCierre() {
        return montoCierre;
    }

    public void setMontoCierre(double montoCierre) {
        this.montoCierre = montoCierre;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }
    
    
}
