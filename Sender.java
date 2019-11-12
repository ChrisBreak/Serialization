//Cristhian Sotelo-Plaza
//CPSC501 FALL 2019
//A3

//compile: javac -cp .:jdom2/* *.java
//run: java -cp .:jdom2/* Sender

import org.jdom2.*;
import java.lang.reflect.*;
import org.jdom2.output.XMLOutputter;
import myClasses.*;
import java.io.*;
import java.net.*;

public class Sender {

	public static void main(String[] args) {

		Serializer serializer = new Serializer();
		Airplane aPlane = new Airplane();
		aPlane.setSeats(20);
		Airport yyc = new Airport();
		yyc.setLocation("Calgary");
		Pilot someP = new Pilot("John", 23, new int[]{1,2,3});
		Runway aRun = new Runway(1000, 200, yyc);
		Runway aRun2 = new Runway(1200, 240, yyc);
		Runway[] runways = new Runway[]{aRun, aRun2};
		yyc.setRunways(runways);
		serializer.serialize(yyc);
		Document serialDoc = serializer.getDocument();

		FileOutputStream fosSerial = null;
		PrintWriter outputStream;

		try {
			fosSerial = new FileOutputStream("Serialized.xml");
			XMLOutputter xmlOut = new XMLOutputter();
			//xmlOut.output(doc, System.out);
			xmlOut.output(serialDoc, fosSerial);

			Socket socket = new Socket("localhost", 8888);
			outputStream = new PrintWriter(new DataOutputStream(socket.getOutputStream()));
			xmlOut.output(serialDoc, outputStream);
			outputStream.flush();
			outputStream.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		catch (IOException e) {
			System.err.println(e);
		}
	}
}
