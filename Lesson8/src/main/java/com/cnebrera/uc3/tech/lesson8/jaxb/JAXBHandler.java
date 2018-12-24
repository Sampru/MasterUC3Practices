package com.cnebrera.uc3.tech.lesson8.jaxb;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.SchemaOutputResolver;
import javax.xml.bind.Unmarshaller;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.xml.sax.SAXException;

import com.cnebrera.uc3.tech.lesson8.util.Constants;
import com.cnebrera.uc3.tech.lesson8.xjc.StudentLessons;

/**
 * JAXB Handler
 * --------------------------------------
 *
 * @author Francisco Manuel Benitez Chico
 * --------------------------------------
 */
public class JAXBHandler {
    /**
     * Attribute - JAXB Context
     */
    private final JAXBContext jaxbContext;

    /**
     * Initialize the JAXB Context
     *
     * @throws JAXBException with an occurred exception
     */
    public JAXBHandler() throws JAXBException {
        // Instantiate the context
        this.jaxbContext = JAXBContext.newInstance(StudentLessons.class);
    }

    /**
     * @param file with the file
     * @return a new instance of StudentLessons with the filled values from the XML
     * @throws JAXBException with an occurred exception
     * @throws SAXException  with an occurred exception
     */
    public StudentLessons convertToObject(final File file) throws JAXBException, SAXException {
        // Get the unmarshaller
        final Unmarshaller umar = this.jaxbContext.createUnmarshaller();

        // Set the XSD validator
        final SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        final Schema schema = sf.newSchema(new File(Constants.XSD_FILE_INPUT));
        umar.setSchema(schema);

        // Unmarshal the content of the file
        return (StudentLessons) umar.unmarshal(file);
    }

    /**
     * @param studentLessons with the instance of StudentLessons
     * @return a string with the XML content
     * @throws JAXBException with an occurred exception
     */
    public String convertToXml(final StudentLessons studentLessons) throws JAXBException {
        // Get the marshaller
        final Marshaller mar = this.jaxbContext.createMarshaller();

        // Output well formatetd XML
        mar.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

        // Output Stream
        final OutputStream os = new ByteArrayOutputStream();

        // Marshal the StudentLessons object
        mar.marshal(studentLessons, os);

        // Return the XML string
        return os.toString();
    }

    /**
     * @param namespaceUri      with the namespace URI
     * @param suggestedFileName with the suggested file name
     * @return the generated schema
     * @throws IOException with an occurred exception
     */
    public void generateSchema(final String namespaceUri, final String suggestedFileName) throws IOException {
        final SchemaOutputResolver schemaOutputResolver = new MySchemaOutputResolver();

        // Generate schema into the resolver
        this.jaxbContext.generateSchema(schemaOutputResolver);

        // Output to the selected file
        schemaOutputResolver.createOutput(namespaceUri, suggestedFileName);
    }
}
