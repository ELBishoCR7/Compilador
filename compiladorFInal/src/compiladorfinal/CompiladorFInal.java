/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package compiladorfinal;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
/**
 *
 * @author gmva2
 */
public class CompiladorFInal {

    /**
     * @param args the command line arguments
     */
    String[] filas;
    String[] columnas;
    String[][] tabla;
    String producciones[] = {
        "P' > P",        //0
        "P > Tipo id V", //1
        "P > A",        //2
        "P > Sentencias",   //3
        "P > Invocaciones", //4
        "Sentencias > Sentencia P",     //5
        "Sentencias > Ɛ",   //6
        "Sentencia > if ( R ) then Bloque else Bloque", //7
        "Sentencia > while ( R ) Bloque",   //8
        "Bloque > { P }",   //9
        "Invocaciones > print ( S ) ; P",   //10
        "Invocaciones > read ( id ) ; P",  //11
        "Tipo > int",   //12
        "Tipo > float", //13
        "Tipo > char",  //14
        "Tipo > string",    //15
        "V > , id V",   //16
        "V > ; P",  //17
        "A > id = S ; sigA",    //18
        "sigA > P", //19
        "sigA > Ɛ",  //NO ESTÁ EN LA TABLA  //20
        "R > E R'", //21
        "R' > < E", //22
        "R' > <= E",        //23
        "R' > > E", //24
        "R' > >= E",    //25
        "R' > == E",    //26
        "R' > != E",    //27
        "S > + E",  //28
        "S > - E",      //29
        "S > E",    //30
        "S > Z", //31
        "E > E + T", //32
        "E > E - T", //33
        "E > T", //34
        "T > T * F", //35
        "T > T / F", //36
        "T > F", //37
        "F > ( S )", //38
        "F > id", //39
        "F > num", //40
        "Z > cadena", //41
        "Z > caracter", //42
        "P > Ɛ" //43
        };
    
     String tipos[] = {"int","float","char","string"};
     boolean tablaSemanticaComparar[][] = 
                                            {{true, true, false,false},
                                             {true, true, false,false},
                                             {false, false, true,false},
                                             {false, false, false,true}};
     
     int tablaSemanticaOperaciones[][] = 
                                            {{0,1,-1,-1},
                                             {1,1,-1,-1},
                                             {-1,-1,1,1},
                                             {-1,-1,1,1}};
     
     boolean tablaSemanticaAsignacion[][] = 
                                            {{true, true, false,false},
                                             {true, true, false,false},
                                             {false, false, true,false},
                                             {true, true, true,true}};
     String entrada2 = "";
    Stack<String> pila = new Stack<>();
    Stack<Integer> pilaSemantica = new Stack<>();
    Stack<String> pilaOperadores = new Stack<>();
    Stack<String> pilaCodigoIntermedio = new Stack<>();
    
    Stack<String> aux = new Stack<>();
    String codigoIntermedio = "";
    List<String> variablesCodigoIntermedio = new ArrayList<>();
    int contarVariables = 0;
    String operacion = "";
    int contarEtiqWhile = 0;
    int contarEtiqIf = 0;
    
    boolean errorSemantico;
    long totalPuntosYComas ;
    String mnsjErrSemantico;
    int filaErrorSemantico;
    boolean noDeclarada = true;
    String error = "";
    String cadena2 = "";
    String cambiosPilaSemantica = "";
    String cambiosPilaOperadores = "";
    
    String control = "";
    String controlWhile = "";
     int contar = 0;
    int contarIf = 0;
    int contarWhile = 0;
    String entradaSalida = "";
    boolean bandera = false;
    String cadPrint = "";
    boolean vacia;
    boolean estaEnEtiqueta;
    boolean variablesInterior = false;
    
    String codvar = "";
    
