
package seydaproje;

import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {

        // CSV OKU
        CSVFileReader.readCSV();

        SwingUtilities.invokeLater(() -> {
            new ExamSystemGUI();
        });
    }
}
