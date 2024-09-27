package com.example.geodemo.export;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;

public interface Exporter {

    void export(String filePath, String fileName) throws TransformerException, ParserConfigurationException;
}
