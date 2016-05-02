package app.Sistema;

import app.frames.UIServidor;
import app.Sistema.ChatMessage.Action;
import criptografia.EncriptaDecriptaDES_v2;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

/**
 
 * @author Vinicius Dutra Cerqueira Rocha - 315112918
 
 * @version 5.0
 * 
 */

public class ServidorService {

    private ServerSocket serverSocket;
    private Socket socket;
    private Map<String, ObjectOutputStream> mapOnlines = new HashMap<String, ObjectOutputStream>();
    static Sistema sys = new Sistema();

    static KeyGenerator keygenerator;// = KeyGenerator.getInstance("DES");
    static SecretKey chaveDES;    
    static EncriptaDecriptaDES_v2 classeDES = new EncriptaDecriptaDES_v2();


    static UIServidor interfaceUI = new UIServidor();
    
    public ServidorService() {
        try {
            serverSocket = new ServerSocket(5555);
            sys.EscreveLogConsole("ServidorService - Metodo ServidorService","***INICIO***");
            interfaceUI.setVisible(true);
            
            sys.EscreveLogUI("Metodo ServidorService","***INICIO***",interfaceUI.jTextArea1);
            
            sys.EscreveLogConsole("ServidorService - Metodo ServidorService","Servidor on!");

            while (true) {
                socket = serverSocket.accept();

                new Thread(new ListenerSocket(socket)).start();
            }

        } catch (IOException ex) {
            sys.EscreveLogConsole("ServidorService - Metodo ServidorService","IOException : " + ex.getMessage()); 
            sys.EscreveLogUI("ServidorService - IOException","IOException : " + ex.getMessage(),interfaceUI.jTextArea1);
        }
    }

    private class ListenerSocket implements Runnable {

        private ObjectOutputStream output;
        private ObjectInputStream input;

