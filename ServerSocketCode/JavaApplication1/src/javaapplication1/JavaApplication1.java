/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplication1;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import static java.nio.file.StandardWatchEventKinds.ENTRY_CREATE;
import static java.nio.file.StandardWatchEventKinds.ENTRY_DELETE;
import static java.nio.file.StandardWatchEventKinds.ENTRY_MODIFY;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.Random;
import java.util.UUID;

/**
 *
 * @author Love Android
 */
public class JavaApplication1 {

    /**
     * @param args the command line arguments
     */
         static Socket s =null;
	static ServerSocket ss=null;
	static ObjectInputStream ois=null;
	static DataOutputStream DOS=null;
    public static void main(String[] args) throws ClassNotFoundException, IOException {
        try {
            connect();
	}catch(Exception e) {
		e.printStackTrace();
	}
    while(true) {
		try {
                        System.err.println("waiting to read the image ...");
    			byte[] buffer = (byte[]) ois.readObject();
			String id = UUID.randomUUID().toString();
                        Random random=new Random();
                        String img_id=(random.nextInt(100000))+"";
                        System.out.println("Write the image");
			FileOutputStream fos = new FileOutputStream("E:\\FinalProject\\InputImages\\"+img_id+".jpg");
                        String file_name="E:\\FinalProject\\InputImages\\"+img_id+".jpg";
			fos.write(buffer);
			fos.close();
                        System.out.println("Waiting the questions number");
                        int questionNumber=(int)ois.readObject();
                        
                        BufferedWriter bf=new BufferedWriter(new FileWriter("E:\\FinalProject\\InputFiles\\"+id+".txt"));
                        bf.write(file_name+"\n"+questionNumber+"");
                        bf.close();
                        System.out.println("End Writing");
			WatchForOutput(id);
		     
		}catch (IOException e) {
                        ss.close();
                        s.close();
                        ois.close();
                        DOS.close();
			ss = null;
                        s = null;
                        ois = null;
                        DOS = null;
                        connect();
		}
	}
    }
    
    static void connect(){
        try{
            ss = new ServerSocket(19012);
            s = ss.accept();
            ois = new ObjectInputStream(s.getInputStream());
            DOS = new DataOutputStream(s.getOutputStream());
            System.out.println("Connection Accepted");
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    	static void WatchForOutput(String id) {
		System.out.println("Watching");
	
		 try {
	            // Creates a instance of WatchService.
	            WatchService watcher = FileSystems.getDefault().newWatchService();

	            // Registers the logDir below with a watch service.
	            Path logDir = Paths.get("E:\\FinalProject\\Outputs");
                    
                    
                    //‪E:\FinalProject\Outputs\3154.txt
	            logDir.register(watcher, ENTRY_CREATE, ENTRY_MODIFY, ENTRY_DELETE);

	            // Monitor the logDir at listen for change notification.
	            while (true) {
	                WatchKey key = watcher.take();
	                for (WatchEvent<?> event : key.pollEvents()) {
	                    WatchEvent.Kind<?> kind = event.kind();

	                      if (ENTRY_MODIFY.equals(kind)) {
                                  Thread.sleep(1);
	                    	  System.out.println(event.context());
	                    	  System.out.println(id +".txt");
	                    	  System.out.println("Reading the new file ");
                                  BufferedReader br = new BufferedReader(new FileReader("E:/FinalProject/Outputs/"+id+".txt"));
                                  
	  	                  String lastLine=br.readLine();
	  	                  String res="";
	  	                  while(lastLine != null) {
	  	                      res += lastLine+" ";
	  	                      lastLine = br.readLine();
	  	                  }
	  	                  br.close();
                                  send(res);
	                    	  return ;  
	                    } 
	                }
	                key.reset();
	            }
	        } catch (IOException | InterruptedException e) {
	            e.printStackTrace();
	        }
		
	}
        
        static void send( String Respond ) {
		try {
			System.out.println(Respond);
			DOS.writeUTF(Respond);
			System.out.println("after writing it");
			DOS.flush();
                        System.out.println("send ended");
		}catch(IOException e){
			e.printStackTrace();
		}
	}
}
