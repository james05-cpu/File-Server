package fileServer;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.net.Socket;

public class Client {
    public static void main(String[] args) {
        final File[] fileOut = new File[1];
        JFrame frame=new JFrame("File Client");
        frame.setSize(450,450);
        frame.setLayout(new BoxLayout(frame.getContentPane(),BoxLayout.Y_AXIS));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JLabel title=new JLabel("MyCode");
        title.setFont(new Font("Arial",Font.BOLD,25));
        title.setBorder(new EmptyBorder(20,0,10,0));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);


        JLabel fname=new JLabel("choose a file");
        fname.setFont(new Font("Arial",Font.BOLD,20));
        fname.setBorder(new EmptyBorder(50,0,0,0));
         fname.setAlignmentX(Component.CENTER_ALIGNMENT);
         JPanel panel=new JPanel();
         panel.setBorder(new EmptyBorder(75,0,10,0));

         JButton sendf=new JButton("Send File");
         sendf.setPreferredSize(new Dimension(150,75));
         sendf.setFont(new Font("Arial",Font.BOLD,20));

         JButton choosef=new JButton("Choose File");
         choosef.setPreferredSize(new Dimension(150,75));
         choosef.setFont(new Font("Arial",Font.BOLD,20));
          panel.add(sendf);
          panel.add(choosef);
          frame.add(title);
          frame.add(fname);
          frame.add(panel);
          frame.setVisible(true);
          choosef.addActionListener(new ActionListener() {
              @Override
              public void actionPerformed(ActionEvent e) {
                  JFileChooser fileChooser=new JFileChooser();
                  fileChooser.setDialogTitle("Choose File");
                  if (fileChooser.showOpenDialog(null)==JFileChooser.APPROVE_OPTION){
                      fileOut[0] =fileChooser.getSelectedFile();
                      fname.setText(fileOut[0].getAbsolutePath());
                  }
              }
          });

          sendf.addActionListener(new ActionListener() {
              @Override
              public void actionPerformed(ActionEvent e) {
                  if (fileOut[0]==null){
                      fname.setText("Please select a file");
                  }
                  else{
                      try{
                          FileInputStream fileInputStream=new FileInputStream(fileOut[0].getAbsolutePath());
                          Socket socket=new Socket("localhost",8080);
                          DataOutputStream dataOutputStream=new DataOutputStream(socket.getOutputStream());
                          String filename= fileOut[0].getName();
                          byte[]namebyte=filename.getBytes();
                          byte[]filebytes=new byte[(int)fileOut[0].length()];
                          fileInputStream.read(filebytes);
                          dataOutputStream.writeInt(namebyte.length);
                          dataOutputStream.write(namebyte);
                          dataOutputStream.writeInt(filebytes.length);
                          dataOutputStream.write(filebytes);
                      } catch (Exception ex) {
                          throw new RuntimeException(ex);
                      }


                  }
              }
          });
    }

}
