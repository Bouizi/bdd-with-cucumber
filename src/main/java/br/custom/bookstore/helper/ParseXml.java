package br.custom.bookstore.helper;

import java.io.IOException;
import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Node;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.w3c.dom.CharacterData;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class ParseXml {

	private Element element;

	public ParseXml(String xml) throws ParserConfigurationException, SAXException, IOException {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		InputSource src = new InputSource();
		src.setCharacterStream(new StringReader(xml));
		Document document = db.parse(src);
		this.element = document.getDocumentElement();
	}

	public String getString(String tagName) throws Exception {
		NodeList list = this.element.getElementsByTagName(tagName);
		if (list != null && list.getLength() > 0) {
			NodeList subList = list.item(0).getChildNodes();
			if (subList != null && subList.getLength() > 0) {
				return subList.item(0).getNodeValue();
			}
		}
		throw new Exception("Nenhum valor retornado");
	}

	public static String findString(NodeList list, String tagName) throws Exception {
		if (list.getLength() > 0) {
			for (int k = 0; k < list.getLength(); k++) {
				Node node = list.item(k);
				if (node.getNodeName().equalsIgnoreCase(tagName)) {
					return node.getTextContent();
				} else if(node.hasChildNodes()) {
					String subNode = findString(node.getChildNodes(), tagName);
					if (subNode.equals("")) {
						continue;
					} else {
						return subNode;
					}
					
				}
			}
			return "";
		}
		return "";
	}

}
