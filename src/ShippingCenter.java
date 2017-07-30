import java.sql.*;
import java.util.LinkedList;
import java.util.List;

/**
 * Class Description
 *
 * @author mtajonera 16927678
 */
public class ShippingCenter {
    private List<Journey> journeyList;
    private static final String DB_URL = "jdbc:mysql://raptor2.aut.ac.nz:3306/testRestricted";
    private Connection connection;
    private Statement statement;
    // statement = connection.createStatement();


    public ShippingCenter(String dbUser, String dbPassword) {
        try {
            connection = DriverManager.getConnection(DB_URL, dbUser, dbPassword);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<String> readAllPorts() {
        List<String> ports = new LinkedList<>();
        String command = "SELECT portName from Ports;";
        try {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(command);
            while (resultSet.next()) {
                ports.add(resultSet.getString("portName"));
            }
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ports;
    }

    public List<Journey> getAllJourneys(String startPort, String startDate, String endPort) {
        journeyList = new LinkedList<>();
        Journey newJourney = new Journey();
        findPaths(newJourney, startDate, startPort, endPort);
        return journeyList;
    }

    public void findPaths(Journey currentJourney, String startDate, String startPort, String endPort) {
        BoatTrip boatTrip;
        String command = "SELECT * FROM Shipping WHERE departPort = '" + startPort + "' AND departDate >" + startDate;
        try {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(command);
            Journey journeyClone;
            while (resultSet.next()) {
                journeyClone = currentJourney.createClone();
                boatTrip = new BoatTrip(resultSet.getString("boatID"), resultSet.getString("departPort"),
                        resultSet.getString("departDate"), resultSet.getString("arrivalPort"),
                        resultSet.getString("arrivalDate"), resultSet.getInt("cost"));

                if (journeyClone.addTrip(boatTrip)) {
                    if (boatTrip.arrivalPort.equals(endPort)) {
                        journeyList.add(journeyClone);
                    } else {
                        findPaths(journeyClone, boatTrip.arrivalDate, boatTrip.arrivalPort, endPort);
                    }
                }
            }
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void close() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        if (!journeyList.isEmpty()) {
            for (Journey journey : journeyList) {
                sb.append(journey).append("\n\n");
            }
        } else {
            sb.append("No journeys found.\n");
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        ShippingCenter shippingCenter = new ShippingCenter("student", "fpn871");
        System.out.println("Listing of all ports: ");
        List<String> ports = shippingCenter.readAllPorts();

        ports.forEach(System.out::println);
        System.out.println("===================================================");

        System.out.println("Get all trips from Auckland to Samoa: ");
        shippingCenter.getAllJourneys("Auckland", "01/01/2017", "Samoa");
        System.out.println(shippingCenter);
        System.out.println("===================================================");

        System.out.println("Get all trips from Samoa to Fiji: ");
        shippingCenter.getAllJourneys("Samoa", "01/01/2017", "Fiji");
        System.out.println(shippingCenter);
        System.out.println("===================================================");

        System.out.println("Get all trips from Auckland to Singapore: ");
        shippingCenter.getAllJourneys("Auckland", "01/01/2017", "Singapore");
        System.out.println(shippingCenter);
        System.out.println("===================================================");

        shippingCenter.close();
    }
}

