package pl.damiankotynia.fourinrow.server.service;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.print.Doc;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class Configuration {
    private static Configuration instance;
    private int port;
    private int width;
    private int height;

    Object element;
    private Configuration() throws Exception {
        File file = new File("config.xml");
        DocumentBuilder documentBuilder = null;
        int flag = 0;
        try {
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();

            documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(file);
            document.getDocumentElement().normalize();
            port = Integer.valueOf(document.getElementsByTagName("ServerSocket").item(0).getAttributes().getNamedItem("value").getTextContent());
            Node node =  document.getElementsByTagName("GameParams").item(0);

            for (int i = 0; i < node.getChildNodes().getLength(); i++){
                if(node.getChildNodes().item(i).getNodeType() == Node.ELEMENT_NODE){
                    if(node.getChildNodes().item(i).getNodeName().equals("Width")){
                        width = Integer.valueOf(node.getChildNodes().item(i).getTextContent());
                        flag++;
                    } else if(node.getChildNodes().item(i).getNodeName().equals("Height")){
                        height = Integer.valueOf(node.getChildNodes().item(i).getTextContent());
                        flag++;
                    }
                }
            }
            if(flag!=2)
                throw new Exception();
        } catch (Exception e) {
            throw e;

        }
    }

    /**
     * Get instance of configuration class
     * @return instance of Configuration class
     * @throws Exception while given configuration file is malformed
     */
    public static Configuration getInstance() throws Exception{
        if(instance == null){
            instance = new Configuration();
            return instance;
        }else{
            return instance;
        }
    }
}
