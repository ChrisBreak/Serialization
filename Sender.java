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
import java.util.Scanner;

public class Sender {

	public void start() {
		while(true) {
			System.out.println("\nChoose object to send: (enter letter)\n"
				+"A - object with primitive variables\n"
				+"B - object with array of primitives variable\n"
				+"C - object with object reference variable\n"
				+"D - object with array of object references variable\n"
				+"E - collection of object references\n"
				+"Q - to quit");

			String input = getUserInput();
			if (input.equalsIgnoreCase("q")) {
				shutdown();
				break;
			}
			input = input.toLowerCase();

			switch (input) {
				case "a":
					send(createAirplane());
					break;
				case "b":
					send(createPilot());
					break;
				case "c":
					send(createRunway());
					break;
				case "d":
					send(createAirport());
					break;
				case "e":
					send(createCollection());
					break;
				default:
					System.out.println("Invalid input");
			}
		}
	}

	private Object createAirplane() {
		System.out.println("object to send: Airplane(int id, int seats, boolean active)");
		System.out.println("enter id, seats, active ");
		String input = getUserInput();
		String[] inputArr = input.split(",");
		int id = (int) parsePrimitive(int.class, inputArr[0].trim());
		int seats = (int) parsePrimitive(int.class, inputArr[1].trim());
		boolean active = (boolean) parsePrimitive(boolean.class, inputArr[2].trim());
		Airplane airP = new Airplane(id, seats, active);
		return airP;
	}

	private Object createPilot() {
		System.out.println("object to send: Pilot(String name, int age, int[] ratings)");
		System.out.println("enter name, age, [ratings");
		String input = getUserInput();
		String[] inputArr = input.split(",", 3);
		String name = inputArr[0];
		int age = (int) parsePrimitive(int.class, inputArr[1].trim());
		String ratingStr = inputArr[2].trim();
		ratingStr = ratingStr.substring(1);
		String[] ratingArr = ratingStr.split(",");
		int[] ratings = new int[ratingArr.length];
		for (int i = 0; i < ratings.length; i++) {
			ratings[i] = (int) parsePrimitive(int.class, ratingArr[i].trim());
		}
		Pilot pilot = new Pilot(name, age, ratings);
		return pilot;
	}

	private Object createRunway() {
		System.out.println("object to send: Runway(int length, int width, Airport airport)");
		System.out.println("enter length, width");
		String input = getUserInput();
		String[] inputArr = input.split(",", 2);
		int length = (int) parsePrimitive(int.class, inputArr[0].trim());
		int width = (int) parsePrimitive(int.class, inputArr[1].trim());
		System.out.println("set Airport(String location)\nenter location");
		String location = getUserInput().trim();
		Airport airport = new Airport();
		airport.setLocation(location);
		Runway aRun = new Runway(length, width, airport);
		return aRun;
	}

	private Object createAirport() {
		return null;
	}

	private Object createCollection() {
		return null;
	}

	private void send(Object obj) {
		System.out.println("sending object...");
		try { Thread.sleep(1500); }
		catch (InterruptedException e) { System.out.println(e); }
		Serializer serializer = new Serializer();
		serializer.serialize(obj);
		Inspector inspector = new Inspector();
		inspector.inspect(obj, true);
		Document serialDoc = serializer.getDocument();
		System.out.println("sent object successfully\n");
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

	private String getUserInput() {
		Scanner sc = new Scanner(System.in);
		return sc.nextLine();
	}

	private Object parsePrimitive(Class objClass, String value) {
		if (value.equals("") || value.equals("null") || value == null) return null;
    if (objClass == int.class) return Integer.parseInt(value);
    else if (objClass == boolean.class) return Boolean.parseBoolean(value);
    else if (objClass == char.class) return value.charAt(0);
    else if (objClass == float.class) return Float.parseFloat(value);
    else if (objClass == double.class) return Double.parseDouble(value);
    else if (objClass == long.class) return Long.parseLong(value);
    else if (objClass == byte.class) return Byte.parseByte(value);
    else if (objClass == short.class) return Short.parseShort(value);
    return null;
  }

	private void shutdown() {
		try {
			PrintWriter outputStream;
			Socket socket = new Socket("localhost", 8888);
			outputStream = new PrintWriter(new DataOutputStream(socket.getOutputStream()));
			outputStream.println("done sending");
			outputStream.flush();
			outputStream.close();
			System.out.println("shutting down client...");
			try { Thread.sleep(1500); }
			catch (InterruptedException e) { System.out.println(e); }

		}
		catch (IOException e) {
			System.err.println(e);
		}
	}
}
