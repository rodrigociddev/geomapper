package com.example.geodemo.export;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Describes an Exporter
 * Implemented by:
 * KmlExporter
 * KmzExporter
 * ProjectExporter
 */
public interface Exporter {

    ByteArrayOutputStream export(String filePath, String fileName) throws TransformerException, ParserConfigurationException, IOException;
}
