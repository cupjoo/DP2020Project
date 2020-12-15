package com.holub.database;

import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.assertEquals;

class XMLImporterTest {
    final String RESULT_FILE_NAME = "people_result.xml";
    final String EXIST_FILE_NAME = "people.xml";

    @Test
    void loadTableName() throws IOException {
        Table.Importer importer = getImporter();

        final String name = "people";
        String tableName = importer.loadTableName();
        assertEquals(name, tableName);
    }

    @Test
    void loadWidth() throws IOException {
        Table.Importer importer = getImporter();

        final int width = 3;
        int tableWidth = importer.loadWidth();
        assertEquals(width, width);
    }

    @Test
    void loadColumnNames() throws IOException {
        Table.Importer importer = getImporter();

        final String[] expections = {"last", "first", "addrId"};
        String[] columnNames = new String[importer.loadWidth()];
        Iterator columns = importer.loadColumnNames();
        for (int i = 0; columns.hasNext();) {
            columnNames[i++] = columns.next().toString();
        }

        int i = 0;
        for(String expect : expections){
            assertEquals(expect, columnNames[i++]);
        }
    }

    @Test
    void loadRow() throws IOException {
        Table.Importer importer = getImporter();

        int width = importer.loadWidth();
        Iterator columns = importer.loadColumnNames();

        String[] columnNames = new String[width];
        int i = 0;
        while(columns.hasNext()){
            columnNames[i++] = columns.next().toString();
        }

        Object[] current = new Object[width];
        while ((columns = importer.loadRow()) != null) {
            i = 0;
            while(columns.hasNext()){
                current[i++] = columns.next().toString();
            }
        }
        assertEquals("Flintstone", current[0].toString());
        assertEquals("Fred", current[1].toString());
        assertEquals("2", current[2].toString());
    }

    private Table.Importer getImporter() throws IOException {
        Reader in = new FileReader("people.xml");
        Table.Importer importer = new XMLImporter(in);
        importer.startTable();
        return importer;
    }
}