        public ListenerSocket(Socket socket) {
            try {
                this.output = new ObjectOutputStream(socket.getOutputStream());
                this.input = new ObjectInputStream (socket.getInputStream());
            } catch (IOException ex) {
                Logger.getLogger(ServidorService.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        @Override
        public void run() {
            ChatMessage message = null;
            try {
                while ((message = (ChatMessage) input.readObject()) != null) {
                    Action action = message.getAction();

                    if (action.equals(Action.CONNECT)) {
                        
                        boolean isConnect = connect(message, output);
                        if (isConnect) {
                            mapOnlines.put(message.getName(), output);
                            sendOnlines();
                        }                        
                    } else if (action.equals(Action.DISCONNECT)) {
                        disconnect(message, output);
                        sendOnlines();
                        return;
                    } else if (action.equals(Action.SEND_ONE)) {
                        sendOne(message);
                    } else if (action.equals(Action.SEND_ALL)) {
                        sendAll(message);
                    }
                }
            } catch (IOException ex) {
                ChatMessage cm = new ChatMessage();
                cm.setName(message.getName());
                disconnect(cm, output);
                sendOnlines();
                sys.EscreveLogConsole("ServidorService - Metodo run - IOException", "O usuário : " + message.getName() + " deixou o chat.");
                sys.EscreveLogUI("ServidorService - Metodo run - IOException", "O usuário : " + message.getName() + " deixou o chat.",interfaceUI.jTextArea1);
            } catch (ClassNotFoundException ex) {
                sys.EscreveLogConsole("ServidorService - Metodo run - ClassNotFoundException", "Erro : " + ex.getMessage() + " .");
                sys.EscreveLogUI("ServidorService - Metodo run - ClassNotFoundException", "Erro : " + ex.getMessage() + " .",interfaceUI.jTextArea1);
            }
        }
    }

    private boolean connect(ChatMessage message, ObjectOutputStream output) {
        sys.EscreveLogConsole("Metodo connect - ServidorService","INICIO");
        sys.EscreveLogUI("Metodo connect - ServidorService","INICIO",interfaceUI.jTextArea1);
        

        if (mapOnlines.size() == 0) {
            message.setText("YES");
            
            sys.EscreveLogUI("Metodo connect - ServidorService","Primeiro cliente a se conectar no servidor",interfaceUI.jTextArea1);
            sys.EscreveLogUI("Metodo connect - ServidorService","tpCrypt : " + message.getTpCrypt(),interfaceUI.jTextArea1);
            
            if(message.getTpCrypt() == 2){
                chaveDES = geraChaveDES();
                message.setIsGenaratedKey(true);
                message.setChaveDES(chaveDES);
                sys.EscreveLogConsole("Metodo connect - ServidorService","Chave : " + chaveDES.toString());
                sys.EscreveLogUI("Metodo connect - ServidorService","Passou dentro do if tp==2, mapOnlines ==0. ChaveDES : " + chaveDES,interfaceUI.jTextArea1);
            }
            
            send(message, output);            
            //sys.EscreveLogConsole("Metodo connect - ServidorService","MapOnlines = 0");
            sys.EscreveLogUI("Metodo connect - ServidorService","MapOnlines = 0",interfaceUI.jTextArea1);
            //sys.EscreveLogConsole("Metodo connect - ServidorService","---FIM Maponlines -----");
            sys.EscreveLogUI("Metodo connect - ServidorService","---FIM Maponlines -----",interfaceUI.jTextArea1);
            return true;
        }

        if (mapOnlines.containsKey(message.getName())) {
            message.setText("NO");
            send(message, output);
            sys.EscreveLogConsole("Metodo connect - ServidorService","Message = NO");
            sys.EscreveLogUI("Metodo connect - ServidorService","Message = NO" ,interfaceUI.jTextArea1);
            sys.EscreveLogConsole("Metodo connect - ServidorService","***FIM***");
            sys.EscreveLogUI("Metodo connect - ServidorService","***FIM***",interfaceUI.jTextArea1);
            return false;
        } else {
            if(message.getTpCrypt() == 2){
                message.setChaveDES(chaveDES);
                sys.EscreveLogUI("Metodo connect - ServidorService",
                        "Passou dentro do if tp==2, mapOnlines !=0. ChaveDES : " + message.getChaveDES(),interfaceUI.jTextArea1);    
                
            }
            message.setText("YES");
            send(message, output);
            sys.EscreveLogConsole("ServidorService - Metodo connect[Else]","Message = YES");
            sys.EscreveLogUI("ServidorService - Metodo connect[Else]","Message = YES",interfaceUI.jTextArea1);
            sys.EscreveLogConsole("ServidorService - Metodo connect[Else]","***FIM***");
            sys.EscreveLogUI("ServidorService - Metodo connect[Else]","***FIM***",interfaceUI.jTextArea1);
            return true;
        }
        
    }

    private void disconnect(ChatMessage message, ObjectOutputStream output) {
        mapOnlines.remove(message.getName());
        sys.EscreveLogConsole("ServidorService - Metodo disconnect","INICIO");
        sys.EscreveLogUI("ServidorService - Metodo disconnect","INICIO",interfaceUI.jTextArea1);
        message.setText(" até logo!");

        message.setAction(Action.SEND_ONE);

        sendAll(message);

       sys.EscreveLogConsole("ServidorService - Metodo disconnect","O usurário " + message.getName() + " sai da sala");
       sys.EscreveLogUI("ServidorService - Metodo disconnect","O usurário " + message.getName() + " sai da sala",interfaceUI.jTextArea1);
       sys.EscreveLogConsole("ServidorService - Metodo disconnect","***FIM**");
       sys.EscreveLogUI("ServidorService - Metodo disconnect","***FIM**",interfaceUI.jTextArea1);
    }

    private void send(ChatMessage message, ObjectOutputStream output) {
        sys.EscreveLogConsole("ServidorService - Metodo send","***INICIO***");
        sys.EscreveLogUI("ServidorService - Metodo send","***FIM***",interfaceUI.jTextArea1);
        try {
            output.writeObject(message);
            /*if(message.getTpCrypt() == 2) {
                sys.EscreveLogConsole("ServidorService - Metodo send","toString : " + "name : " + message.getName() + "\n" + 
                "msg : " + message.getBytetxt() + "\nChave DES : " + message.getChaveDES() + "\nTexto : "+ message.getText());
            }*/
        } catch (IOException ex) {
             sys.EscreveLogUI("ServidorService - Metodo send","[IOException]Erro : " + ex.getMessage(),interfaceUI.jTextArea1);
        }
        sys.EscreveLogConsole("ServidorService - Metodo send","***FIM***");
        sys.EscreveLogUI("ServidorService - Metodo send","***FIM***",interfaceUI.jTextArea1);
    }

    private void sendOne(ChatMessage message) {
        sys.EscreveLogConsole("ServidorService - Metodo send","***INICIO***");
        sys.EscreveLogUI("ServidorService - Metodo send","***INICIO***",interfaceUI.jTextArea1);
        for (Map.Entry<String, ObjectOutputStream> kv : mapOnlines.entrySet()) {
            if (kv.getKey().equals(message.getNameReserved())) {
                try {
                    kv.getValue().writeObject(message);
                    /*if (message.getTpCrypt() == 2) {
                        sys.EscreveLogConsole("ServidorService - Metodo sendOne", "toString : " + "name : " + message.getName() + "\n"
                                + "msg : " + message.getBytetxt() + "\nChave DES : " + message.getChaveDES() + "\nTexto : " + message.getText());
                    }*/
                    
                } catch (IOException ex) {
                    sys.EscreveLogUI("ServidorService - Metodo send","[IOException]Erro : " + ex.getMessage(),interfaceUI.jTextArea1);
                }
            }
        }
       sys.EscreveLogConsole("ServidorService - Metodo send","***FIM***");
       sys.EscreveLogUI("ServidorService - Metodo send","***FIM***",interfaceUI.jTextArea1);
    }

    private void sendAll(ChatMessage message) {
        sys.EscreveLogConsole("ServidorService - Metodo sendAll","***INICIO***");
        sys.EscreveLogUI("ServidorService - Metodo sendAll","***INICIO***",interfaceUI.jTextArea1);
        
        for (Map.Entry<String, ObjectOutputStream> kv : mapOnlines.entrySet()) {
            if (!kv.getKey().equals(message.getName())) {
                message.setAction(Action.SEND_ONE);
                try {
                    kv.getValue().writeObject(message);
                    
                    /*if (message.getTpCrypt() == 2) {
                        sys.EscreveLogConsole("ServidorService - Metodo sendAll", "toString : " + "name : " + message.getName() + "\n"
                                + "msg : " + message.getBytetxt() + "\nChave DES : " + message.getChaveDES() + "\nTexto : " + message.getText());
                    }*/
                } catch (IOException ex) {
                   sys.EscreveLogUI("ServidorService - Metodo sendAll","[IOException]Erro : " + ex.getMessage(),interfaceUI.jTextArea1);
                }
            }
        }
        sys.EscreveLogConsole("ServidorService - Metodo sendAll","***FIM***");
        sys.EscreveLogUI("Metodo sendAll - ServidorService","***FIM***",interfaceUI.jTextArea1);
    }

    private void sendOnlines() {
        sys.EscreveLogConsole("ServidorService - Metodo sendOnlines","***INICIO****");
        sys.EscreveLogUI("ServidorService - Metodo sendOnlines","***INICIO****",interfaceUI.jTextArea1);
        
        Set<String> setNames = new HashSet<String>();
        for (Map.Entry<String, ObjectOutputStream> kv : mapOnlines.entrySet()) {
            setNames.add(kv.getKey());
        }
        String aux = new String().valueOf(setNames.size());
        interfaceUI.LabelQtde.setText(aux);
        ChatMessage message = new ChatMessage();
        message.setAction(Action.USERS_ONLINE);
        message.setSetOnlines(setNames);

        for (Map.Entry<String, ObjectOutputStream> kv : mapOnlines.entrySet()) {
            message.setName(kv.getKey());
            try {
                kv.getValue().writeObject(message);
                
            } catch (IOException ex) {
                sys.EscreveLogUI("ServidorService - Metodo sendOnlines","[sendOnlines]Erro : " + ex.getMessage(),interfaceUI.jTextArea1);
            }
        }
        sys.EscreveLogConsole("ServidorService - Metodo sendOnlines","***FIM****");
        sys.EscreveLogUI("ServidorService - Metodo sendOnlines","***INICIO****",interfaceUI.jTextArea1);
        
    }
   

    public SecretKey geraChaveDES() {
        try {
            keygenerator = KeyGenerator.getInstance("DES");
            chaveDES = keygenerator.generateKey();            
        } catch (NoSuchAlgorithmException ex) {
            sys.EscreveLogUI("geraChaveDES","[NoSuchAlgorithmException]Erro : " + ex.getMessage(),interfaceUI.jTextArea1);
        }
        return chaveDES;
    }
}
