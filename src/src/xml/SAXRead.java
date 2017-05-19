package src.xml;

import src.DataSheet;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by oleg on 07.05.2017.
 */
public class SAXRead {

    /*Method from Laba4_XML, MainFrame class*/
    public static DataSheet parseFile(String path) {
        DataSheet dataSheet = null;
        SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
        try {
            SAXParser saxParser = saxParserFactory.newSAXParser();
            DataHandler dataHandler = new DataHandler();
            InputStream xmlInputStream = new FileInputStream(path);
            saxParser.parse(xmlInputStream, dataHandler);
            dataSheet = dataHandler.getDataSheet();
        } catch (org.xml.sax.SAXException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            return dataSheet;
        }
    }

}
