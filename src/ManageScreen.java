import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.io.FileWriter;
import java.io.BufferedWriter;

public class ManageScreen extends JPanel {
    private JComboBox<String> survivedComboBox;
    private JTextField passengerIdMinTextField;
    private JTextField passengerIdMaxTextField;
    private JTextField passengerNameTextField;
    private JComboBox<String> genderComboBox;
    private JTextField siblingsSpousesTextField;
    private JTextField parentsChildrenTextField;
    private JTextField ticketNumberTextField;
    private JTextField ticketMinPriceTextField;
    private JTextField ticketMaxPriceTextField;
    private JTextField cabinNumberTextField;
    private JComboBox<String> portComboBox;
    private List<Passenger> passengers;
    private int lastOutFileNumber;

    //The time complexity of the current method is O(n),
    // because all the filtering process performed in one loop.
    public List<Passenger> filterPassengers(List<Passenger> passengers) {
        this.passengers = passengers;
        List<Passenger> filteredPassengers = new ArrayList<>();

        String passengerClass = this.survivedComboBox.getSelectedItem().toString();
        String passengerMinNum = this.passengerIdMinTextField.getText();
        String passengerMaxNum = this.passengerIdMaxTextField.getText();
        String name = this.passengerNameTextField.getText();
        String gender = this.genderComboBox.getSelectedItem().toString();
        String siblingsSpouses = this.siblingsSpousesTextField.getText();
        String parentsChildren = this.parentsChildrenTextField.getText();
        String cardNumber = this.ticketNumberTextField.getText();
        String ticketMinCost = this.ticketMinPriceTextField.getText();
        String ticketMaxCost = this.ticketMaxPriceTextField.getText();
        String cellNumber = this.cabinNumberTextField.getText();
        String port = this.portComboBox.getSelectedItem().toString();

        for (Passenger passenger : this.passengers) {
            if (!passengerClass.equals("All") && !passenger.getPClass().equals(passengerClass)) {
                continue;
            }
            if (!passengerMinNum.isEmpty() && Integer.parseInt(passengerMinNum) > Integer.parseInt(passenger.getPassengerId())) {
                continue;
            }
            if (!passengerMaxNum.isEmpty() && Integer.parseInt(passengerMaxNum) < Integer.parseInt(passenger.getPassengerId())) {
                continue;
            }
            if (!name.isEmpty() && !passenger.getName().contains(name)) {
                continue;
            }
            if (!gender.equals("All") && !passenger.getSex().equals(gender)) {
                continue;
            }
            if (!siblingsSpouses.isEmpty() && !passenger.getSibSp().equals(siblingsSpouses)) {
                continue;
            }
            if (!parentsChildren.isEmpty() && !passenger.getParch().equals(parentsChildren)) {
                continue;
            }
            if (!cardNumber.isEmpty() && !passenger.getTicket().equals(cardNumber)) {
                continue;
            }
            if (!ticketMinCost.isEmpty() && Float.parseFloat(ticketMinCost) > Float.parseFloat(passenger.getFare())) {
                continue;
            }
            if (!ticketMaxCost.isEmpty() && Float.parseFloat(ticketMaxCost) < Float.parseFloat(passenger.getFare())) {
                continue;
            }
            if (!cellNumber.isEmpty() && !passenger.getCabin().equals(cellNumber.toUpperCase())) {
                continue;
            }
            if (!port.equals("All") && !passenger.getEmbarked().equals(port)) {
                continue;
            }
            filteredPassengers.add(passenger);
        }
        createOutFilteredFile(filteredPassengers);
        return filteredPassengers;
    }

