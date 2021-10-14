
package com.libreriaSpring.demo.exceptions;

public class ErrorServicio extends Exception{
    
    /*Esta exception sirve para diferenciar los errores de nuestro código de los que ocurren en el sistema*/
    public ErrorServicio(String msn){
        super(msn);
    }
}
