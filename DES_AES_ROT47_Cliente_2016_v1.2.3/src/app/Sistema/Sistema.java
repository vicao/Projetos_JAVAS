package app.Sistema;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;

/**
 
 * @author Vinicius Dutra Cerqueira Rocha - 315112918
 
 * @version 5.0
 * 
 */

public class Sistema {
    File Pastalog = new File("LogsCripto\\");
    File ArqLog = new File("");
    static String Data = new SimpleDateFormat("[dd/MM/yyyy - HH:mm:ss]").format(new Date(System.currentTimeMillis()));
    static String DataArq = new SimpleDateFormat("[dd/MM/yyyy - HH:mm:ss]").format(new Date(System.currentTimeMillis()));
    
    /**
     * Metodo para criação de arquivo de log
     * @param arquivo - nome e localização do arquivo gerado
     * @return Retorna true se o arquivo foi criado com sucesso.
     */
    public boolean CriaLog(File arquivo){
      
        try {            
            if(!arquivo.getParentFile().exists()){
                //EscreveLogConsole("CriaLog","Parent File : " + arquivo.getParentFile().getAbsolutePath());
                arquivo.getParentFile().mkdir();
                
                if (!arquivo.exists()) {  
                    
                    arquivo.createNewFile();
                    //EscreveLogConsole("CriaLog","Arquivo do log criado : " + arquivo.getParentFile().getAbsolutePath());
                   
                }
            }
            
            return true;
        } catch (IOException ex) {
            return false;
        }
        
        }
    
    /**
     * Metodo para escrever no arquivo de log
     * @param texto Conteudo que sera salvo no arquivo de log
     * @param arquivo Arquivo a ser criado
     */
    
    public void EscreveLog(String texto,File arquivo) {
        try {
            FileWriter FWriter;
            PrintWriter PWriter;  
            arquivo = new File(arquivo.getAbsolutePath().concat(".logCc"));           
            EscreveLogConsole("EscreveLog", "Entrou no if.");
            FWriter = new FileWriter(arquivo, true);
            PWriter = new PrintWriter(FWriter);
            PWriter.println(texto);
            System.out.println(texto);
            PWriter.close();
        } catch (IOException ex) {
            System.out.println(Data + "[EscreveLog]Erro : " + ex);
            JOptionPane.showMessageDialog(null,"Erro : " + ex,"Erro de Log" ,JOptionPane.ERROR_MESSAGE);
        }
       
    } 

    
    
    /**
     * Metodo para leitura da chave a ser usada na criptografia
     * @param arquivoChave - Arquivo contendo a chave
     * @return chave a ser utilizada
     */
    public String LeChave(File arquivoChave){
        String chave = "";
        BufferedReader buffRead = null;
        try {
            buffRead = new BufferedReader(new FileReader(arquivoChave));
            String linha = "";
            linha = buffRead.readLine();  
                if (linha != null) {
                    chave = linha;                    
                }
                
                     
            
            buffRead.close();
        } catch (Exception ex) {
            System.out.println(Data + "[LeChave]Erro : " + ex);
            Logger.getLogger(Sistema.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                buffRead.close();
            } catch (IOException ex) {
                System.out.println(Data + "[LeChave]Erro : " + ex);
                Logger.getLogger(Sistema.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return chave;
    }
    
    /**
     * Metodo de ponto de log que será exibido no console
     * @param metodo nome do metodo que está sendo executado
     * @param txt texto a ser exibido no console após o nome do método.
     */
    public void EscreveLogConsole(String metodo, String txt) {
        String Data = new SimpleDateFormat("[dd/MM/yyyy - HH:mm:ss]").format(new Date(System.currentTimeMillis()));
        System.out.println(Data + "[" + metodo + "]" + txt);
    }
    
    /**
     * Metodo de ponto de log que irá imprimir na interface gráfica
     * @param metodo nome do metodo que está sendo executado
     * @param txt texto a ser exibido no console após o nome do método.
     * @param area componente jtextarea que irá exibir o log
     */
    public void EscreveLogUI(String metodo, String txt,JTextArea area) {
        String Data = new SimpleDateFormat("[dd/MM/yyyy - HH:mm:ss]").format(new Date(System.currentTimeMillis()));
        //System.out.println(Data + "[" + metodo + "]" + txt);
        area.append(Data + "[" + metodo + "]" + txt + "\n");
    }
    
}