    //The time complexity of the current method is O(n*log n) because of the sort method.
    public void createOutFilteredFile(List<Passenger> filteredPassengers) {
        filteredPassengers.sort(Comparator.comparing(Passenger::getFormattedName));
        this.lastOutFileNumber = this.lastOutFileNumber + 1;
        String fileName = Constants.PATH_TO_OUT_FILE + this.lastOutFileNumber + ".csv";
        try {
            FileWriter fileWriter = new FileWriter(fileName);
            BufferedWriter bwr = new BufferedWriter(fileWriter);
            for (Passenger passenger : filteredPassengers) {
                bwr.write(passenger.getPassengerString());
            }
            bwr.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    //The time complexity of the current method is O(n).
    public int countSurvivors(List<Passenger> filteredPassengers) {
        int count = 0;
        for (Passenger passenger : filteredPassengers) {
            if (passenger.isSurvivor()) {
                count++;
            }
        }
        return count;
    }

    //The time complexity of the current constructor is O(1), because it does not have any loops, and it is just adding components to the panel.
    public ManageScreen(int x, int y, int width, int height, List<Passenger> passengers) {
        File file = new File(Constants.PATH_TO_DATA_FILE); //this is the path to the data file
        lastOutFileNumber = Constants.ZERO;
        if (file.exists()) {
            this.setLayout(null);
            this.setBounds(x, y, width, height);
            this.passengers = passengers;

            JLabel survivedLabel = new JLabel("Passenger Class: ");
            survivedLabel.setBounds(x + Constants.MARGIN_FROM_TOP, y, Constants.LABEL_WIDTH, Constants.LABEL_HEIGHT);
            this.add(survivedLabel);
            this.survivedComboBox = new JComboBox<>(Constants.PASSENGER_CLASS_OPTIONS);
            this.survivedComboBox.setBounds(survivedLabel.getX() + survivedLabel.getWidth() + Constants.SPACE, survivedLabel.getY(), Constants.COMBO_OR_TEXT_BOX_WIDTH, Constants.COMBO_OR_TEXT_BOX_HEIGHT);
            this.add(this.survivedComboBox);

            JLabel passengerMinIdLabel = new JLabel("Passenger Min ID:");
            passengerMinIdLabel.setBounds(Constants.MARGIN_FROM_LEFT, survivedLabel.getY() + survivedLabel.getHeight() + Constants.MARGIN_FROM_TOP, Constants.LABEL_WIDTH, Constants.LABEL_HEIGHT);
            this.add(passengerMinIdLabel);
            this.passengerIdMinTextField = new JTextField();
            this.passengerIdMinTextField.setBounds(passengerMinIdLabel.getX() + passengerMinIdLabel.getWidth() + Constants.SPACE, passengerMinIdLabel.getY(), Constants.COMBO_OR_TEXT_BOX_WIDTH, Constants.COMBO_OR_TEXT_BOX_HEIGHT);
            this.add(this.passengerIdMinTextField);

            JLabel passengerMaxIdLabel = new JLabel("Passenger Max ID:");
            passengerMaxIdLabel.setBounds(Constants.MARGIN_FROM_LEFT, passengerMinIdLabel.getY() + passengerMinIdLabel.getHeight() + Constants.MARGIN_FROM_TOP, Constants.LABEL_WIDTH, Constants.LABEL_HEIGHT);
            this.add(passengerMaxIdLabel);
            this.passengerIdMaxTextField = new JTextField();
            this.passengerIdMaxTextField.setBounds(passengerMaxIdLabel.getX() + passengerMaxIdLabel.getWidth() + Constants.SPACE, passengerMaxIdLabel.getY(), Constants.COMBO_OR_TEXT_BOX_WIDTH, Constants.COMBO_OR_TEXT_BOX_HEIGHT);
            this.add(this.passengerIdMaxTextField);


            JLabel passengerNameLabel = new JLabel("Passenger Name:");
            passengerNameLabel.setBounds(passengerMaxIdLabel.getX(), passengerMaxIdLabel.getY() + passengerMaxIdLabel.getHeight() + Constants.MARGIN_FROM_LEFT, Constants.LABEL_WIDTH, Constants.LABEL_HEIGHT);
            this.add(passengerNameLabel);
            this.passengerNameTextField = new JTextField();
            this.passengerNameTextField.setBounds(passengerNameLabel.getX() + passengerNameLabel.getWidth() + Constants.SPACE, passengerNameLabel.getY(), Constants.COMBO_OR_TEXT_BOX_WIDTH, Constants.COMBO_OR_TEXT_BOX_HEIGHT);
            this.add(this.passengerNameTextField);

            JLabel genderLabel = new JLabel("Gender:");
            genderLabel.setBounds(passengerNameLabel.getX(), passengerNameLabel.getY() + passengerNameLabel.getHeight() + Constants.MARGIN_FROM_LEFT, Constants.LABEL_WIDTH, Constants.LABEL_HEIGHT);
            this.add(genderLabel);
            this.genderComboBox = new JComboBox<>(Constants.GENDER_OPTIONS);
            this.genderComboBox.setBounds(genderLabel.getX() + genderLabel.getWidth() + Constants.SPACE, genderLabel.getY(), Constants.COMBO_OR_TEXT_BOX_WIDTH, Constants.COMBO_OR_TEXT_BOX_HEIGHT);
            this.add(this.genderComboBox);

            JLabel siblingsSpousesLabel = new JLabel("Siblings/Spouses:");
            siblingsSpousesLabel.setBounds(genderLabel.getX(), genderLabel.getY() + genderLabel.getHeight() + Constants.MARGIN_FROM_LEFT, Constants.LABEL_WIDTH, Constants.LABEL_HEIGHT);
            this.add(siblingsSpousesLabel);
            this.siblingsSpousesTextField = new JTextField();
            this.siblingsSpousesTextField.setBounds(siblingsSpousesLabel.getX() + siblingsSpousesLabel.getWidth() + Constants.SPACE, siblingsSpousesLabel.getY(), Constants.COMBO_OR_TEXT_BOX_WIDTH, Constants.COMBO_OR_TEXT_BOX_HEIGHT);
            this.add(this.siblingsSpousesTextField);

            JLabel parentsChildrenLabel = new JLabel("Parents/Children:");
            parentsChildrenLabel.setBounds(siblingsSpousesLabel.getX(), siblingsSpousesLabel.getY() + siblingsSpousesLabel.getHeight() + Constants.MARGIN_FROM_LEFT, Constants.LABEL_WIDTH, Constants.LABEL_HEIGHT);
            this.add(parentsChildrenLabel);
            this.parentsChildrenTextField = new JTextField();
            this.parentsChildrenTextField.setBounds(siblingsSpousesLabel.getX() + siblingsSpousesLabel.getWidth() + Constants.SPACE, this.siblingsSpousesTextField.getY() + this.siblingsSpousesTextField.getHeight() + Constants.MARGIN_FROM_LEFT, Constants.COMBO_OR_TEXT_BOX_WIDTH, Constants.COMBO_OR_TEXT_BOX_HEIGHT);
            this.add(this.parentsChildrenTextField);

            JLabel ticketNumberLabel = new JLabel("Ticket Number:");
            ticketNumberLabel.setBounds(parentsChildrenLabel.getX(), parentsChildrenLabel.getY() + parentsChildrenLabel.getHeight() + Constants.MARGIN_FROM_LEFT, Constants.LABEL_WIDTH, Constants.LABEL_HEIGHT);
            this.add(ticketNumberLabel);
            this.ticketNumberTextField = new JTextField();
            this.ticketNumberTextField.setBounds(siblingsSpousesLabel.getX() + siblingsSpousesLabel.getWidth() + Constants.SPACE, this.parentsChildrenTextField.getY() + this.parentsChildrenTextField.getHeight() + Constants.MARGIN_FROM_LEFT, Constants.COMBO_OR_TEXT_BOX_WIDTH, Constants.COMBO_OR_TEXT_BOX_HEIGHT);
            this.add(this.ticketNumberTextField);

            JLabel ticketMinPriceLabel = new JLabel("Ticket Min Price:");
            ticketMinPriceLabel.setBounds(ticketNumberLabel.getX(), ticketNumberLabel.getY() + ticketNumberLabel.getHeight() + Constants.MARGIN_FROM_LEFT, Constants.LABEL_WIDTH, Constants.LABEL_HEIGHT);
            this.add(ticketMinPriceLabel);
            this.ticketMinPriceTextField = new JTextField();
            this.ticketMinPriceTextField.setBounds(siblingsSpousesLabel.getX() + siblingsSpousesLabel.getWidth() + Constants.SPACE, this.ticketNumberTextField.getY() + this.ticketNumberTextField.getHeight() + Constants.MARGIN_FROM_LEFT, Constants.COMBO_OR_TEXT_BOX_WIDTH, Constants.COMBO_OR_TEXT_BOX_HEIGHT);
            this.add(this.ticketMinPriceTextField);

            JLabel ticketMaxPriceLabel = new JLabel("Ticket Max Price:");
            ticketMaxPriceLabel.setBounds(ticketMinPriceLabel.getX(), ticketMinPriceLabel.getY() + ticketMinPriceLabel.getHeight() + Constants.MARGIN_FROM_LEFT, Constants.LABEL_WIDTH, Constants.LABEL_HEIGHT);
            this.add(ticketMaxPriceLabel);
            this.ticketMaxPriceTextField = new JTextField();
            this.ticketMaxPriceTextField.setBounds(siblingsSpousesLabel.getX() + siblingsSpousesLabel.getWidth() + Constants.SPACE, this.ticketMinPriceTextField.getY() + this.ticketMinPriceTextField.getHeight() + Constants.MARGIN_FROM_LEFT, Constants.COMBO_OR_TEXT_BOX_WIDTH, Constants.COMBO_OR_TEXT_BOX_HEIGHT);
            this.add(this.ticketMaxPriceTextField);

            JLabel cabinNumberLabel = new JLabel("Cabin Number:");
            cabinNumberLabel.setBounds(ticketMaxPriceLabel.getX(), ticketMaxPriceLabel.getY() + ticketMaxPriceLabel.getHeight() + Constants.MARGIN_FROM_LEFT, Constants.LABEL_WIDTH, Constants.LABEL_HEIGHT);
            this.add(cabinNumberLabel);
            this.cabinNumberTextField = new JTextField();
            this.cabinNumberTextField.setBounds(siblingsSpousesLabel.getX() + siblingsSpousesLabel.getWidth() + Constants.SPACE, this.ticketMaxPriceTextField.getY() + this.ticketMaxPriceTextField.getHeight() + Constants.MARGIN_FROM_LEFT, Constants.COMBO_OR_TEXT_BOX_WIDTH, Constants.COMBO_OR_TEXT_BOX_HEIGHT);
            this.add(this.cabinNumberTextField);

            JLabel portLabel = new JLabel("Port:");
            portLabel.setBounds(cabinNumberLabel.getX(), cabinNumberLabel.getY() + cabinNumberLabel.getHeight() + Constants.MARGIN_FROM_LEFT, Constants.LABEL_WIDTH, Constants.LABEL_HEIGHT);
            this.add(portLabel);
            this.portComboBox = new JComboBox<>(Constants.PORT_OPTIONS);
            this.portComboBox.setBounds(siblingsSpousesLabel.getX() + siblingsSpousesLabel.getWidth() + Constants.SPACE, this.cabinNumberTextField.getY() + this.cabinNumberTextField.getHeight() + Constants.MARGIN_FROM_LEFT, Constants.COMBO_OR_TEXT_BOX_WIDTH, Constants.COMBO_OR_TEXT_BOX_HEIGHT);
            this.add(this.portComboBox);

            JButton filterButton = new JButton("Filter");
            filterButton.setSize(Constants.BUTTON_WIDTH, Constants.BUTTON_HEIGHT);


            filterButton.addActionListener(e -> {
                List<Passenger> filteredPassengers = filterPassengers(this.passengers);

                int totalRows = filteredPassengers.size();
                int survivedCount = countSurvivors(filteredPassengers);
                int notSurvivedCount = totalRows - survivedCount;
                String message = String.format("Total Rows: " + totalRows + " ( " + survivedCount + " survived, " + notSurvivedCount + " did not)" + "\n"
                        + "csv file created, to read it please close the application.");
                JOptionPane.showMessageDialog(ManageScreen.this, message);
            });
            this.add(filterButton);
            filterButton.setLocation(50, 450);
            filterButton.setVisible(true);
            JButton statisticsButton = new JButton("Create statistics file");
            statisticsButton.setSize(Constants.BUTTON_WIDTH, Constants.BUTTON_HEIGHT);

            statisticsButton.addActionListener(e -> {
                if (createSurvivorsStats()) {
                    JOptionPane.showMessageDialog(null, "Statistics file created successfully, to read it please close the application.", "Success", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, "Error creating statistics file.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            });

            this.add(statisticsButton);
            statisticsButton.setLocation(300, 450);
            statisticsButton.setVisible(true);

            ImageIcon backgroundImage = new ImageIcon(Constants.PATH_TO_BACKGROUND);
            JLabel background = new JLabel(backgroundImage);
            background.setSize(Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT);
            this.add(background);
        }
    }

    //The time complexity of the current constructor is O(n), it mostly depends on the passengers list size.
    //I put the following integers into an array to prevent duplicate code fragments.
    public boolean createSurvivorsStats() {
        int[] ints = new int[38];
        int class1Count = ints[0];
        int class1Total = ints[1];
        int class2Count = ints[2];
        int class2Total = ints[3];
        int class3Count = ints[4];
        int class3Total = ints[5];
        int maleCount = ints[6];
        int maleTotal = ints[7];
        int femaleCount = ints[8];
        int femaleTotal = ints[9];
        int zeroToTenTotal = ints[10];
        int zeroToTenCount = ints[11];
        int elevenToTwentyTotal = ints[12];
        int elevenToTwentyCount = ints[13];
        int twentyOneToThirtyTotal = ints[14];
        int twentyOneToThirtyCount = ints[15];
        int thirtyOneToFortyTotal = ints[16];
        int thirtyOneToFortyCount = ints[17];
        int fortyOneToFiftyTotal = ints[18];
        int fortyOneToFiftyCount = ints[19];
        int fiftyOnePlusTotal = ints[20];
        int fiftyOnePlusCount = ints[21];
        int lonelyPassengerTotal = ints[22];
        int lonelyPassengerCount = ints[23];
        int familyPassengerTotal = ints[24];
        int familyPassengerCount = ints[25];
        int cheapFareTotal = ints[26];
        int cheapFareCount = ints[27];
        int normalFareTotal = ints[28];
        int normalFareCount = ints[29];
        int expFareTotal = ints[30];
        int expFareCount = ints[31];
        int portSTotal = ints[32];
        int portSCount = ints[33];
        int portQTotal = ints[34];
        int portQCount = ints[35];
        int portCTotal = ints[36];
        int portCCount = ints[37];
        for (Passenger passenger : this.passengers) {
            if (passenger.getPClass().equals("1")) {
                class1Total++;
                if (passenger.isSurvivor()) {
                    class1Count++;
                }
            }
            if (passenger.getPClass().equals("2")) {
                class2Total++;
                if (passenger.isSurvivor()) {
                    class2Count++;
                }
            }
            if (passenger.getPClass().equals("3")) {
                class3Total++;
                if (passenger.isSurvivor()) {
                    class3Count++;
                }
            }
            if (passenger.getSex().equals("male")) {
                maleTotal++;
                if (passenger.isSurvivor()) {
                    maleCount++;
                }
            }
            if (passenger.getSex().equals("female")) {
                femaleTotal++;
                if (passenger.isSurvivor()) {
                    femaleCount++;
                }
            }
            if (!passenger.getAge().equals("")) {
                if (Double.parseDouble(passenger.getAge()) >= 0 && Double.parseDouble(passenger.getAge()) <= 10) {
                    zeroToTenTotal++;
                    if (passenger.isSurvivor()) {
                        zeroToTenCount++;
                    }
                }
                if (Double.parseDouble(passenger.getAge()) >= 11 && Double.parseDouble(passenger.getAge()) <= 20) {
                    elevenToTwentyTotal++;
                    if (passenger.isSurvivor()) {
                        elevenToTwentyCount++;
                    }
                }
                if (Double.parseDouble(passenger.getAge()) >= 21 && Double.parseDouble(passenger.getAge()) <= 30) {
                    twentyOneToThirtyTotal++;
                    if (passenger.isSurvivor()) {
                        twentyOneToThirtyCount++;
                    }
                }
                if (Double.parseDouble(passenger.getAge()) >= 31 && Double.parseDouble(passenger.getAge()) <= 40) {
                    thirtyOneToFortyTotal++;
                    if (passenger.isSurvivor()) {
                        thirtyOneToFortyCount++;
                    }
                }
                if (Double.parseDouble(passenger.getAge()) >= 41 && Double.parseDouble(passenger.getAge()) <= 50) {
                    fortyOneToFiftyTotal++;
                    if (passenger.isSurvivor()) {
                        fortyOneToFiftyCount++;
                    }
                }
                if (Double.parseDouble(passenger.getAge()) >= 51) {
                    fiftyOnePlusTotal++;
                    if (passenger.isSurvivor()) {
                        fiftyOnePlusCount++;
                    }
                }
            }
            if (Double.parseDouble(passenger.getSibSp()) + Double.parseDouble(passenger.getParch()) == 0) {
                lonelyPassengerTotal++;
                if (passenger.isSurvivor()) {
                    lonelyPassengerCount++;
                }
            } else {
                familyPassengerTotal++;
                if (passenger.isSurvivor()) {
                    familyPassengerCount++;
                }
            }

            if (Double.parseDouble(passenger.getFare()) <= 10) {
                cheapFareTotal++;
                if (passenger.isSurvivor()) {
                    cheapFareCount++;
                }
            }


            if (Double.parseDouble(passenger.getFare()) >= 11 && Double.parseDouble(passenger.getFare()) <= 30) {
                normalFareTotal++;
                if (passenger.isSurvivor()) {
                    normalFareCount++;
                }
            }
            if (Double.parseDouble(passenger.getFare()) >= 30) {
                expFareTotal++;
                if (passenger.isSurvivor()) {
                    expFareCount++;
                }
            }


            if (passenger.getEmbarked().equals("S")) {
                portSTotal++;
                if (passenger.isSurvivor()) {
                    portSCount++;
                }
            }
            if (passenger.getEmbarked().equals("Q")) {
                portQTotal++;
                if (passenger.isSurvivor()) {
                    portQCount++;
                }

            }
            if (passenger.getEmbarked().equals("C")) {
                portCTotal++;
                if (passenger.isSurvivor()) {
                    portCCount++;
                }
            }

        }
        try {
            FileWriter fileWriter = new FileWriter(Constants.PATH_TO_STATISTICS_FILE);
            BufferedWriter bwr = new BufferedWriter(fileWriter);
            String class1Percent = String.format("%.2f", (100. * class1Count / class1Total));
            String class2Percent = String.format("%.2f", (100. * class2Count / class2Total));
            String class3Percent = String.format("%.2f", (100. * class3Count / class3Total));
            bwr.write("Survival percentages by department:" + "\n" + "First: " + class1Percent +
                    "%" + "\n" + "Second: " + class2Percent + "%" + "\n" + "Third: " + class3Percent + "%" + "\n");
            String malePercent = String.format("%.2f", (100. * maleCount / maleTotal));
            String femalePercent = String.format("%.2f", (100. * femaleCount / femaleTotal));
            bwr.write("Survival percentages by Gender:" + "\n" + "Male: " + malePercent + "%" + "\n" + "Female: " + femalePercent + "%" + "\n");
            String zeroToTenPercent = String.format("%.2f", (100. * zeroToTenCount / zeroToTenTotal));
            String elevenToTwentyPercent = String.format("%.2f", (100. * elevenToTwentyCount / elevenToTwentyTotal));
            String twentyOneToThirtyPercent = String.format("%.2f", (100. * twentyOneToThirtyCount / twentyOneToThirtyTotal));
            String thirtyOneToFortyPercent = String.format("%.2f", (100. * thirtyOneToFortyCount / thirtyOneToFortyTotal));
            String fortyOneToFiftyPercent = String.format("%.2f", (100. * fortyOneToFiftyCount / fortyOneToFiftyTotal));
            String fiftyOnePlusPercent = String.format("%.2f", (100. * fiftyOnePlusCount / fiftyOnePlusTotal));
            bwr.write("Survival percentages by age:" + "\n" + "0-10: " + zeroToTenPercent + "%" + "\n" +
                    "11-20: " + elevenToTwentyPercent + "%" + "\n" + "21-30: " + twentyOneToThirtyPercent + "%" + "\n" +
                    "31-40: " + thirtyOneToFortyPercent + "%" + "\n" +
                    "41-50: " + fortyOneToFiftyPercent + "%" + "\n" + "51+: " + fiftyOnePlusPercent + "%" + "\n");
            String lonelyPassengersPercent = String.format("%.2f", (100. * lonelyPassengerCount / lonelyPassengerTotal));
            String familyPassengersPercent = String.format("%.2f", (100. * familyPassengerCount / familyPassengerTotal));
            bwr.write("Survival percentage of lonely passengers: " + lonelyPassengersPercent + "%" + "\n");
            bwr.write("Survival percentage of  passengers with family: " + familyPassengersPercent + "%" + "\n");
            String cheapTicketPercent = String.format("%.2f", (100. * cheapFareCount / cheapFareTotal));
            String normalTicketPercent = String.format("%.2f", (100. * normalFareCount / normalFareTotal));
            String expTicketPercent = String.format("%.2f", (100. * expFareCount / expFareTotal));
            bwr.write("Survival percentages by ticket price:" + "\n" + "Cheap ticket: " + cheapTicketPercent + "%" + "\n" +
                    "Normal ticket: " + normalTicketPercent + "%" + "\n" + "Expensive ticket: " + expTicketPercent + "%" + "\n");
            String SPortPercent = String.format("%.2f", (100. * portSCount / portSTotal));
            String QPortPercent = String.format("%.2f", (100. * portQCount / portQTotal));
            String CPortPercent = String.format("%.2f", (100. * portCCount / portCTotal));
            bwr.write("Survival percentages by port name:" + "\n" + "Cherbourg: " + CPortPercent + "%" + "\n" +
                    "Queenstown: " + QPortPercent + "%" + "\n" + "Southampton: " + SPortPercent + "%" + "\n");
            bwr.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}



