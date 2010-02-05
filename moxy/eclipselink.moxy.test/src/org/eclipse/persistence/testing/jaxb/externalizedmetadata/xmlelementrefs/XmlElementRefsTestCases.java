/*******************************************************************************
 * Copyright (c) 1998, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 and Eclipse Distribution License v. 1.0
 * which accompanies this distribution.
 * The Eclipse Public License is available at http://www.eclipse.org/legal/epl-v10.html
 * and the Eclipse Distribution License is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * Contributors:
 * dmccann - December 04/2009 - 2.0 - Initial implementation
 ******************************************************************************/
package org.eclipse.persistence.testing.jaxb.externalizedmetadata.xmlelementrefs;

import java.io.File;
import java.io.InputStream;

import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.eclipse.persistence.testing.jaxb.externalizedmetadata.ExternalizedMetadataTestCases;
import org.w3c.dom.Document;

/**
 * Tests XmlElementRefs via eclipselink-oxm.xml
 *
 */
public class XmlElementRefsTestCases extends ExternalizedMetadataTestCases {
    private static final String CONTEXT_PATH = "org.eclipse.persistence.testing.jaxb.externalizedmetadata.xmlelementrefs";
    private static final String PATH = "org/eclipse/persistence/testing/jaxb/externalizedmetadata/xmlelementrefs/";
    
    /**
     * This is the preferred (and only) constructor.
     * 
     * @param name
     */
    public XmlElementRefsTestCases(String name) {
        super(name);
    }
    
    /**
     * Tests @XmlElementRefs schema generation via eclipselink-oxm.xml.  
     * 
     * Positive test.
     */
    public void testXmlElementRefsSchemaGen() {
        MySchemaOutputResolver outputResolver = generateSchema(CONTEXT_PATH, PATH, 2);
        // validate schema
        String controlSchema = PATH + "schema.xsd";
        compareSchemas(outputResolver.schemaFiles.get(EMPTY_NAMESPACE), new File(controlSchema));
    }

    /**
     * Tests @XmlElementRefs via eclipselink-oxm.xml.
     * 
     * Positive test.
     */
    public void testXmlElementRefs() {
        // load XML metadata
    	MySchemaOutputResolver outputResolver = generateSchema(new Class[] { Foos.class, ObjectFactory.class }, CONTEXT_PATH, PATH, 2);
          
        // load instance doc
        String src = PATH + "foos.xml";
        InputStream iDocStream = loader.getResourceAsStream(src);
        if (iDocStream == null) {
            fail("Couldn't load instance doc [" + src + "]");
        }

        // unmarshal
        Object obj = null;
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        try {
            obj = unmarshaller.unmarshal(iDocStream);
            assertFalse("Unmarshalled object is null.", obj == null);
        } catch (JAXBException e) {
            e.printStackTrace();
            fail("Unmarshal operation failed.");
        }

        Document testDoc = parser.newDocument();
        Document ctrlDoc = parser.newDocument();
        try {
            ctrlDoc = getControlDocument(src);
        } catch (Exception e) {
            e.printStackTrace();
            fail("An unexpected exception occurred loading control document [" + src + "].");
        }

        // marshal
        Marshaller marshaller = jaxbContext.createMarshaller();
        try {
            marshaller.marshal(obj, testDoc);
            assertTrue("Document comparison failed unxepectedly: ", compareDocuments(ctrlDoc, testDoc));
        } catch (JAXBException e) {
            e.printStackTrace();
            fail("Unmarshal operation failed.");
        }
    }
}
