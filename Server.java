package fileServer;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {
    static ArrayList filesList = new ArrayList();

    public static void main(String[] args) throws IOException {
        int port=8080;
        ServerSocket serverSocket;
        try{
            serverSocket=new ServerSocket(port) ;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        System.out.println("server running");
        while (true){
            Socket socket;
            try {
                socket=serverSocket.accept();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            System.out.println("new connection accepted");
            try{
                httpHandler handler=new httpHandler(socket);
                Thread thread=new Thread(handler);
                thread.start();

            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

}
class httpHandler implements Runnable{
    Socket socket;
    InputStream inputStream;
    OutputStream outputStream;
    BufferedReader bufferedReader;
    public httpHandler(Socket socket) throws IOException {
        this.socket=socket;
        this.inputStream=socket.getInputStream();
        this.outputStream=socket.getOutputStream();
        this.bufferedReader=new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    @Override
    public void run() {
        try{
            process();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void process() throws IOException {
        while (true) {
            DataInputStream dataInputStream=new DataInputStream(socket.getInputStream());
            int len=dataInputStream.readInt();
            String fileName=null;
            if (len>0){
                byte[]fileNameBytes=new byte[len];
                dataInputStream.readFully(fileNameBytes,0,fileNameBytes.length);
                fileName=new String(fileNameBytes);
            }
            int fileContentLength=dataInputStream.readInt();
            if (fileContentLength>0){
                byte[]fileContentBytes=new byte[fileContentLength];
                dataInputStream.readFully(fileContentBytes,0,fileContentBytes.length);
                storeFile(fileName,fileContentBytes);
            }

        }
    }
    public static void storeFile(String fileName, byte[] data) throws IOException {
        File filein=new File(fileName);
        if (!filein.exists()){
            filein.createNewFile();
        }
        try {
            FileOutputStream outputStream=new FileOutputStream(filein);
            outputStream.write(data);
            outputStream.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public void retrieveFile(String fileName){

    }

}

        /*JFrame frame=new JFrame("File Client");
        frame.setSize(450,450);
        frame.setLayout(new BoxLayout(frame.getContentPane(),BoxLayout.Y_AXIS));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel panel=new JPanel();
        panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));
        JScrollPane scrollPane=new JScrollPane(panel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        JLabel title=new JLabel("MyCode");
        title.setFont(new Font("Arial",Font.BOLD,25));
        title.setBorder(new EmptyBorder(20,0,10,0));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        frame.add(title);
        frame.add(scrollPane);
        frame.setVisible(true);
        ServerSocket  serverSocket=new ServerSocket(9999);
        while (true){
            try {
                Socket socket=serverSocket.accept();
                DataInputStream dataInputStream=new DataInputStream(socket.getInputStream());
                int len=dataInputStream.readInt();
                if (len>0){
                    byte[]namebytes=new byte[len];
                    dataInputStream.readFully(namebytes,0,namebytes.length);
                }
                int contentlen=dataInputStream.readInt();
                if (contentlen>0){
                    byte[]filebytes=new byte[contentlen];
                    dataInputStream.readFully(filebytes,0,filebytes.length);
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
 */

