import java.util.LinkedList;
import java.util.List;

/**
 * Class Description
 *
 * @author mtajonera 16927678
 */
public class Journey {
    private LinkedList<BoatTrip> boatTripList;

    public Journey() {
        boatTripList = new LinkedList<>();
    }

    public Journey(List<BoatTrip> trips) {
        this();
        boatTripList.addAll(trips);

    }

    public boolean addTrip(BoatTrip trip) {
        if (containsPort(trip.arrivalPort)) {
            return false;
        } else {
            boatTripList.add(trip);
            return true;
        }
    }

    public boolean containsPort(String port) {
        for (BoatTrip boatTrips : boatTripList) {
            if (boatTrips.arrivalPort.equals(port)
                    || boatTrips.departPort.equals(port)) {
                return true;
            }
        }
        return false;
    }

    public Journey createClone() {
        return new Journey(boatTripList);
    }

    public String getStartPort() {
        if (boatTripList.size() != 0) {
            return boatTripList.get(0).departPort;
        } else {
            return null;
        }
    }

    public String getEndPort() {
        if (boatTripList.size() != 0) {
            return boatTripList.get(boatTripList.size() - 1).arrivalPort;
        } else {
            return null;
        }
    }

    public String getEndDate() {
        if (boatTripList.size() != 0) {
            return boatTripList.get(boatTripList.size() - 1).arrivalDate;
        } else {
            return null;
        }
    }

    public int getTotalJourneyCost() {
        int cost = 0;

        if (boatTripList.size() != 0) {
            for (BoatTrip boatTrips : boatTripList) {
                cost += boatTrips.cost;
            }
            return cost;
        } else {
            return cost;
        }
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("Trips: \n");
        int tripCount = 1;

        for (BoatTrip boatTrips : boatTripList) {
            sb.append("Trip ").append(tripCount).append(": ").append(boatTrips.departPort)
                    .append(" to ").append(boatTrips.arrivalPort).append("\n");
            tripCount++;
        }
        sb.append("Cost: $").append(getTotalJourneyCost());

        return sb.toString();
    }
}
