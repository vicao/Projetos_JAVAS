package criptografia;

import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * @author Vinicius Dutra Cerqueira Rocha - 315112918

 * @version 5.0
 * 
 */

public class ROT47 {
    
    private final static int key = 47;
    static String Data = new SimpleDateFormat("[dd/MM/yyyy - HH:mm:ss]").format(new Date(System.currentTimeMillis()));
    

    /**
     * Metodo para encriptar um texto utilizando a criptografia ROT47
     * @param textoClaro texto a ser criptografado
     * @return retorna o texto criptografado
     */
	public static String Encrypt(String textoClaro)
	{
		int novo_valor_ascii;
		int valor_ascii;
		String mensagem_cifrada = "";
		//percorra o meu textoClaroo claro
		for(int i=0; i < textoClaro.length();i++)
		{
			valor_ascii = (int)textoClaro.charAt(i);
			if(valor_ascii >= 33 && valor_ascii <= 126)
			{
				valor_ascii -= 33;
				novo_valor_ascii = (valor_ascii + key) % 94;
				mensagem_cifrada += (char)(novo_valor_ascii+33);
			}
			else
				mensagem_cifrada += textoClaro.charAt(i);
			}
                System.out.println(Data + "[ROT47]Mensagem Cifrada : " + mensagem_cifrada);
		return mensagem_cifrada;
	}
	
    /**
     * Metodo para desencriptar um texto utilizando a criptografia ROT47     
     * @param textoClaro textoClaro a ser criptografado
     * @return retorna o texto criptografado
     */
	public static String Decrypt(String textoClaro)
	{
		int novo_valor_ascii;
		int valor_ascii;
		String mensagem_cifrada = "";
		//percorra o meu texto claro
		for(int i=0; i < textoClaro.length();i++)
		{
			valor_ascii = (int)textoClaro.charAt(i);
			if(valor_ascii >= 33 && valor_ascii <= 126)
			{
				valor_ascii -= 33;
				int val = (valor_ascii - key);
				if(val < 0)
					val = 94 + (valor_ascii - key);
				novo_valor_ascii = val % 94;
				mensagem_cifrada += (char)(novo_valor_ascii+33);
			}
			else
				mensagem_cifrada += textoClaro.charAt(i);
			}
                System.out.println(Data + "[ROT47]Mensagem Decifrada : " + mensagem_cifrada);
		return mensagem_cifrada;
	}
	
	
}
