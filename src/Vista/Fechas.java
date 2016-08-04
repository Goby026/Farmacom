
package Vista;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 *
 * @author Gaby
 */
public class Fechas {
    
    // Suma los días recibidos a la fecha  
    public Date sumarRestarDiasFecha(Date fecha, int dias) {

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(fecha); // Configuramos la fecha que se recibe
        calendar.add(Calendar.DAY_OF_YEAR, dias);  // numero de días a añadir, o restar en caso de días<0

        return calendar.getTime(); // Devuelve el objeto Date con los nuevos días añadidos

    }
    
    public String fecha(){
        Calendar c = new GregorianCalendar();
        int dia = c.get(Calendar.DATE);
        int mes = c.get(Calendar.MONTH);
        int yy = c.get(Calendar.YEAR);
        String fecha = yy+"-"+mes+"-"+dia;
        return fecha;
    }
    
    public String fechaCadena(){
        String d=null,m=null,a=null;
        Calendar c = new GregorianCalendar();        
        int dia = c.get(Calendar.DATE);
        int mes = c.get(Calendar.MONTH)+1;
        int yy = c.get(Calendar.YEAR);
        
        switch(mes){
            case 1:
                m= "Enero";
                break;
            case 2:
                m= "Febrero";
                break;
            case 3:
                m= "Marzo";
                break;
            case 4:
                m= "Abril";
                break;
            case 5:
                m= "Mayo";
                break;
            case 6:
                m= "Junio";
                break;
            case 7:
                m = "Julio";
                break;
            case 8:
                m = "Agosto";
                break;
            case 9:
                m = "Setiembre";
                break;
            case 10:
                m = "Octubre";
                break;
            case 11:
                m = "Noviembre";
                break;
            case 12:
                m = "Diciembre";
                break;
        }
        String fecha = dia+" de "+m+" del "+yy;
        return fecha;
    }
    
    public String hora(){
        Calendar c = new GregorianCalendar();        
        int hora = c.get(Calendar.HOUR_OF_DAY);
        int min = c.get(Calendar.MINUTE);
        int seg = c.get(Calendar.SECOND);
        String h = hora+":"+min+":"+seg;
        return h;
    }

    
}
