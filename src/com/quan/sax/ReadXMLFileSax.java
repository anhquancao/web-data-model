package com.quan.sax;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;




public class ReadXMLFileSax {
	public static void main(String[] args) {
		try {
			SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
			SAXParser saxParser = saxParserFactory.newSAXParser();
			
			DefaultHandler handler =  new DefaultHandler() {
				boolean bContribution = false;
				boolean bText = false;
				boolean bStatus = false;
				boolean bAssignedTo = false;
				
				public void startElement(String uri, String localName, String qName, Attributes attrs) 
				throws SAXException {
					System.out.println("Start element : " + qName);
					System.out.println(uri);
					System.out.println(localName);
					System.out.println(qName);
					if (qName.equalsIgnoreCase("contribution")) {
						bContribution = true;
						System.out.println("cname: " + attrs.getValue("cname"));
					}
					if (qName.equalsIgnoreCase("text")) {
						bText = true;
					}
					
					if (qName.equalsIgnoreCase("status")) {
						bStatus = true;
					}
					
					if (qName.equalsIgnoreCase("assignedTo")) {
						bAssignedTo = true;
					}
				}
				public void endElement(String uri, String localName,
						String qName) throws SAXException {

						System.out.println("End Element :" + qName);
					}
				
				
				public void characters(char ch[], int start, int length) throws SAXException {

					if (bText) {
						System.out.println("Text: " + new String(ch, start, length));
						bText = false;
					}

					if (bAssignedTo) {
						System.out.println("Assigned to : " + new String(ch, start, length));
						bAssignedTo = false;
					}

					if (bStatus) {
						System.out.println("Status : " + new String(ch, start, length));
						bStatus = false;
					}

				}
				
			};
			saxParser.parse("./tp1/tpfixed.xml", handler);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
}
