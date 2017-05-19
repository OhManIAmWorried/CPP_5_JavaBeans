package src.xml;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import src.Data;
import src.DataSheet;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;

/**
 * Created by oleg on 07.05.2017.
 */
public class DataSheetToXML {

    private static Document document;
    private static Element root;

    private static void createDocument() {
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            document = documentBuilder.newDocument();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
    }

    private static Element createElementFromData(Data data) {
        String date = data.getDate();
        double x = data.getX();
        double y = data.getY();

        Element dataElement = document.createElement("data");
        Attr attr = document.createAttribute("date");
        attr.setValue(date); //TODO: date.trim()
        dataElement.setAttributeNode(attr);

        Element xElement = document.createElement("x");
        xElement.appendChild(document.createTextNode(Double.toString(x)));
        dataElement.appendChild(xElement);

        Element yElement = document.createElement("y");
        yElement.appendChild(document.createTextNode(Double.toString(y)));
        dataElement.appendChild(yElement);

        return dataElement;
    }

    /*Method from Laba4_XML guide (page 61)*/
    private static void writeDocumentToFile(String filePath) {
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        try {
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.ENCODING,"Windows-1251");
            transformer.setOutputProperty(OutputKeys.INDENT,"yes");
            DOMSource source = new DOMSource(document);
            StreamResult result = new StreamResult(new File(filePath));
            transformer.transform(source,result);
        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
        } catch (TransformerException e) {
            e.printStackTrace();
        }
    }

    public static void writeToFile(DataSheet dataSheet, String filePath) {
        createDocument();
        root = document.createElement("datasheet");
        document.appendChild(root);
        for (Data data : dataSheet.getDataArrayList()) {root.appendChild(createElementFromData(data));}
        writeDocumentToFile(filePath);
    }
}
