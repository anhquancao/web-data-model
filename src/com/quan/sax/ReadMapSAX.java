package com.quan.sax;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class ReadMapSAX {
	public static void extractInfo(String title) throws Exception {
		URL url = new URL(
				"https://en.wikipedia.org/w/api.php?format=xml&action=query&prop=extracts&exintro=&explaintext=&titles="
						+ title);
		
		URLConnection connection = url.openConnection();
		
		DefaultHandler handler = new DefaultHandler() {
			boolean bExtract = false;
			public void startElement(String uri, String localName, String qName, Attributes attrs) {
				if (qName.equalsIgnoreCase("extract")) {
					bExtract = true;
				}
			}
			
			public void characters(char ch[], int start, int length) {
				if (bExtract) {
					String s = new String(ch, start, length);
					System.out.println(s);
					bExtract = false;
				}
				
				
			}
		};
		SAXParser saxParser = SAXParserFactory.newInstance().newSAXParser();
		saxParser.parse(connection.getInputStream(), handler);
	}
	
	public static void main(String[] args) {
		try {
			SAXParser saxParser = SAXParserFactory.newInstance().newSAXParser();
			DefaultHandler handler = new DefaultHandler() {
				
				@Override
				public void startElement(String uri, String localName, String qName, Attributes attrs) throws SAXException {
					if (qName.equalsIgnoreCase("tag")) {
						if (attrs.getValue("k").equalsIgnoreCase("name")) {
							try {
								String value = attrs.getValue("v");
								System.out.println(value);
								extractInfo(value.replaceAll(" ", "%20"));
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					}
				}
		
			};
			
			saxParser.parse("./tp1/map.osm", handler);
			
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
}
