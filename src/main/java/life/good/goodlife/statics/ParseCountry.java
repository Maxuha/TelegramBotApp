package life.good.goodlife.statics;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class ParseCountry {
    public static String getNameCountryByCode(String code) {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(new File(Objects.requireNonNull(ParseCountry.class.getClassLoader().getResource("data/country.xml")).getFile()));
            NodeList countryNodeList = document.getElementsByTagName("country");
            for (int i = 0; i < countryNodeList.getLength(); i++) {
                Element countryElement = (Element) countryNodeList.item(i);
                Element codeElement = (Element) countryElement.getElementsByTagName("alpha2").item(0);
                Element nameElement = (Element) countryElement.getElementsByTagName("name").item(0);
                if (codeElement.getTextContent().trim().equals(code)) {
                    return nameElement.getTextContent().trim();
                }
            }
        } catch (ParserConfigurationException | SAXException | IOException e) {
            System.out.println("Неудалось получить информацию об стране - " + e.getMessage());
        }
        return "Not Found";
    }
}
