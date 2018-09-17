package com.quan.dom;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class ReadXMLFileDOM {

	public static void main(String[] args) {
		try {
			File xmlFile = new File("./tp1/tpfixed.xml");
			DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = builderFactory.newDocumentBuilder();
			Document doc = builder.parse(xmlFile);

			// optional, but recommended
			// read this -
			// http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
			doc.getDocumentElement().normalize();

			System.out.println("Root element: " + doc.getDocumentElement().getNodeName());
			NodeList nList = doc.getElementsByTagName("contribution");
			System.out.println("----------------------------");
			for (int i = 0; i < nList.getLength(); i++) {
				Node nNode = nList.item(i);
				System.out.println("\nCurrent element:" + nNode.getNodeName());
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					Element element = (Element) nNode;
					System.out.println("Cname: " + element.getAttribute("cname"));
					NodeList textElements = element.getElementsByTagName("text");
					for (int j = 0; j < textElements.getLength(); j++) {
						Element textElement = (Element) textElements.item(j);
						System.out.println("Date: " + textElement.getAttribute("date"));
						System.out.println("Text: " + textElement.getTextContent());
					}

				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
