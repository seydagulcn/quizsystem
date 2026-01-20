package seydaproje;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class CSVFileReader {

    public static void readCSV() {
        try {
            // CSV'yi classpath'ten al
            InputStream is = CSVFileReader.class
                    .getClassLoader()
                    .getResourceAsStream("data/ogrenciler.csv");

            if (is == null) {
                System.out.println("CSV dosyası bulunamadı!");
                return;
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String line;

            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                
                for (String v : values) {
                    System.out.print(v + " ");
                }
                System.out.println();
            }

            br.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
