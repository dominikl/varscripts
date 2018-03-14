
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.HashSet;

import static csv.CSVUtils.*;

public class ExtractColumns {

    public static void main(String[] args) throws Exception {

        /**
         * Splits a column containing multiple values separated by ';' into two.
         */

        String in = "/Users/dlindner/idr0040/annotations.csv";
        String out = "/Users/dlindner/idr0040/filepaths.csv";

        String[] columns = {"Dataset Name", "Comment [Image File Path]"};

        BufferedReader r = new BufferedReader(new FileReader(in));
        BufferedWriter w = new BufferedWriter(new FileWriter(out));
        
        HashSet<String> unique = new HashSet<String>();
        
        String[] headers = split(r.readLine());
        int[] index = new int[columns.length];
        for(int i=0; i<columns.length; i++) {
            for(int j=0; j<headers.length; j++) {
                if(headers[j].equals(columns[i])) {
                    index[i] = j;
                }
            }
        }
        
        String line = null;
        w.write(join(columns)+"\n");

        while ((line = r.readLine()) != null) {
            String parts[] = split(line);
            String[] outline = new String[columns.length];

            String tmp = "";
            for (int i = 0; i < columns.length; i++) {
                outline[i] = parts[index[i]];
                tmp += parts[index[i]];
            }

            if (!unique.contains(tmp)) {
                w.write(join(outline) + "\n");
                unique.add(tmp);
            }
        }
        
        r.close();
        w.close();
    }
}
