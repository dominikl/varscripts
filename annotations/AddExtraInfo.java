
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;

import static csv.CSVUtils.*;

/**
 * Parses a specific column (inputColumn) of a CSV file (see process() method) and 
 * depending on that writes something in the specified outputColumn.
 */
public class AddExtraInfo {

    public static void main(String[] args) throws Exception {

        String in = "/Users/dlindner/Repositories/idr-metadata/idr0040-aymoz-singlecell/experimentA/idr0040-experimentA-annotation.csv";
        String out = "/Users/dlindner/Repositories/idr-metadata/idr0040-aymoz-singlecell/experimentA/idr0040-experimentA-annotation_2.csv";

        String inputColumn = "Characteristics [Mating Type]";
        String outputColumn = "Term Source Accession";

        BufferedReader r = new BufferedReader(new FileReader(in));
        BufferedWriter w = new BufferedWriter(new FileWriter(out));

        int outputColumnIndex = -1;
        int inputColumnIndex = -1;

        // header
        String line = r.readLine();
        w.write(line + "\n");
        String[] parts = split(line);
        for (int i = 0; i < parts.length; i++) {
            if (parts[i].equals(outputColumn))
                outputColumnIndex = i;
            if (parts[i].equals(inputColumn))
                inputColumnIndex = i;
        }

        if (outputColumnIndex <= 0 || inputColumnIndex <= 0) {
            System.err.println("Couldn't identify input/output columns.");
            r.close();
            w.close();
            return;
        }

        while ((line = r.readLine()) != null) {
            parts = split(line);
            parts[outputColumnIndex] = process(parts[inputColumnIndex]);
            w.write(join(parts) + "\n");
        }
        r.close();
        w.close();
    }

    private static String process(String input) {
        // input will be: Mata X MATalpha
        // where Mata will be accession 1 and MATAlpha accession 2 (if present)
        
        String[] tmp = input.split("\\s+");
        
        String out = "";
        
        if (tmp[0].contains("Mata"))
            out = "EFO_0001275";
        if (tmp[0].contains("MATalpha"))
            out = "EFO_0001270";

        if (tmp.length > 1 ) {
            if (tmp[tmp.length - 1].contains("Mata"))
                out+= ";EFO_0001275";
            if (tmp[tmp.length - 1].contains("MATalpha"))
                out+= ";EFO_0001270";
        }
        
        return out;
    }
}
