package com.example.geodemo.export;

public enum ExportFormat {
    KMZ("KmzExporter"), KML("KmlExporter"), PROJECT("ProjectExporter");

    private final String exporterName;

    ExportFormat(String format){
        this.exporterName = format;
    }
    public String getExporterName(){
        return exporterName;
    }
}
