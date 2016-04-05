package util;


import java.io.IOException;
import javax.xml.soap.*;
import javax.xml.transform.*;
import javax.xml.transform.stream.StreamResult;

/**
 *
 * @author lester.john
 */

public class SOAPConnect {
    
    public SOAPMessage SOAPConSend(String url) throws SOAPException, IOException {
        
        SOAPConnection soapConnection = SOAPConnectionFactory.newInstance().createConnection();
        SOAPMessage soapResponse = soapConnection.call(SOAPConObject("http://ws.cdyne.com/"),url);
        return soapResponse;
    
    }
    
    public SOAPMessage SOAPConObject(String serverURI) throws SOAPException, IOException {

        MessageFactory messageFactory = MessageFactory.newInstance();
        SOAPMessage soapMessage = messageFactory.createMessage();
        SOAPPart soapPart = soapMessage.getSOAPPart();

        // SOAP Envelope
        SOAPEnvelope envelope = soapPart.getEnvelope();
        envelope.addNamespaceDeclaration("example", serverURI);

        /*
        Constructed SOAP Request Message:
        <SOAP-ENV:Envelope xmlns:SOAP-ENV="http://schemas.xmlsoap.org/soap/envelope/" xmlns:example="http://ws.cdyne.com/">
            <SOAP-ENV:Header/>
            <SOAP-ENV:Body>
                <example:VerifyEmail>
                    <example:email>mutantninja@gmail.com</example:email>
                    <example:LicenseKey>123</example:LicenseKey>
                </example:VerifyEmail>
            </SOAP-ENV:Body>
        </SOAP-ENV:Envelope>
         */
        
        // SOAP Body
        SOAPBody soapBody = envelope.getBody();
        SOAPElement soapBodyElem = soapBody.addChildElement("VerifyEmail", "example");
        SOAPElement soapBodyElem1 = soapBodyElem.addChildElement("email", "example");
        soapBodyElem1.addTextNode("mutantninja@gmail.com");
        SOAPElement soapBodyElem2 = soapBodyElem.addChildElement("LicenseKey", "example");
        soapBodyElem2.addTextNode("123");

        MimeHeaders headers = soapMessage.getMimeHeaders();
        headers.addHeader("SOAPAction", serverURI  + "VerifyEmail");

        soapMessage.saveChanges();

        /* Print the request message */
        System.out.print("Request SOAP Message = ");
        soapMessage.writeTo(System.out);
        System.out.println();

        return soapMessage;

    }
    
    public void SOAPConResponse(SOAPMessage soapResponse) throws TransformerConfigurationException, SOAPException, TransformerException {

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        Source sourceContent = soapResponse.getSOAPPart().getContent();
        System.out.print("\nResponse SOAP Message = ");
        StreamResult result = new StreamResult(System.out);
        transformer.transform(sourceContent, result);

    }   
}