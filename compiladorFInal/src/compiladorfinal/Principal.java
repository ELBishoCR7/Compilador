/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package compiladorfinal;

import java.io.File;

/**
 *
 * @author gmva2
 */
public class Principal {
    public static void main(String[] args) {
        String ruta = "C:/Users/gmva2/OneDrive/Documentos/NetBeansProjects/compiladorFInal/src/compiladorfinal/Lexer.flex";
        generarLexer(ruta);
        
    }
    
    public static void generarLexer(String ruta)
    {
        File archivo = new File(ruta);
        JFlex.Main.generate(archivo);
    }
}
