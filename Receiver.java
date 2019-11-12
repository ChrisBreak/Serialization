//Cristhian Sotelo-Plaza
//CPSC501 FALL 2019
//A3

//compile: javac -cp .:jdom2/* *.java
//run: java -cp .:jdom2/* Receiver

import java.io.*;
import java.net.*;
import java.util.Scanner;
import org.jdom2.*;
import org.jdom2.input.SAXBuilder;
import java.lang.reflect.*;
import org.jdom2.output.XMLOutputter;
import myClasses.*;

public class Receiver {

	public static void main(String[] args) {

		try {
			// Listen on port 8888
			ServerSocket serverSocket = new ServerSocket(8888);

			while(true) {
				System.out.println("waiting for object...");
				Socket socket = serverSocket.accept();

				DataInputStream inputStream = new DataInputStream(socket.getInputStream());;
			  byte[] response = new byte[50 * 1024];
				int retValue;
	      int loopCount = 0;

				while ((retValue = inputStream.read(response, loopCount, 512)) != -1) {
	        loopCount += retValue;
	      }

				String responseStr = new String(response, "UTF-8");
				if (responseStr.contains("done sending")) {
					System.out.println("shutting down server...");
					try { Thread.sleep(1500); }
					catch (InterruptedException e) { System.out.println(e); }
					break;
				}

				OutputStream fw = new FileOutputStream("received.xml");
				fw.write(response, 0, loopCount);
				fw.flush();

				inputStream.close();

				SAXBuilder builder = new SAXBuilder();
				Document jdomDoc = builder.build(new File("received.xml"));

				Serializer serializer = new Serializer();
				Deserializer des = new Deserializer();
				des.deserialize(jdomDoc);
				serializer.serialize(des.getObject());
				Document deserialDoc = serializer.getDocument();
				System.out.println("received object...");
				try { Thread.sleep(1500); }
				catch (InterruptedException e) { System.out.println(e); }
				Inspector inspector = new Inspector();
				inspector.inspect(des.getObject(), true);

				FileOutputStream fosDeserial = new FileOutputStream("Deserialized.xml");
				XMLOutputter xmlOut = new XMLOutputter();
				System.out.print("xml: ");
				xmlOut.output(deserialDoc, System.out);
				xmlOut.output(deserialDoc, fosDeserial);
				System.out.println("");
			}
		}
		catch (Exception e) {
			e.printStackTrace(System.out);
			System.out.println("Error: " + e.getMessage());
		}
	}
}
