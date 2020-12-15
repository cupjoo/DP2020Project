package com.holub.database;

import java.io.IOException;
import java.io.Writer;
import java.util.Iterator;

public class HTMLExporter implements Table.Exporter {
    private final Writer out;

    public HTMLExporter(Writer out){
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
        openTag(0, "html");
        openTag(0, "body");
    }

    public void storeMetadata(String tableName, int width, int height, Iterator columnNames) throws IOException {
        tableName = tableName == null ? "anonymous" : tableName;
        generateTag(1, "div", tableName);
        openTag(1, "table");
        openTag(2, "tr");
        while(columnNames.hasNext()){
            generateTag(3, "th", columnNames.next());
        }
        closeTag(2, "tr");
    }

    public void storeRow(Iterator data) throws IOException {
        openTag(2, "tr");
        while(data.hasNext()){
            generateTag(3, "td", data.next());
        }
        closeTag(2, "tr");
    }

    public void endTable() throws IOException {
        closeTag(1, "table");
        closeTag(0, "body");
        closeTag(0, "html");
    }
}
