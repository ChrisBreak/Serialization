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

		DataInputStream inputStream;
		ServerSocket serverSocket;

		try {
			// Listen on port 8888
			serverSocket = new ServerSocket(8888);

			Socket socket = serverSocket.accept();

			inputStream = new DataInputStream(socket.getInputStream());;
			String recStr = "";
			OutputStream fw = new FileOutputStream("received.xml");
		  byte[] response = new byte[50 * 1024];
			int retValue;
      int loopCount = 0;

			while ((retValue = inputStream.read(response, loopCount, 512)) != -1) {
        loopCount += retValue;
      }

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

			FileOutputStream fosDeserial = new FileOutputStream("Deserialized.xml");
			XMLOutputter xmlOut = new XMLOutputter();
			xmlOut.output(deserialDoc, fosDeserial);

		}
		catch (Exception e) {
			e.printStackTrace(System.out);
			System.out.println("Error: " + e.getMessage());
		}
	}
}
