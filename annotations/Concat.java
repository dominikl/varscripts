
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;

import static csv.CSVUtils.*;

/**
 * Simply prefixes (or postfixes) the content of every cell 
 * in specified column (index).
 */
public class Concat {
    
    public static void main(String[] args) throws Exception {

        String in = "/Users/dlindner/idr0040/filepaths_2.csv";
        String out = "/Users/dlindner/idr0040/filepaths_3.csv";
        
        boolean prefix = true;
        
        String concat = "Dataset:name:";
        int index = 0;

        BufferedReader r = new BufferedReader(new FileReader(in));
        BufferedWriter w = new BufferedWriter(new FileWriter(out));

        w.write(r.readLine()+"\n"); // header
        
        String line = null;
        while ((line = r.readLine()) != null) {
            String parts[] = split(line);
            if (prefix)
                parts[index] = concat+parts[index];
            else
                parts[index] = parts[index]+concat;
            w.write(join(parts)+"\n");
        }
        
        r.close();
        w.close();
    }
}
