package persistence;

import model.AppHistory;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

// Represents a writer that writes JSON representation of App History to file
// Code referenced from JsonSerializationDemo Starter
public class JsonWriter {
    private static final int TAB = 4;
    private final String destination;
    private PrintWriter writer;

    // EFFECTS: constructs writer to write to destination file
    public JsonWriter(String destination) {
        this.destination = destination;
    }


    // EFFECTS: opens writer; throws FileNotFoundException if destination file cannot
    // be opened for writing
    public void open() throws FileNotFoundException {
        writer = new PrintWriter(new File(destination));
    }


    // EFFECTS: writes JSON representation of workroom to file
    public void write(AppHistory a) {
        JSONObject json = a.toJson();
        saveToFile(json.toString(TAB));
    }


    // EFFECTS: closes writer
    public void close() {
        writer.close();
    }

    // EFFECTS: writes string to file
    private void saveToFile(String json) {
        writer.print(json);
    }
}

