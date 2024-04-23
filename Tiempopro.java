/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package meditplus;

import  java.util.Calendar;
import java.util.GregorianCalendar;
        
public class Tiempopro {
    Calendar fecha= new GregorianCalendar();
    String anho = Integer.toString(fecha.get(Calendar.YEAR));
    String mes = Integer.toString(fecha.get(Calendar.MONTH));
    String dia = Integer.toString(fecha.get(Calendar.DATE));
    
    String fechacomp= anho+"-"+mes+"-"+dia;
    String hora = Integer.toString(fecha.get(Calendar.HOUR_OF_DAY));
    String minuto = Integer.toString(fecha.get(Calendar.MINUTE));
    String horacomp=hora+":"+minuto;
    
}
