package com.holub.database;

import java.io.IOException;
import java.io.Writer;
import java.util.Iterator;

public class XMLExporter implements Table.Exporter {
    private final Writer out;

    public XMLExporter(Writer out){
        this.out = out;
    }

    private void openTag(int tab, String tag) throws IOException {
        out.write("\t".repeat(tab) + "<" + tag + ">\n");
    }

    private void closeTag(int tab, String tag) throws IOException {
        out.write("\t".repeat(tab) + "</" + tag + ">\n");
    }

    private void generateTag(int tab, String tag, Object content) throws IOException {
        out.write("\t".repeat(tab) + "<" + tag + ">");
        out.write(content.toString());
        out.write("</" + tag + ">\n");
    }

    public void startTable() throws IOException {
        out.write("<?xml version=\"1.0\"?>\n");
        openTag(0, "table");
    }

    public void storeMetadata(String tableName, int width, int height, Iterator columnNames) throws IOException {
        tableName = tableName == null ? "anonymous" : tableName;
        generateTag(1, "name", tableName);
        openTag(1, "metadatas");
        while(columnNames.hasNext()){
            generateTag(2, "data", columnNames.next());
        }
        closeTag(1, "metadatas");
        openTag(1, "rows");
    }

    public void storeRow(Iterator data) throws IOException {
        openTag(2, "row");
        while(data.hasNext()){
            generateTag(3, "col", data.next());
        }
        closeTag(2, "row");
    }

    public void endTable() throws IOException {
        closeTag(1, "rows");
        closeTag(0, "table");
    }
}
