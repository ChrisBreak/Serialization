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

public class Sender {

	public static void main(String[] args) {

		Serializer serializer = new Serializer();
		Airplane aPlane = new Airplane();
		aPlane.setSeats(20);
		Airport yyc = new Airport();
		yyc.setLocation("Calgary");
		Pilot someP = new Pilot("John", new int[]{1,2,3}, yyc);
		aPlane.setPilot(someP);
		serializer.serialize(aPlane);
		Document serialDoc = serializer.getDocument();

		serializer = new Serializer();
		Deserializer des = new Deserializer();
		des.deserialize(serialDoc);
		serializer.serialize(des.getObject());
		Document deserialDoc = serializer.getDocument();

		FileOutputStream fosSerial = null;
		FileOutputStream fosDeserial = null;
		try {
			fosSerial = new FileOutputStream("Serialized.xml");
			fosDeserial = new FileOutputStream("Deserialized.xml");
			XMLOutputter xmlOut = new XMLOutputter();
			//xmlOut.output(doc, System.out);
			xmlOut.output(serialDoc, fosSerial);
			xmlOut.output(deserialDoc, fosDeserial);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		catch (IOException e) {
			System.err.println(e);
		}
	}
}
