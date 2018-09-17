package com.quan.dom;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class ReadMapDOM {
	private static void extractInformation(String title) throws Exception {
		DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();

		URL url = new URL(
				"https://en.wikipedia.org/w/api.php?format=xml&action=query&prop=extracts&exintro=&explaintext=&titles="
						+ title);
		URLConnection connection = url.openConnection();
		connection.connect();
		System.out.println("Connected");

		Document wikiDoc = builder.parse(connection.getInputStream());
		wikiDoc.getDocumentElement().normalize();
		Element extract = (Element) wikiDoc.getElementsByTagName("extract").item(0);

		if (extract != null)
			System.out.println(extract.getTextContent());
	}

	public static void main(String[] args) {
		File mapFile = new File("./tp1/map.osm");
		List<String> names = new ArrayList<>();
		try {
			DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = builderFactory.newDocumentBuilder();
			Document doc = builder.parse(mapFile);

			doc.getDocumentElement().normalize();
			NodeList nList = doc.getElementsByTagName("tag");
			for (int i = 0; i < nList.getLength(); i++) {
				Element tagElement = (Element) nList.item(i);
				if (tagElement.getAttribute("k").equalsIgnoreCase("name")) {
					String value = tagElement.getAttribute("v");
					System.out.println("v: " + value);
					extractInformation(value.replaceAll(" ", "%20"));
					names.add(tagElement.getAttribute("v"));
				}
			}

		} catch (Exception e) {

		}
	}
}
