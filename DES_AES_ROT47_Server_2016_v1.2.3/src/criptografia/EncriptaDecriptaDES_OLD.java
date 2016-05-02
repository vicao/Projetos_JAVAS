package criptografia;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.Cipher;
 import javax.crypto.KeyGenerator; 
 import javax.crypto.SecretKey; 
 

/**
 
 * @author Vinicius Dutra Cerqueira Rocha - 315112918

 * @version 5.0
 * 
 */

 public class EncriptaDecriptaDES_OLD {
	 
    static KeyGenerator keygenerator;// = KeyGenerator.getInstance("DES");
    static SecretKey chaveDES = null;
    
    /**
     *  Metodo para criptografar o texto utilizando o processo DES
     * @param texto  - texto a ser encriptado
     * @param chave  Chave a ser utilizada pra realizar a criptografia
     *  @return array de bytes com o texto criptografado.
     */
     public static byte[] encriptar(String texto,SecretKey chave){
        byte[] textoEncriptado = null;
             try {
                 //keygenerator = KeyGenerator.getInstance("DES");   
                 //chave = keygenerator.generateKey();
                 Cipher cifraDES; // Cria a cifra
                 cifraDES = Cipher.getInstance("DES/ECB/PKCS5Padding"); // Inicializa a cifra para o processo de encriptação
                 cifraDES.init(Cipher.ENCRYPT_MODE, chave); // Texto puro
                 byte[] asercriptografado = texto.getBytes();
                 //System.out.println("Texto [Formato de Byte] : " + asercriptografado);
                // System.out.println("Texto Puro : " + new String(asercriptografado)); // Texto encriptado
                 textoEncriptado = cifraDES.doFinal(asercriptografado);
                 System.out.println("[EncriptaDecriptaDES][encriptar]Texto Encriptado : " + textoEncriptado);                
             } catch (Exception ex) {
                 Logger.getLogger(EncriptaDecriptaDES_OLD.class.getName()).log(Level.SEVERE, null, ex);
             }
          return textoEncriptado;               
    }     
	
     /**
      * Metodo para descriptografar usando o processo DES
      * @param textoEncriptado array de bytes encriptado
      * @param chave chave de descriptografia
      * @return texto descriptografado
      */
    public static String descriptar(byte[] textoEncriptado,SecretKey chave){
          String descriptografado = "";
             try {
                 //keygenerator = KeyGenerator.getInstance("DES");                 
                 Cipher cifraDES; // Cria a cifra
                 cifraDES = Cipher.getInstance("DES/ECB/PKCS5Padding"); // Inicializa a cifra para o processo de encriptação                 
                 //System.out.println("Texto Encriptografado : " + new String(textoEncriptado)); // Texto encriptado
                 cifraDES.init(Cipher.DECRYPT_MODE, chave); // Decriptografa o texto
                 byte[] textoDecriptografado = cifraDES.doFinal(textoEncriptado); 
                 descriptografado = new String(textoDecriptografado);
                 System.out.println("[EncriptaDecriptaDES][descriptar]Texto Decriptografado : " + descriptografado);
             } catch (Exception ex) {
                 Logger.getLogger(EncriptaDecriptaDES_OLD.class.getName()).log(Level.SEVERE, null, ex);
             }
                   return   descriptografado;   
    }
    
    
    /**
     * Metodo antigo de criptografia
     * @param texto texto a ser criptografado
     * @return array de bytes
     */
    public static byte[] encriptarOLD(String texto){
        byte[] textoEncriptado = null;
             try {
                 keygenerator = KeyGenerator.getInstance("DES");   
                 chaveDES = keygenerator.generateKey();
                 Cipher cifraDES; // Cria a cifra
                 cifraDES = Cipher.getInstance("DES/ECB/PKCS5Padding"); // Inicializa a cifra para o processo de encriptação
                 cifraDES.init(Cipher.ENCRYPT_MODE, chaveDES); // Texto puro
                 byte[] asercriptografado = texto.getBytes();
                 //System.out.println("Texto [Formato de Byte] : " + asercriptografado);
                // System.out.println("Texto Puro : " + new String(asercriptografado)); // Texto encriptado
                 textoEncriptado = cifraDES.doFinal(asercriptografado);
                 System.out.println("Texto Encriptado : " + textoEncriptado);                
             } catch (Exception ex) {
                 Logger.getLogger(EncriptaDecriptaDES_OLD.class.getName()).log(Level.SEVERE, null, ex);
             }
          return textoEncriptado;               
    }  
    
    /**
     * Metodo antigo de descriptografia
     * @param textoEncriptado texto a ser encriptador
     * @return string contendo o texto criptografado
     */
    public static String descriptarOLD(byte[] textoEncriptado){
          String descriptografado = "";
             try {
                 //keygenerator = KeyGenerator.getInstance("DES");                 
                 Cipher cifraDES; // Cria a cifra
                 cifraDES = Cipher.getInstance("DES/ECB/PKCS5Padding"); // Inicializa a cifra para o processo de encriptação                 
                 //System.out.println("Texto Encriptografado : " + new String(textoEncriptado)); // Texto encriptado
                 cifraDES.init(Cipher.DECRYPT_MODE, chaveDES); // Decriptografa o texto
                 byte[] textoDecriptografado = cifraDES.doFinal(textoEncriptado); 
                 descriptografado = new String(textoDecriptografado);
                 //System.out.println("Texto Decriptografado : " + descriptografado);
             } catch (Exception ex) {
                 Logger.getLogger(EncriptaDecriptaDES_OLD.class.getName()).log(Level.SEVERE, null, ex);
             }
                   return   descriptografado;   
    }
    
}
