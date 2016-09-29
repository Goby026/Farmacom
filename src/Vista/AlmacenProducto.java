package Vista;

public class AlmacenProducto {
    
    int idAlmacenProducto, idAlmacen, idProducto, cantidad;

    public AlmacenProducto() {
    }

    public AlmacenProducto(int idAlmacenProducto, int idAlmacen, int idProducto, int cantidad) {
        this.idAlmacenProducto = idAlmacenProducto;
        this.idAlmacen = idAlmacen;
        this.idProducto = idProducto;
        this.cantidad = cantidad;
    }

    public int getIdAlmacenProducto() {
        return idAlmacenProducto;
    }

    public void setIdAlmacenProducto(int idAlmacenProducto) {
        this.idAlmacenProducto = idAlmacenProducto;
    }

    public int getIdAlmacen() {
        return idAlmacen;
    }

    public void setIdAlmacen(int idAlmacen) {
        this.idAlmacen = idAlmacen;
    }

    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
    
    
    
}
