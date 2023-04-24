import javax.swing.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

class Titanic extends JFrame {
    private List<Passenger> passengers;

    public static void main(String[] args) {
        new Titanic();
    }

    //This constructor have a time complexity of O(n), because it calls the readData method.
    public Titanic() {
        this.setTitle("Titanic Passengers Data");
        ImageIcon titanic = new ImageIcon(Constants.PATH_TO_ICON);
        this.setIconImage(titanic.getImage());
        this.setLayout(null);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setSize(Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        File file = new File(Constants.PATH_TO_DATA_FILE);
        this.readData(file);
        this.add(new ManageScreen(0, 0, Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT, this.passengers));
        this.setVisible(true);
    }

    //The splitTool string makes the split method splitting only the commas of the csv file.
    //The -1 limit limits the split method from splitting one unnecessary comma.
    //The time complexity of this method is O(n), it mostly depends on the number of lines.
    public void readData(File file) {
        this.passengers = new ArrayList<>();
        try {
            FileReader fileReader = new FileReader(file);
            BufferedReader br = new BufferedReader(fileReader);
            br.readLine();
            String line;

            while ((line = br.readLine()) != null) {
                String splitTool = Constants.SPLIT_TOOL;
                String[] fields = line.split(splitTool, -1);
                Passenger passenger = new Passenger(fields[0], fields[1], fields[2], fields[3], fields[4], fields[5],
                        fields[6], fields[7], fields[8], fields[9], fields[10], fields[11]);
                this.passengers.add(passenger);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}