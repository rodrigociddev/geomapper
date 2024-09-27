package com.example.geodemo.export;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.FileNotFoundException;
import java.io.IOException;

public interface Exporter {

    void export(String filePath, String fileName) throws TransformerException, ParserConfigurationException, IOException;
}
