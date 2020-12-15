package com.holub.database;

import org.junit.jupiter.api.Test;

import java.io.*;

import static org.junit.jupiter.api.Assertions.*;

class XMLExporterTest {
    final String RESULT_FILE_NAME = "people_result.xml";
    final String EXIST_FILE_NAME = "people.xml";

    @Test
    void testImport() throws IOException {
        // from existing xml to table
        Table table = readTable(EXIST_FILE_NAME);

        // from table to another xml
        Writer writer = new FileWriter(RESULT_FILE_NAME);
        table.export(new XMLExporter(writer));
        writer.close();

        Table createdTable = readTable(RESULT_FILE_NAME);
        assertEquals(table.toString(), createdTable.toString());
    }

    private Table readTable(String name) throws IOException {
        Reader reader = new FileReader(name);
        Table table = new ConcreteTable(new XMLImporter(reader));
        reader.close();
        return table;
    }
}