    private void Datos()
    {
        
        String excelFilePath = "C:\\Users\\gmva2\\OneDrive\\Escritorio\\Libro 18.xlsx"; // Ruta del archivo Excel
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
                            tabla[i-1][j-1] = getCellValueAsString(cell);
                            
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
    
    private String getCellValueAsString(Cell cell) {
    switch (cell.getCellType()) {
        case STRING:
            return cell.getStringCellValue();
        case NUMERIC:
            if (cell.getNumericCellValue() == Math.floor(cell.getNumericCellValue())) {
                // Si el número es entero, formatearlo sin punto decimal
                return String.valueOf((int) cell.getNumericCellValue());
            } else {
                // Si el número tiene decimales, conservar el formato
                return String.valueOf(cell.getNumericCellValue());
            }
        case BOOLEAN:
            return String.valueOf(cell.getBooleanCellValue());
        case BLANK:
            return "";
        default:
            return cell.toString();
    }
    }
     public static void main(String[] args) {
    }
    
     
     int cont;
     List<String> values = new ArrayList<>();
     public String  evaluar(String[] entrada, List<Integer> cantidadTokensFila, List<String> valores)
    {
        values=valores;
        Datos();
        System.out.println("length entrada: "+entrada.length+" lenght valores: "+valores.size());
        valores.add("$");
        pila.push("$");
        pila.push("0");
        int columna = 0;
        int fila = 0;
         cont = 0;  
        String contenidoPila = pila.toString()+"\n";
        entrada2+=entrada[cont]+"\n";
        cadena2 = cadena2+(valores.get(cont)+"\n");
        System.out.println("VALORES LONGITUD "+valores.size()+" valores: "+valores+" valores "+cont+": "+valores.get(cont));
  
        while(true)
        {
            System.out.println("VIENEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEE: "+entrada[cont]);
            try {
                fila = Integer.parseInt(pila.peek());
                columna = indiceColumnas(entrada[cont], columnas);
            } catch (ArrayIndexOutOfBoundsException e) {
                System.out.println("CADENA NO ACEPTADA termino la cadena");
                Error(cont, cantidadTokensFila, 0);
                System.out.println("ERAQUIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIII3: "+valores.get(cont));
                break;
            }catch(NumberFormatException e)
            {
                //System.out.println("fila: "+pila.peek()+" columna: "+columna+" caracter: "+entrada[cont]);
                System.out.println("CADENA NO ACEPTADA cadena vacia");
                Error(cont, cantidadTokensFila, 0);
                System.out.println("ERAQUIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIII2: "+valores.get(cont));
                break;

            }
            
            
            
            try {
                //System.out.println("fila: "+fila+" columna: "+columna);
                int valor = Integer.parseInt(tabla[fila][columna]);
                pila.push(entrada[cont]);
                pila.push(valor+"");
                //System.out.println("ENTRADA[CONT]: "+entrada[cont]+" cont: "+cont);
                //System.out.println("tal: "+pila.peek()+" con tal: "+entrada[cont]+" da: "+valor);
                System.out.println("VALOR: "+valores.get(cont));
               if(entrada[cont].equals("int") || entrada[cont].equals("float")|| entrada[cont].equals("char") || entrada[cont].equals("string"))
               {
                   pilaCodigoIntermedio.add(valores.get(cont));
                   aux.add(valores.get(cont));
               }
               else if(entrada[cont].equals("id"))
               {
                   
                   if(!pilaCodigoIntermedio.isEmpty())
                   {
                      if(!pilaCodigoIntermedio.peek().equals(";"))
                        {
                            pilaCodigoIntermedio.add(valores.get(cont)+"");
                            aux.add(valores.get(cont));
                        } 
                   }
                   if(cont>3)
                   {
                       if(valores.get(cont-2).equals("read"))
                       {
                                if(!bandera)
                                    {
                                     codigoIntermedio(aux, entrada2);
                                     bandera = true;
                                    }
                                llenarPilaSemantica(valores.get(cont-1)); 
                                //contarVariables++;
                                //variablesCodigoIntermedio.add("var"+contarVariables+ "="+valores.get(cont)+";");
                                //codigoIntermedio+="var"+contarVariables+ "="+valores.get(cont)+";\n";
                                //aquisi
                                String tipo="";
                                for (int j = pilaCodigoIntermedio.size() - 1; j >= 0; j--) 
                                {
                                    if(pilaCodigoIntermedio.get(j).equals(valores.get(cont)))
                                    {
                                        while(!(pilaCodigoIntermedio.get(j).equals("float") || pilaCodigoIntermedio.get(j).equals("int") || pilaCodigoIntermedio.get(j).equals("char") || pilaCodigoIntermedio.get(j).equals("string")))
                                        {
                                            j--;
                                        }
                                        tipo = pilaCodigoIntermedio.get(j);
                                        break;
                                    }
                                }
                                
                                String especificador = "";
                                
                                switch(tipo)
                                {
                                    case "int":
                                        especificador = "%d";
                                    break;
                                    case "float":
                                        especificador = "%f";
                                    break;
                                    case "char":
                                        especificador = "%c";
                                    break;
                                    case "string":
                                        especificador = "%s";
                                    break;
                                }
                                
                                
                                if(!entradaSalida.isEmpty())
                                {
                                    //codigoIntermedio+=entradaSalida+"(\""+especificador+"\", &var"+contarVariables+");\n";
                                    codigoIntermedio+=entradaSalida+"(\""+especificador+"\", &"+valores.get(cont)+");\n";
                                    codvar+=entradaSalida+"(\""+especificador+"\", &"+valores.get(cont)+");\n";
                                    entradaSalida="";
                                }
                                
                                cambiosPilaSemantica+=pilaSemantica+"\n";
                       }
                   }
                   
                   
               }else if(entrada[cont].equals(";"))
               {
                   pilaCodigoIntermedio.add(valores.get(cont)+"");  
                   aux.add(valores.get(cont));
                   if(!llenarPilaOperadores(valores.get(cont),cont)) break;
                   cambiosPilaOperadores+=pilaOperadores+"\n";
                   
                   mostrarPilas();
               }
               else if(entrada[cont].equals("="))
               {
                   System.out.println("FUUUUEEEEE AAAA: "+valores.get(cont-1));
                   if(!bandera)
                   {
                    codigoIntermedio(aux, entrada2);
                    bandera = true;
                   }
                   llenarPilaSemantica(valores.get(cont-1));
                   cambiosPilaSemantica+=pilaSemantica+"\n";
                   
                   mostrarPilas();
               }
               else
               {
                   if(!llenarPilaOperadores(valores.get(cont),cont)) break;
                   cambiosPilaOperadores+=pilaOperadores+"\n";
                   //System.out.println("VIIINOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOO UN:: "+entrada[cont]);
                   mostrarPilas();
               }
               
               
               
              
                contenidoPila += pila.toString()+"\n";
                entrada2+=entrada[cont]+"\n";
                cadena2 = cadena2+(valores.get(cont)+"\n");
                cont++;
            } catch (NumberFormatException e) {
               // System.out.println("fila: "+fila+ " columna: "+columna);

                String valor = tabla[fila][columna];
                
                if(!valor.equals("P0"))
                {
                    if(!valor.equals(""))
                    {
                        //System.out.println("VALOR: "+valor);
                       // System.out.println("PRODUCCION: "+valor.substring(1));
                        int indiceProduccion = Integer.parseInt(valor.substring(1));
                        
                        if(!producciones[indiceProduccion].split(" ")[2].equals("Ɛ"))
                        {
                            for(int i = 2; i<producciones[indiceProduccion].split(" ").length; i++)
                            {
                                pila.pop();
                                pila.pop();

                            }
                        }else
                        {
                            System.out.println("VACIAAAAAA: "+producciones[indiceProduccion].split(" ")[2]);
                        }
                        
                        System.out.println("VACIAAAAAA: "+producciones[indiceProduccion].split(" ")[2]);
                        fila = Integer.parseInt(pila.peek());
                        //System.out.println("fila: "+fila);
                        String terminalIdNum = producciones[indiceProduccion].split(" ")[2];
                        
                        // Contar el número total de puntos y comas en la pila
                        totalPuntosYComas = pilaCodigoIntermedio.stream().filter(elemento -> elemento.equals(";")).count();
                        // Eliminar todos los puntos y comas, excepto el último
                        pilaCodigoIntermedio.removeIf(elemento -> elemento.equals(";") && totalPuntosYComas-- > 1);

                        if(terminalIdNum.equals("id"))
                        {
                            
                            
                            if(producciones[indiceProduccion].split(" ")[0].equals("A"))
                            {
                                
                                
                                
                                filaErrorSemantico=cont;
                                while (true) {     
                                    if(!valores.get(filaErrorSemantico).equals("="))
                                    {
                                        filaErrorSemantico--;
                                    }else
                                    {
                                        filaErrorSemantico--;
                                        //System.out.println("VALOR DE IDEN A=S; "+valores.get(filaErrorSemantico--));
                                        
                                        HashSet<String> aux = new HashSet<>();
                                       
                                        
                                        //error+=valores.get(filaErrorSemantico)+"\n ";
                                        
                                         errorSemantico = false;
                                          noDeclarada=false;
                                        // Recorrer la lista y verificar si ya existe el elemento
                                        for (String elemento : pilaCodigoIntermedio) {
                                            
                                             mnsjErrSemantico = "";
                                            if (!aux.add(elemento) && !(elemento.equals("int")) && !(elemento.equals("char"))&& !(elemento.equals("float")) && !(elemento.equals("string"))) { // add() devuelve false si el elemento ya está en el conjunto
                                                
                                                mnsjErrSemantico="variable duplicada: " + elemento;
                                                errorSemantico = true;
                                                break;
                                            }else if(valores.get(filaErrorSemantico).equals(elemento))
                                            {
                                                System.out.println("VARIABLE: "+valores.get(filaErrorSemantico)+" ELEMENTO: "+elemento);
                                                noDeclarada=true;
                                            }
                                            if(!noDeclarada)
                                            {
                                                mnsjErrSemantico="variable no declarada: "+valores.get(filaErrorSemantico); 
                                            }
                                        }
                                        break;
                                    }
                                    
                                }
                                
                            }
                            else
                            { 
                               
                                System.out.println("FUE FFFFFFF: "+valores.get(cont-1));
                                
                                
                                String val = "";
                                if(!entradaSalida.isEmpty())
                                {
                                     if(contarVariables<0)
                                        {
                                            contarVariables=0;
                                        }
                                     
                                     
                                    String tipo="";
                                    boolean ban = false;
                                    
                                    String code[] = codigoIntermedio.split("\n");
                                    for(int i = 0; i<code.length;i++)
                                        {
                                            Pattern pattern;
                                            if(contarVariables<1)
                                            {
                                                 pattern = Pattern.compile("var"+(contarVariables+1)+"( )*=( )*[a-zA-Z0-9_$]+;");

                                            }else
                                            {
                                                 pattern = Pattern.compile("var"+contarVariables);

                                            }
                                            Matcher matcher = pattern.matcher(code[i]);
                                            int primero = 0;
                                            int segundo = 0;
                                            // Buscar todas las coincidencias
                                            while (matcher.find()) {
                                                System.out.println("Se encontró el patrón \"" + "var"+(contarVariables+1) + "\" en: " + code[i]+" en el indice "+i+"  ..............................................................................");
                                                
                                                for(int f = 0; f<code[i].length();f++)
                                                {
                                                    if(code[i].charAt(f)=='=')
                                                    {
                                                        primero = f;
                                                    }
                                                    else if(code[i].charAt(f)==';')
                                                    {
                                                        segundo = f;
                                                        ban = true;
                                                        break;
                                                    }
                                                    if(ban)
                                                    {
                                                        break;
                                                    }
                                                }
                                            }
                                            if(ban)
                                            {
                                                val = code[i].substring(primero+1,code[i].length()-1);
                                                break;
                                            }
                                            
                                        }
                                    
                                    
                                    
                                    for (int j = pilaCodigoIntermedio.size() - 1; j >= 0; j--) 
                                    {
                                        
                                        
                                        
                                        System.out.println("VALOOOOOOOOOOOOOOOOOOOOOOOOOORRRRRRRRRREEEEEEEEEEEE: "+val);
                                        if(pilaCodigoIntermedio.get(j).equals(val))
                                        {
                                            while(!(pilaCodigoIntermedio.get(j).equals("float") || pilaCodigoIntermedio.get(j).equals("int") || pilaCodigoIntermedio.get(j).equals("char") || pilaCodigoIntermedio.get(j).equals("string")))
                                            {
                                                j--;
                                            }
                                            tipo = pilaCodigoIntermedio.get(j);
                                            System.out.println("Tipooooooo: "+tipo);
                                            break;
                                        }
                                    }

                                    String especificador = "";

                                    switch(tipo)
                                    {
                                        case "int":
                                            especificador = "%d";
                                        break;
                                        case "float":
                                            especificador = "%f";
                                        break;
                                        case "char":
                                            especificador = "%c";
                                        break;
                                        case "string":
                                            especificador = "%s";
                                        break;
                                    }
                                    
                                    
                                    
                                    
                                    if(contarVariables<1)
                                    {
                                       
                                        codigoIntermedio+=entradaSalida+"(\""+especificador+"\", var"+(contarVariables+1)+");\n";
                                        codvar+=entradaSalida+"(\""+especificador+"\", var"+(contarVariables+1)+");\n";
                                    }else
                                    {
                                        codigoIntermedio+=entradaSalida+"(\""+especificador+"\", var"+contarVariables+");\n";
                                        codvar+=entradaSalida+"(\""+especificador+"\", var"+contarVariables+");\n";
                                    }
                                    
                                    entradaSalida="";
                                }else
                                {
                                    llenarPilaSemantica(valores.get(cont-1)); 
                                    
                                  /*if(!estaEnEtiqueta)
                                    {
                                        contarVariables++;
                                    }*/
                                     
                                    contarVariables++;
                                   /* if(contarVariables==0)
                                    {
                                        contarVariables++;
                                    }*/
                                   if(contarVariables<1)
                                   {
                                       contarVariables=1;
                                   }
                                    System.out.println("VARRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRR: en nose"+contarVariables);
                                    variablesCodigoIntermedio.add("var"+contarVariables+"="+valores.get(cont-1)+";");
                                   if(controlWhile.isEmpty() && control.isEmpty())
                                   {
                                    codigoIntermedio+="var"+contarVariables+ "="+valores.get(cont-1)+";\n";
                                    codvar+="var"+contarVariables+ "="+valores.get(cont-1)+";\n";
                                    //desconozco
                                    }
                                   if(variablesInterior)
                                   {
                                       
                                       //contarVariables++;
                                       codigoIntermedio+="var"+contarVariables+ "="+valores.get(cont-1)+";\n";
                                       codvar+="var"+contarVariables+ "="+valores.get(cont-1)+";\n";
                                   }
                                   

                                
                                    
                                }
                                
                                
                                
                                
                                cambiosPilaSemantica+=pilaSemantica+"\n";
                                mostrarPilas();
                                HashSet<String> aux = new HashSet<>();
                                //error+=valores.get(filaErrorSemantico)+"\n ";
                                noDeclarada=false;
                                // Recorrer la lista y verificar si ya existe el elemento
                                for (String elemento : pilaCodigoIntermedio) {
                                    mnsjErrSemantico = "";
                                    
                                    if(valores.get(cont-1).equals(elemento))
                                    {
                                        System.out.println("VARIABLE: "+valores.get(cont-1)+" ELEMENTO: "+elemento);
                                        noDeclarada=true;
                                    }
                                    
                                    if(!noDeclarada)
                                    {
                                        mnsjErrSemantico="variable no declarada: "+valores.get(cont-1); 
                                    }
                                }  
                                System.out.println("valor en id: "+valores.get(cont-1)+" cont: "+(cont-1));
                               
                                
                            }
                            
                        }
                        else if(terminalIdNum.equals("num")){
                            System.out.println("valor en num: "+valores.get(cont-1)+" cont: "+(cont-1)); 
                            
                            pilaSemantica.add(0);
                            contarVariables++;                                 
                            System.out.println("VARRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRR: en num"+contarVariables);

                            
                            variablesCodigoIntermedio.add("var"+contarVariables+ "="+valores.get(cont-1)+";");
                            
                            
                            if(!estaEnEtiqueta)
                            {
                                if(variablesInterior)
                                {
                                    //contarVariables++;
                                    codigoIntermedio+="var"+contarVariables+ "="+valores.get(cont-1)+";\n";

                                }else
                                {
                                    codigoIntermedio+="var"+contarVariables+ "="+valores.get(cont-1)+";\n";
                                }

                            }
                            
                            codvar+="var"+contarVariables+ "="+valores.get(cont-1)+";\n";
                            if(!entradaSalida.isEmpty())
                            {
                                codigoIntermedio+=entradaSalida+"("+"var"+contarVariables+");\n";
                                codvar+=entradaSalida+"("+"var"+contarVariables+");\n";
                                entradaSalida="";
                            }
                            cambiosPilaSemantica+=pilaSemantica+"\n";
                        }else if(terminalIdNum.equals("cadena"))
                        {
                            System.err.println(valores.get(cont-3));
                           if(!valores.get(cont-3).equals("print"))
                           {
                               pilaSemantica.add(3);
                           }else
                           {
                               cadPrint=valores.get(cont-1);
                               if(!bandera)
                               {
                                   codigoIntermedio(aux, valor);
                                   bandera = true;
                               }
                               
                               if(!entradaSalida.isEmpty())
                                {
                                    codigoIntermedio+=entradaSalida+"("+cadPrint+");\n";
                                    codvar+=entradaSalida+"("+cadPrint+");\n";
                                    entradaSalida="";
                                }
                            
                               
                           }
                            
                        }
                        if (errorSemantico) 
                        {
                            System.out.println("se encontraron elementos duplicados.");
                            error+="Error semantico:\n"+mnsjErrSemantico;
                            break;
                        }else if(!noDeclarada)
                        {
                            error+="Error semantico:\n"+mnsjErrSemantico;
                            break;
                        }
                        
                        
                        System.out.println("produccion: "+producciones[indiceProduccion]);
                        System.out.println("produccion cortada: "+producciones[indiceProduccion].split(" ")[0]);
                        System.out.println("produccion terminal: "+producciones[indiceProduccion].split(" ")[2]);
                        System.out.println("");
                        columna = indiceColumnas(producciones[indiceProduccion].split(" ")[0], columnas);
                        pila.push(producciones[indiceProduccion].split(" ")[0]);
                       // System.out.println("fila: "+fila+" columna: "+columna);
                        pila.push(tabla[fila][columna]);
                        contenidoPila += pila.toString()+"\n";
                        entrada2+=entrada[cont]+"\n";
                        cadena2 = cadena2+(valores.get(cont)+"\n");
                    }
                    else
                    {
                        System.out.println("CADENA NO ACEPTADA lugar vacio");
                        
                        Error(cont, cantidadTokensFila, 0);
                        System.out.println("ERAQUIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIII0: "+"fila: "+fila+" columna: "+columna+" entrada "+entrada[cont]);
                        System.out.println("Error: "+error);
                        break; 
                    }
                    
                }
                else
                {
                   System.out.println("CADENA ACEPTADA");
                    break;
                }
            }
        }
        //System.out.println("CONTENIDO PILA:\n"+contenidoPila);
        //System.out.println("CANTIDAD TOKENS CON CONT:\n"+cont);
        System.out.println("PILA antes: "+pilaCodigoIntermedio);
        pilaCodigoIntermedio.removeIf(elemento -> elemento.equals(";"));
        System.out.println("PILA despues: "+pilaCodigoIntermedio);
        System.out.println("PILA SEMANTICA: "+pilaSemantica);
        
        System.out.println("PILA SEMANTICA COMPLETA:");
        System.out.println(cambiosPilaSemantica);
        System.out.println("PILA OPERADORES COMPLETA: "+pilaOperadores.size());
        System.out.println(cambiosPilaOperadores);
        
        for (String elemento : pilaCodigoIntermedio) {
            System.out.println(elemento);
        }
        System.out.println("ENTRADA:");
        System.out.println(entrada2);
        System.out.println("Valores:");
        System.out.println(cadena2);
        System.out.println("PILA: ");
        System.out.println(contenidoPila);
        System.out.println("codigo intermedio: "+codigoIntermedio);
                System.out.println("CODIGO INTERMEDIOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOO"+codigoIntermedio);
        tipoVar();
        System.out.println("CADENA DEL PRINT: "+cadPrint);
        System.out.println("Entrada 2: "+entrada2);
        for(String e: pilaCodigoIntermedio)
        {
            System.out.println("elemento: "+e);
        }
        System.out.println("codvar:");
        System.out.println(codvar);
        return contenidoPila;
    }
    
    private int indiceColumnas(String caracter, String[] columnas)
    {
        //System.out.println("caracter: "+caracter);
        int indice = -1;
        //System.out.println("caracter: "+caracter);
        for(int j = 0; j<columnas.length; j++)
        {
            //System.out.println("J: "+j+ "   caracter: "+caracter+"    Columna:   "+columnas[j]);
            if(caracter.equals(columnas[j]))
            {
                indice = j;
               // System.out.println("##caracter: "+caracter+"   columna: "+columnas[j]);
                break;
            }
        }
        
        return indice;
    }
    
    private void Error(int cont, List<Integer> cantidadTokensFila, int num)
    {
      
        int g = 1;
        //for (Integer elemento : cantidadTokensFila) {
        for(int i = 0; i <cantidadTokensFila.size(); i++)
        {
            System.out.println("CONT: "+cont+" CANTRIDAD: "+cantidadTokensFila);
            if(cont >= cantidadTokensFila.get(i) && cont <= cantidadTokensFila.get(i+1))
            {
                error+="error sintactico en la fila: "+(i+1);
                break;
            }
            g++;
           
                   
        }
    }
    
  
    public String getError()
    {
        System.out.println("ERROR EN GET ERROR: "+error);
        return error;
    }
    
    public void llenarPilaSemantica(String id)
    {
        int indiceId = pilaCodigoIntermedio.indexOf(id);
        
        // Recorre la pila hacia la izquierda, buscando el primer tipo encontrado
        for (int i = indiceId - 1; i >= 0; i--) {
            String elemento = pilaCodigoIntermedio.get(i);
            // Comprueba si el elemento es un tipo válido
            if (elemento.equals("int") || elemento.equals("char") || elemento.equals("float") || elemento.equals("string")) {
                System.out.println(id + ", " + elemento);
                switch (elemento) {
                    case "int":
                        pilaSemantica.add(0);
                        break;
                    case "float":
                        pilaSemantica.add(1);
                        break;
                    case "char":
                        pilaSemantica.add(2);
                        break;
                    case "string":
                        pilaSemantica.add(3);
                        break;
                    default:
                        throw new AssertionError();
                }
                break;
            }
        }

        // Si no se encontró un tipo a la izquierda, retorna un mensaje
        System.out.println("Tipo no encontrado a la izquierda de " + id);
    }
    
    public void mostrarPilas()
    {
        System.out.println("PILA SEMANTICA COMPLETA:");
        System.out.println(cambiosPilaSemantica);
        System.out.println("PILA OPERADORES COMPLETA:");
        System.out.println(cambiosPilaOperadores);
    }
    String pilop = "";
    public boolean llenarPilaOperadores(String operador, int indice)
    {
        System.out.println("OPERADORRRRRR>: "+operador);
        pilop+=operador+"\n";
        switch (operador) {
            case "+":
                if(!pilaOperadores.isEmpty()){
                    while(pilaOperadores.peek().equals("+") || pilaOperadores.peek().equals("-") || pilaOperadores.peek().equals("/") || pilaOperadores.peek().equals("*"))
                    {
                        operacion=" "+pilaOperadores.peek()+" ";
                        if(!operacionSemantica()) return false;
                        if(pilaOperadores.isEmpty()) break;
                        
                    }
                    pilaOperadores.push(operador);
                }
                else
                {
                    pilaOperadores.push(operador);
                }
                
                break;
            case "-":
                if(!pilaOperadores.isEmpty()){
                   while(pilaOperadores.peek().equals("+") || pilaOperadores.peek().equals("-") || pilaOperadores.peek().equals("/") || pilaOperadores.peek().equals("*"))
                    {
                        operacion=" "+pilaOperadores.peek()+" ";
                        if(!operacionSemantica()) return false;
                        if(pilaOperadores.isEmpty()) break;
                    }
                    pilaOperadores.push(operador);
                }
                else
                {
                    pilaOperadores.push(operador);
                }
                break;
            case "/":
                if(!pilaOperadores.isEmpty()){
                   while(pilaOperadores.peek().equals("/") || pilaOperadores.peek().equals("*"))
                    {
                        operacion=" "+pilaOperadores.peek()+" ";
                        if(!operacionSemantica()) return false;
                        if(pilaOperadores.isEmpty()) break;
                    }
                    pilaOperadores.push(operador);
                }
                else
                {
                    pilaOperadores.push(operador);
                }
                break;
            case "*":
                if(!pilaOperadores.isEmpty()){
                   while(pilaOperadores.peek().equals("/") || pilaOperadores.peek().equals("*"))
                    {
                        operacion=" "+pilaOperadores.peek()+" ";
                        if(!operacionSemantica()) return false;
                        if(pilaOperadores.isEmpty()) break;
                    }
                    pilaOperadores.push(operador);
                }
                else
                {
                    pilaOperadores.push(operador);
                }
                break;
            case "(":
                pilaOperadores.push("(");
                break;
            case ")":
                if(!pilaOperadores.isEmpty())
                {
                while(!pilaOperadores.peek().equals("("))
                {
                    operacion=" "+pilaOperadores.peek()+" ";
                    if(!operacionSemantica()) return false;
                }
                pilaOperadores.pop();
                //pilaOperadores.pop();
                }
                break;
            case ";":
                 vacia=false;
                if(!pilaOperadores.isEmpty())
                {
                    while(!pilaOperadores.isEmpty())
                    {
                        operacion=" "+pilaOperadores.peek()+" ";
                        if(!operacionSemantica()) return false;
                    }
                
                }else
                {
                    vacia=true;
                }
                
                System.out.println("PILA SEMANTICA DESPUES DE ; "+pilaSemantica+" pila semantica sixe: "+pilaSemantica.size());
                
                if(pilaSemantica.size()==2){
                    System.out.println("UAJJUAUAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAUUUUAUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUA"+pilaSemantica.get(1)+" "+pilaSemantica.get(0));
                if(!tablaSemanticaAsignacion[pilaSemantica.pop()][pilaSemantica.pop()])
                {
                    error+="Error semantico tipos imcompatibles en asignación";
                    System.out.println("Error semantico tipos imcompatibles en asignación \n");
                    return false;
                }
                }
                
                
                            
                
                
                
                if(!vacia)
                {
                    contarVariables=0;
                }
                break;
                case "<",">","<=",">=","!=","==":
                if(!pilaOperadores.isEmpty()){
                    while(pilaOperadores.peek().equals("+") || pilaOperadores.peek().equals("-") || pilaOperadores.peek().equals("/") || pilaOperadores.peek().equals("*"))
                    {
                        operacion=" "+pilaOperadores.peek()+" ";
                        if(!operacionSemantica()) return false;
                        if(pilaOperadores.isEmpty()) break;
                        
                    }
                    pilaOperadores.push(operador);
                }
                else
                {
                    pilaOperadores.push(operador);
                }
                
                break;
                    case "if":
                    control="if";
                    //codigoIntermedio+=control+"\n";
                    contarIf=1;
                    contarEtiqIf++;
                    estaEnEtiqueta=true;
                break;
                case "while":
                    estaEnEtiqueta=true;
                    codigoIntermedio+="EtqWhile"+(contarEtiqWhile+1)+":\n";
                    codvar+="EtqWhile"+(contarEtiqWhile+1)+":\n";
                    //control="while";
                    controlWhile = "while";
                    contarWhile=1;
                    //codigoIntermedio+=control+"\n";
                    contarEtiqWhile++;

                break;
                case "then":
                    codigoIntermedio+=" goto EtqElse"+contarEtiqIf+";\n";
                    codvar+=" goto EtqElse"+contarEtiqIf+";\n";
                break;
                case "{":
                    
                   
                    if(control.equals("if"))
                    {
                        contar++;
                        if(contar==3)
                            {
                            codigoIntermedio+="EtqElse"+contarEtiqIf+":\n";
                            codvar+="EtqElse"+contarEtiqIf+":\n";
                            }
                    }
                    if(controlWhile.equals("while"))
                    {
                        
                        codigoIntermedio+="goto EtqEndWhile"+contarEtiqWhile+";\n";
                        codvar+="goto EtqEndWhile"+contarEtiqWhile+";\n";
                        
                        estaEnEtiqueta=false;
                        
                        
                        
                        
                    }
                
                break;
                case "}":
                    
                    
                if(control.equals("if"))
                    {
                        
                        contar++;
                        if(contar%2==0 && contar>2)
                            {
                                estaEnEtiqueta=false;
                                codigoIntermedio+="EtqEndIf"+contarEtiqIf+":\n";
                                codvar+="EtqEndIf"+contarEtiqIf+":\n";
                                contar = 0;
                                control="";
                            }
                            else if(contar == 2)
                            {
                                codigoIntermedio+="goto EtqEndIf"+contarEtiqIf+";\n";
                                codvar+="goto EtqEndIf"+contarEtiqIf+";\n";
                            }
                    }
                    if(controlWhile.equals("while"))
                    {
                        String code[] = codvar.split("\n");
                        boolean ban=false;
                        String val="";
                        String val1="";
                        String val2="";
                        for(int i = 0; i<code.length;i++)
                                        {
                                            Pattern pattern;
                                            /*if(contarVariables<1)
                                            {
                                                 pattern = Pattern.compile("vc"+(contarEtiqWhile+1)+"( )*=( )*[a-zA-Z0-9_$]+;");

                                            }else
                                            {
                                                 pattern = Pattern.compile("vc"+contarEtiqWhile);

                                            }*/
                                            pattern = Pattern.compile("vc"+contarEtiqWhile+"=");
                                            Matcher matcher = pattern.matcher(code[i]);
                                            int primero = 0;
                                            int segundo = 0;
                                            // Buscar todas las coincidencias
                                            while (matcher.find()) {
                                               // System.out.println("Se encontró el patrónnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnn \"" + "vc"+(contarEtiqWhile) + "\" en: " + code[i]+" en el indice "+i+"  ..............................................................................");
                                                val=code[i];
                                                
                                            }
                                            
                                            String valvec[] = val.split(" ");
                                            
                                            for(int j = 0; j<valvec.length; j++)
                                            {
                                                System.out.println("valor: "+valvec[j]);
                                                if(j==2)
                                                {
                                                    val2=valvec[2];
                                                }
                                            }
                                            
                                            for(int j = 0; j<valvec[0].length(); j++)
                                            {
                                                if(valvec[0].charAt(j)=='=')
                                                {
                                                    val1=valvec[0].substring(j+1);
                                                    break;
                                                }
                                            }
                                            
                                             
                                            System.out.println("val1: "+val1+" val2:"+val2);
                                        }
                                   
                       String valorcito = "";
                       String id1="";
                       String id2="";
                       List<Integer> listindices = new ArrayList<>();
                        Pattern pat = Pattern.compile("[0-9]*");
                        Matcher mat = pat.matcher(val1);
                        Matcher mat2 = pat.matcher(val2);
                        if (!mat.matches()) {
                            listindices = new ArrayList<>();
                            for(int i = 0; i<code.length;i++)
                                        {
                                            Pattern pattern;
                                            //System.out.println("code en in: "+code[i]);
                                            pattern = Pattern.compile("var[0-9]*\\s*=*"+val1);
                                            Matcher matcher = pattern.matcher(code[i]);
                                            int primero = 0;
                                            int segundo = 0;
                                            // Buscar todas las coincidencias
                                            while (matcher.find()) {
                                                System.out.println("Se hea encontró el patrónnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnn \"" + "vc"+(contarEtiqWhile) + "\" en: " + code[i]+" en el indice "+i+"  ..............................................................................");
                                                valorcito+=code[i]+"\n";
                                                listindices.add(i);
                                                
                                            }
                                           
                                            /*String valvec[] = val.split(" ");
                                            
                                            for(int j = 0; j<valvec.length; j++)
                                            {
                                                System.out.println("valor: "+valvec[j]);
                                                if(j==2)
                                                {
                                                    val2=valvec[2];
                                                }
                                            }
                                            
                                            for(int j = 0; j<valvec[0].length(); j++)
                                            {
                                                if(valvec[0].charAt(j)=='=')
                                                {
                                                    val1=valvec[0].substring(j+1);
                                                    break;
                                                }
                                            }
                                            */
                                             
                                            System.out.println("val1: "+val1+" val2:"+val2);
                                        }
                             System.out.println("valorcito: "+valorcito);
                             System.out.println("indices: "+listindices);
                             
                             
                             for(int g =0; g<code[listindices.get(listindices.size()-1)].length(); g++)
                             {
                                 if(code[listindices.get(listindices.size()-1)].charAt(g)==' ')
                                 {
                                     id1=code[listindices.get(listindices.size()-1)].substring(0,g);
                                     break;
                                 }
                                 if(code[listindices.get(listindices.size()-1)].charAt(g)=='=')
                                 {
                                     id1=code[listindices.get(listindices.size()-1)].substring(0,g);
                                     break;
                                 }
                             }
                        }
                        else if(!mat2.matches())
                        {
                            listindices = new ArrayList<>();
                            for(int i = 0; i<code.length;i++)
                                        {
                                            Pattern pattern;
                                            //System.out.println("code en in: "+code[i]);
                                            pattern = Pattern.compile("var[0-9]*\\s*=*"+val2);
                                            Matcher matcher = pattern.matcher(code[i]);
                                            
                                            // Buscar todas las coincidencias
                                            while (matcher.find()) {
                                                System.out.println("Se hea encontró el patrónnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnn \"" + "vc"+(contarEtiqWhile) + "\" en: " + code[i]+" en el indice "+i+"  ..............................................................................");
                                                valorcito+=code[i]+"\n";
                                                listindices.add(i);
                                                
                                            }
                                           
                                           
                                             
                                            System.out.println("val1: "+val1+" val2:"+val2);
                                        }
                             System.out.println("valorcito: "+valorcito);
                             System.out.println("indices: "+listindices);
                             
                             
                             for(int g =0; g<code[listindices.get(listindices.size()-1)].length(); g++)
                             {
                                 if(code[listindices.get(listindices.size()-1)].charAt(g)==' ')
                                 {
                                     id2=code[listindices.get(listindices.size()-1)].substring(0,g);
                                     break;
                                 }
                                 if(code[listindices.get(listindices.size()-1)].charAt(g)=='=')
                                 {
                                     id2=code[listindices.get(listindices.size()-1)].substring(0,g);
                                     break;
                                 }
                             }
                        }
            
                        
                        System.out.println("id1: "+id1);
                        System.out.println("id2: "+id2);
                        
                        if(!id1.isEmpty())
                        {
                            codigoIntermedio+=val1+"="+id1+";\n";
                        }
                        if(!id2.isEmpty())
                        {
                            codigoIntermedio+=val2+"="+id2+";\n";
                        }
                        codigoIntermedio+="goto EtqWhile"+contarEtiqWhile+";\nEtqEndWhile"+contarEtiqWhile+":\n";
                        codvar+="goto EtqWhile"+contarEtiqWhile+";\nEtqEndWhile"+contarEtiqWhile+":\n";
                        variablesInterior=false;
                        controlWhile="";
                    }
                break;
                case "print":
                    entradaSalida="printf";
                break;
                case "read":
                    entradaSalida="scanf";

                break;
           
        }
        return true;
    }
    
    
    public boolean operacionSemantica()
    {
        
        System.out.println("SEMANTICA EN OPERACION SEMANTICA: "+pilaSemantica);
        int v1 = pilaSemantica.pop();
        int v2 = pilaSemantica.pop();
       
        if(tablaSemanticaOperaciones[v1][v2]!=-1)
            {
                System.out.println("va1 y v2: "+v1+", "+v2);
                System.out.println("valor tablaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa: "+tablaSemanticaOperaciones[v1][v2]);
                pilaSemantica.push(tablaSemanticaOperaciones[v1][v2]);
                pilaOperadores.pop();
                
                
                String valorA = variablesCodigoIntermedio.get(variablesCodigoIntermedio.size()-1);           
                String valorB = variablesCodigoIntermedio.get(variablesCodigoIntermedio.size()-2);
                
                variablesCodigoIntermedio.remove(variablesCodigoIntermedio.size()-1);
                if(!estaEnEtiqueta)
                {
                    codigoIntermedio+= valorB.substring(0,4)+" = "+valorB.substring(0,4)+operacion+valorA.substring(0,4)+";\n";
                    
                    int indicevar;
                    System.out.println("COOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOONT: "+cont+" "+values.get(cont));
                    for( indicevar =cont; indicevar>=0; indicevar--)
                    {
                        System.out.println("values: "+values.get(indicevar));
                        if(values.get(indicevar).equals("="))
                        {
                            break;
                        }
                    }
                    codvar+=valorB.substring(0,4)+" "+values.get(indicevar-1)+" = "+valorB.substring(0,4)+" "+valorB.substring(5,valorB.length()-1)+operacion+valorA.substring(0,4)+" "+valorA.substring(5,valorA.length()-1)+";\n";
                    //values.get(cont-5)  buscar el igual y el que este antes del igual

                }
                
                if(control.equals("if") && contarIf==1)
                    {
                        System.out.println("AQUI VINOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOO: "+values.get(cont));
                        String valor1 = "";
                        String valor2 = "";
                        for(int i = cont; i>=0; i--)
                        {
                            if(!values.get(i).equals(")") && !values.get(i).equals(">") && !values.get(i).equals("<") && !values.get(i).equals(">=") && !values.get(i).equals("<=") && !values.get(i).equals("==") && !values.get(i).equals("!="))
                            {
                                valor1=values.get(i);
                                for(int j = (i-1); j>=0; j--)
                                {
                                    if(!values.get(j).equals("(") && !values.get(j).equals(")") && !values.get(j).equals(">") && !values.get(j).equals("<") && !values.get(j).equals(">=") && !values.get(j).equals("<=") && !values.get(j).equals("==") && !values.get(j).equals("!="))
                                    {
                                       valor2=values.get(j);
                                       break;
                                     
                                    }
                                }
                                break;
                            }
                        }
                        
                        
                        System.out.println("la 1: "+valor1+" la 2: "+valor2);
                        
                        codigoIntermedio+="bool vc"+contarEtiqIf+"="+valor2+operacion+valor1+";\n";
                        codvar+="vc"+contarEtiqIf+"="+valor2+operacion+valor1+";\n";
                        codigoIntermedio+="if(!vc"+contarEtiqIf+")";
                        codvar+="if(!vc"+contarEtiqIf+")";
                        variablesInterior=true;
                        contarVariables=0;
                        
                        //codigoIntermedio+=control+"(!"+valorB.substring(0,4)+")";
                        //codvar+=control+"(!"+valorB.substring(0,4)+")";
                        contarIf=0;
                    }
                    if(controlWhile.equals("while") && contarWhile==1)
                    {
                        
                        System.out.println("AQUI VINOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOO: "+values.get(cont));
                        String valor1 = "";
                        String valor2 = "";
                        for(int i = cont; i>=0; i--)
                        {
                            if(!values.get(i).equals(")") && !values.get(i).equals(">") && !values.get(i).equals("<") && !values.get(i).equals(">=") && !values.get(i).equals("<=") && !values.get(i).equals("==") && !values.get(i).equals("!="))
                            {
                                valor1=values.get(i);
                                for(int j = (i-1); j>=0; j--)
                                {
                                    if(!values.get(j).equals("(") && !values.get(j).equals(")") && !values.get(j).equals(">") && !values.get(j).equals("<") && !values.get(j).equals(">=") && !values.get(j).equals("<=") && !values.get(j).equals("==") && !values.get(j).equals("!="))
                                    {
                                       valor2=values.get(j);
                                       break;
                                     
                                    }
                                }
                                break;
                            }
                        }
                        
                        
                        System.out.println("la 1: "+valor1+" la 2: "+valor2);
                        
                        
                        codigoIntermedio+="bool vc"+contarEtiqWhile+"="+valor2+operacion+valor1+";\n";
                        codvar+="vc"+contarEtiqWhile+"="+valor2+operacion+valor1+"\n";
                        codigoIntermedio+="if(!vc"+contarEtiqWhile+")";
                        codvar+="if(!vc"+contarEtiqWhile+")";
                        variablesInterior=true;
                        contarVariables=0;
                        contarWhile=0;
                    }
                    
                    
                
                    contarVariables = contarVariables-1;
                
                
                mostrarPilas();
                System.out.println("operacion semantica: "+v1+", "+v2+" pila: "+pilaSemantica);   
                return true;
            }
            else{
                System.out.println("va1 y v2: "+v1+", "+v2);
                System.out.println("valor tabla: "+tablaSemanticaOperaciones[v1][v2]);
                error+="Error semantico operacion incompatible \n";
                System.out.println("Error semantico operacion incompatible \n");
                return false;
            }
        
        
        
    }
    public void codigoIntermedio(Stack<String> pila2, String variable)
    {
        
        Stack<String> invertida = new Stack<>();
         while (!pila2.isEmpty()) {
            invertida.push(pila2.pop());
        }
        System.out.println("aux: "+invertida);
        
        String valor = "";
        String tipo = "";
        while(!invertida.isEmpty())
        {
            System.out.println("size: "+invertida.size());
            valor = invertida.pop();
            System.out.println("valor pilacodigoi: "+valor);
            if(!valor.equals(";"))
            {
                
                System.out.println("valor pilacodigoi: "+valor);
                switch (valor) {
                    case "int":
                        tipo = "int";
                        break;
                    case "float":
                        tipo = "float";
                        break;
                    case "char":
                        tipo = "char";
                        break;
                    case "string":
                        tipo = "string";
                        break;
                    default:
                        switch (tipo) {
                            case "int":
                                codigoIntermedio += "int "+ valor+";\n";
                                codvar+="int "+ valor+";\n";
                                break;
                            case "float":
                                codigoIntermedio += "float "+ valor+";\n";
                                codvar+="float "+ valor+";\n";
                            break;
                            case "char":
                                codigoIntermedio += "char "+ valor+";\n";
                                codvar+="char "+ valor+";\n";
                            break;
                            case "string":
                                codigoIntermedio += "string "+ valor+";\n";
                                codvar+="string "+ valor+";\n";
                            break;
                        }
                        

                }
            }
            
            }
        System.out.println("codigo intermedio: "+codigoIntermedio);
        
    }
    
    
    public String getCodigoIntermedio()
    {
        return codigoIntermedio;
    }
    
    
    public void tipoVar()
    {
       
        // Expresión regular para detectar los operadores
        String regex = "(\\+|-|\\*|/|<|>|<=|>=|==|!=)";
        
        // Validar que no contiene operadores
        
        
        
        String vector[] = codigoIntermedio.split("\n");
        List<String> variables = new ArrayList<String>();
        List<Integer> indices = new ArrayList<Integer>();
        List<String> unicas = new ArrayList<String>();
       
        
        for(int i =0; i<vector.length; i++)
        {
            if(vector[i].length()>3)
            {   
                if(vector[i].substring(0,3).equals("var"))
                {
                    if (!vector[i].matches(regex)) {
                        System.out.println("La cadena no contiene operadores.");
                        variables.add(vector[i].substring(0,4)+" "+i);
                    } else {
                        System.out.println("La cadena contiene operadores.");
                    }

                }
            }
        }
        
     
       
        // Iterar sobre el vector
        for (int i = 0; i < variables.size(); i++) {
            boolean ban = false;
            
                    for(int j=0; j<unicas.size();j++)
                    {
                        if(variables.get(i).substring(0,4).equals(unicas.get(j).substring(0,4)))
                        {
                            ban=true;
                        }
                    }
                    if(!ban)
                    {
                        unicas.add(variables.get(i));
                    }
                }
               
            for(int i=0; i<unicas.size();i++)
            {
                indices.add(Integer.parseInt(unicas.get(i).substring(5)));
            }
                System.out.println("Unicasssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssss: "+unicas);
                System.out.println("indices: "+indices);
            
          for(int i=0; i<indices.size();i++)
            {
                String tipo="";
                   // Recorremos la pila de derecha a izquierda
            for (int j = pilaCodigoIntermedio.size() - 1; j >= 0; j--) {
                System.out.println("vecpileta "+pilaCodigoIntermedio.get(j));

                // Si encontramos el identificador, retornamos el tipo anterior
                if (pilaCodigoIntermedio.get(j).equals(vector[indices.get(i)].replace(";", "").substring(5)) && j>0) {
                    //int cont = j;
                    while(!(pilaCodigoIntermedio.get(j).equals("float") || pilaCodigoIntermedio.get(j).equals("int") || pilaCodigoIntermedio.get(j).equals("char") || pilaCodigoIntermedio.get(j).equals("string")))
                    {
                        System.out.println("nose: "+pilaCodigoIntermedio.get(j));
                        j--;
                    }
                    
                    
                    
                    tipo = pilaCodigoIntermedio.get(j); // El tipo está en la posición anterior

                    
                    System.out.println("SALIO DEL WHILEEE");
                    break;
                }
                
            }
                  try
                    {
                        Integer.parseInt(vector[indices.get(i)].replace(";", "").substring(5));
                        tipo="int";
                    }catch(NumberFormatException e)
                    {
                    }
                vector[indices.get(i)] = tipo+" "+vector[indices.get(i)];
                System.out.println("vec:::: "+tipo+vector[indices.get(i)]);
                System.out.println("vecAAAAAA "+vector[indices.get(i)].replace(";", "").substring(5));
            }
          
         
          
          codigoIntermedio="int main()\n{\n";
          for (int i = 0; i < vector.length; i++) {
              System.out.println("var::: "+vector[i]);
              codigoIntermedio+=vector[i]+"\n";
                     
                }
          codigoIntermedio+="}";
          
          
          
        
      /*  for (String variable : variables) {
            System.out.println("variables"+variable);
            
        }
        
        for (String unica : unicas) {
            System.out.println("unicas"+unica);
            
        }*/
         
        //System.out.println("PILAINTERMEDIO: "+pilaCodigoIntermedio);
        
        
        
       /* for (Integer indice : indices) {
            System.out.println("indices"+indice);
        }*/
        
      
    }
    
}
