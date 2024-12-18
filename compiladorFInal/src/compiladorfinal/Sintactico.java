/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package compiladorfinal;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author gmva2
 */
public class Sintactico {
    
    String[] filas;
    String[] columnas;
    String[][] tabla;
    
    Stack<String> pila = new Stack<String>();
    Stack<String> aux = new Stack<String>();
    String CadenaPila = "";
    String errores = "";
    
     private void Datos()
    {
        
        String excelFilePath = "C:\\Users\\gmva2\\OneDrive\\Escritorio\\nose.xlsx"; // Ruta del archivo Excel
        try {
            // Leer el archivo Excel
            FileInputStream fileInputStream = new FileInputStream(new File(excelFilePath));
            Workbook workbook = new XSSFWorkbook(fileInputStream);

            // Leer la primera hoja
            Sheet sheet = (Sheet) workbook.getSheetAt(0);
            
            // Determinar las dimensiones de la hoja
            
            int rows = sheet.getPhysicalNumberOfRows(); // Total de filas
            int cols = sheet.getRow(1).getPhysicalNumberOfCells(); // Total de columnas en la primera fila
            
            // Crear la matriz
            tabla = new String[rows-1][cols-1];

            
            //filas
             filas = new String[rows-1];
            for (int i = 1; i < rows; i++) {
                Row row = sheet.getRow(i);
            
                if(row!=null)
                {
                    Cell cell = row.getCell(0);
                    if (cell != null) {
                            filas[i-1] = cell.toString(); // Convertir la celda a String
                            
                        } 
                } 
            }
            for(int i = 0; i<filas.length; i++)
            {
                System.out.println(filas[i]);
            }
             
            //columnas
            columnas = new String[cols-1];
            for (int i = 1; i < cols; i++) {
                Row row = sheet.getRow(0);
            
                if(row!=null)
                {
                    Cell cell = row.getCell(i);
                    if (cell != null) {
                            columnas[i-1] = cell.toString(); // Convertir la celda a String
                        } 
                } 
            }
            
             for(int i = 0; i<columnas.length; i++)
            {
                System.out.println(columnas[i]);
            }
            
            
            // Llenar la matriz con los datos del Excel
            for (int i = 1; i < rows; i++) {
                Row row = sheet.getRow(i);
                if (row != null) {
                    for (int j = 1; j < cols; j++) {
                        Cell cell = row.getCell(j);
                        if (cell != null) {
                            tabla[i-1][j-1] = cell.toString();
                            
                             // Convertir la celda a String
                        } else {
                            tabla[i-1][j-1] = ""; // Celda vacía
                        }
                    }
                }
            }

            // Cerrar el libro
            workbook.close();
            fileInputStream.close();

            // Imprimir la matriz
            for (int i = 0; i < rows-1; i++) {
                for (int j = 0; j < cols-1; j++) {
                    if(tabla[i][j].equals(""))
                    {
                        System.out.print("''");
                    }
                    System.out.print("'"+tabla[i][j]+"'");
                }
                System.out.println();
            }
            System.out.println("indice 1: "+tabla[0][0]);
        } catch (IOException e) {
            System.err.println("Error al leer el archivo: " + e.getMessage());
        }
    }
    
     
     public String Anlisis(List<String> tokens)
     {
         int indice = 0;
         int fila;
         int columna;
         
         pila.push("$");
         pila.push("prog");
         
         while(indice<tokens.size())
         {
             
             
            
            String valor[] = tokens.get(indice).split(" ");
            String token = valor[0];
            int linea = Integer.parseInt(valor[1]);
            
            while(pila.peek().equals(token))
                    {
                        pila.pop();
                        indice++;
                    }
            
            fila = ObtenerIndice(filas,pila.peek());
            columna = ObtenerIndice(columnas,token);
             System.out.println("fil: "+pila.peek()+" "+fila);
             System.out.println("col: "+token+" "+columna);
            
            
            if(tabla[fila][columna].equals("error"))
            {
                if(tabla[fila][columna].equals(""))
                {
                    String Produccion[] = tabla[fila][columna].split(" ");
                    pila.pop();

                    for(String v: Produccion)
                    {
                        aux.push(v);
                    }

                    for(String v: Produccion)
                    {
                        pila.push(aux.pop());
                    }
                    CadenaPila+=pila+"\n";

                    while(pila.peek().equals(token))
                    {
                        pila.pop();
                        indice++;
                    }
                    
                    
                     System.out.println("pila: "+pila);
                }
                else
                {
                    pila.pop();
                }
            }
            else
            {
                //ERROR
                switch (pila.peek()) {
                    case "prog":
                        errores+= "Error sintactico falta palabra reservada program en la linea: "+linea+"\n";
                        break;
                    case "subroutine":
                        errores+= "Error sintactico no se encontro subroutine en la linea: "+linea+"\n";
                        break;
                    case "L","L'","R'","E'","T'":
                        errores+= "Error sintactico no se encontro operador en la linea: "+linea+"\n";
                        break;
                    case "F":
                        errores+= "Error sintactico no se encontro operando en la linea: "+linea+"\n";
                        break;    
                    default:
                        errores+= "Error sintactico no definido en la linea: "+linea+"\n";
                }
                indice++;
                
            }
            break; 
         }
         
         return CadenaPila;
     }
     
     public int ObtenerIndice(String vec[], String cadena)
     {
         int index = -1;
         for(int i =0; i<vec.length; i++)
         {
             if(cadena.equals(vec[i]))
             {
                 index=i;
                 break;
             }
         }
        return index;
     }
     
     
     public static void main(String[] args) {
        Sintactico sin = new Sintactico();
        sin.Datos();
        
        List<String> l = new ArrayList<>();
        
        l.add("program 1");
      /*  l.add("idp 1");
        l.add("subroutine  2");
        l.add("ids 2");
        l.add("( 2");
        l.add("integer 2");
        l.add("id 2");
        l.add(", 2");
        l.add("id 2");
        l.add(", 2");
        l.add("id 2");
        l.add(") 2");
        l.add("program 1");
        l.add("program 1");
        l.add("program 1");
        l.add("program 1");
        l.add("program 1");
        l.add("program 1");
        l.add("program 1");
        l.add("program 1");
        l.add("program 1");
        l.add("program 1");
        l.add("program 1");
        l.add("program 1");
        l.add("program 1");
        l.add("program 1");
        l.add("program 1");
*/
      sin.Anlisis(l);
    }
}
