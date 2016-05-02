package app.tests;

import Criptografia.EncriptaDecriptaDES_v2;
import app.Sistema.Sistema;
import java.util.Scanner;

/**

 * @author Vinicius Dutra Cerqueira Rocha - 315112918
 
 * @version 5.0
 * 
 */

public class ClasseDeTeste {

      static Scanner sca = new Scanner(System.in);
      static String texto = "";
      static String chave = "";
      static byte[] criptografado = null;
      static String descriptografado = "";
      //static AES classeAES = new AES();
      static EncriptaDecriptaDES_v2 classeDES = new EncriptaDecriptaDES_v2();
      static Sistema controller = new Sistema();
      static criptografia.ROT47 secrot = new criptografia.ROT47();
      
      
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
    
       metodoDES();
       //metodoROT();
    }
    

    /**
     * Metodo de teste de criptografia DES
     */
    static void metodoDES(){
       byte[] encriptado = null; 
       String resultado = "";
       System.out.println("Digite o texto a ser criptografado : ");
       texto = sca.nextLine();     
       encriptado = classeDES.encriptarOLD(texto);   
       
       resultado = classeDES.descriptarOLD(encriptado);
       System.out.println("Resultado : " + resultado);
       String teste = "";
       teste = encriptado.toString();
       System.out.println("Resultado Teste: " + teste);
    }


    /**
     * Metodo de teste de criptografia ROT47
     * 
     */
    static void metodoROT(){
       System.out.println("Digite o texto a ser criptografado : ");
       texto = sca.nextLine();
       String encriptado = secrot.Encrypt(texto);
       System.out.println("Texto claro : " + texto);
       System.out.println("Texto Criptografado : " + encriptado);
       System.out.println("Texto Decriptografado : " + secrot.Decrypt(encriptado));
    }



}
