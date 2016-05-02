package app.tests;

import app.Sistema.Sistema;
import app.Sistema.Sistema;
import criptografia.EncriptaDecriptaDES;
import criptografia.ROT47;
import java.security.NoSuchAlgorithmException;
import javax.crypto.SecretKey; 

import java.util.Scanner;
import javax.crypto.KeyGenerator;

/**
 * 
 * @author Vinicius Dutra Cerqueira Rocha - 315112918
 
 * @version 5.0
 * 
 */


public class ClasseTesteCriptografia {

      static Scanner sca = new Scanner(System.in);
      static String texto = "";
      static String chave = "";
      static byte[] criptografado = null;
      static String descriptografado = "";      
      static EncriptaDecriptaDES classeDES = new EncriptaDecriptaDES();
      static ROT47 rot = new ROT47();
      static Sistema controller = new Sistema();
      static KeyGenerator keygenerator;// = KeyGenerator.getInstance("DES");
      static SecretKey key = null;
      
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
    
       //metodoAES();
        byte[] encriptado = null;
        encriptado = metodoDES();
        String a = "";
        //a = TestByte(encriptado);
        //System.out.println("Resultado final : " + a);
        
        
        //metodoROT47();

    }
 
    static byte[] metodoDES(){
       byte[] encriptado = null; 
       String resultado = "";
       System.out.print("Digite o texto a ser criptografado : ");
       texto = sca.nextLine();
       key = geraKeyDES();
       System.out.println("--------------------------------------------------");
       encriptado = classeDES.encriptar(texto,key);
       System.out.println("--------------------------------------------------");
       resultado = classeDES.descriptar(encriptado,key);
       System.out.println("--------------------------------------------------");
       System.out.println("[MetodoDES][Descriptografado]Resultado : " + resultado);
       System.out.println("[MetodoDES][toString]Texto digitado : "+ encriptado.toString());
       /**/
        return encriptado;
    }

    static void metodoROT47(){
        /*System.out.println("Digite o texto a ser criptografado : ");
        texto = sca.nextLine();
        String textoCRIPTO = "";
        String textDESCRIPTO = "";
        textoCRIPTO = rot.getCifra(texto);
        textDESCRIPTO = rot.getDecifraROT47(textoCRIPTO);
        System.out.println("Texto Claro : " + texto);
        System.out.println("Texto criptografado : "+ textoCRIPTO);
        System.out.println("Texto Descriptografado : "+ textDESCRIPTO);*/
    }

    static String TestByte(byte[] bytes) {

        String example = "This is an example";
        byte[] bytes2 = example.getBytes();
        bytes2 = bytes;
        System.out.println("Text : " + example);
        System.out.println("Text [Byte Format] : " + bytes2);
        System.out.println("Text [Byte Format] : " + bytes2.toString());
        String s = new String(bytes2);
        System.out.println ("Text Decryted : " + s.toString());
        return s;
    }

    
    static SecretKey geraKeyDES(){
        try {
            keygenerator = KeyGenerator.getInstance("DES");
            key = keygenerator.generateKey();
        } catch (NoSuchAlgorithmException ex) {
            System.out.println("Erro : " + ex.getMessage());
            //sys.EscreveLogUI("geraChaveDES", "[NoSuchAlgorithmException]Erro : " + ex.getMessage(), interfaceUI.jTextArea1);
        }
        return key;
    }
    
    
    
}
