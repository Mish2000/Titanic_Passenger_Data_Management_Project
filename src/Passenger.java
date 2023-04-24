public class Passenger {
    private final String passengerId;
    private final String survived;
    private final String pClass;
    private final String name;
    private final String sex;
    private final String age;
    private final String sibSp;
    private final String parch;
    private final String ticket;
    private final String fare;
    private final String cabin;
    private final String embarked;

    //The time complexity of the constructor is O(1).
    public Passenger(String passengerId, String survived, String pClass, String name, String sex, String age, String sibSp, String parch, String ticket, String fare, String cabin, String embarked) {
        this.passengerId = passengerId;
        this.survived = survived;
        this.pClass = pClass;
        this.name = name;
        this.sex = sex;
        this.age = age;
        this.sibSp = sibSp;
        this.parch = parch;
        this.ticket = ticket;
        this.fare = fare;
        this.cabin = cabin;
        this.embarked = embarked;
    }

    //The time complexity of the current method is O(1).
    public String getPassengerString() {
        return this.passengerId + "," + this.survived + "," + this.pClass + "," +
                this.name + "," + this.sex + "," + this.age + "," + this.sibSp + "," +
                this.parch + "," + this.ticket + "," + this.fare + "," +
                this.cabin + "," + this.embarked + "\n";
    }

    //The time complexity of the current method is O(1).
    public String getPassengerId() {
        return passengerId;
    }

    //The time complexity of the current method is O(1).
    public boolean isSurvivor() {
        if (this.survived.equals("1")) {
            return true;
        }
        return false;
    }

    //The time complexity of the current method is O(1).
    public String getPClass() {
        return pClass;
    }

    //The time complexity of the current method is O(1).
    public String getName() {
        return name;
    }

    //The time complexity of the current method is O(1).
    public String getSex() {
        return sex;
    }

    //The time complexity of the current method is O(1).
    public String getAge() {
        return age;
    }

    //The time complexity of the current method is O(1).
    public String getSibSp() {
        return sibSp;
    }

    //The time complexity of the current method is O(1).
    public String getParch() {
        return parch;
    }

    //The time complexity of the current method is O(1).
    public String getTicket() {
        return ticket;
    }

    //The time complexity of the current method is O(1).
    public String getFare() {
        return fare;
    }

    //The time complexity of the current method is O(1).
    public String getCabin() {
        return cabin;
    }

    //The time complexity of the current method is O(1).
    public String getEmbarked() {
        return embarked;
    }

    //The time complexity of the current method is O(n), because it mostly depends on the length of the string.
    public String getFormattedName() {
        String[] fullNameParts = this.name.split(",");
        String lastName = fullNameParts[0];
        String finalLastName = lastName.substring(1);
        String firstName = fullNameParts[1];
        String[] firstNameParts = firstName.split(" ");
        String finalFirstName = "";
        for (int i = 0; i < firstNameParts[2].length(); i++) {
            if (firstNameParts[2].charAt(i) == '"' || firstNameParts[2].charAt(i) == ')' || firstNameParts[2].charAt(i) == '(') {
                continue;
            }
            finalFirstName += firstNameParts[2].charAt(i);
        }
        return finalFirstName + " " + finalLastName;
    }


}