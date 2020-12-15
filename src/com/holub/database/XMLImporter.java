package com.holub.database;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;

import static java.util.regex.Pattern.compile;

public class XMLImporter implements Table.Importer {
    final String BLANK = "---!---";
    private BufferedReader  in;
    private String tableName;
    private final List<String> columnNames = new ArrayList<>();

    public XMLImporter(Reader in) {
        this.in = in instanceof BufferedReader ?
                (BufferedReader) in : new BufferedReader(in);
    }

    private String parseTag(String line, String tag){
        String sb = "<" + tag + ">(.+?)</" + tag + ">";
        Matcher mt = compile(sb).matcher(line);
        return mt.find() ? mt.group(1) : BLANK;
    }

    public void startTable() throws IOException {
        in.readLine();
        in.readLine();

        tableName = parseTag(in.readLine(), "name");
        tableName = tableName.equals(BLANK) ? "anonymous" : tableName;

        in.readLine();
        while(true){
            String data = parseTag(in.readLine(), "data");
            if(data.equals(BLANK)){
                break;
            }
            columnNames.add(data);
        }
        in.readLine();
    }

    public String loadTableName() throws IOException {
        return tableName;
    }

    public int loadWidth() throws IOException {
        return columnNames.size();
    }

    public Iterator loadColumnNames() throws IOException {
        return columnNames.iterator();
    }

    public Iterator loadRow() throws IOException {
        List<String> row = new ArrayList<>();
        in.readLine();
        for(int i = 0; i < columnNames.size(); i++){
            String data = parseTag(in.readLine(), "col");
            if(data.equals(BLANK))  break;
            row.add(data);
        }
        in.readLine();
        return row.isEmpty() ? null : row.iterator();
    }

    public void endTable() throws IOException {

    }
}
