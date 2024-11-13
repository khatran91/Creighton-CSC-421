import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

// Author: Kha Tran
// Created on: 10/29/24
// Purpose: This class is used to provide different schedule based on their reference while still include the required events during the day
class Event {
    private Date startTime;
    private Date endTime;
    private String description;
    private boolean isRequired;

    public Event(String start, String end, String description, boolean isRequired) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        this.startTime = sdf.parse(start);
        this.endTime = sdf.parse(end);
        this.description = description;
        this.isRequired = isRequired;
    }

    public long getDuration() {
        return (endTime.getTime() - startTime.getTime()) / (1000 * 60);
    }

    public Date getStartTime() {
        return startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public boolean isRequired() {
        return isRequired;
    }

    @Override
    public String toString() {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        return sdf.format(startTime) + " " + sdf.format(endTime) + " " + description;
    }
}

class Schedule {
    private List<Event> events;

    public Schedule() {
        this.events = new ArrayList<>();
    }

    // Keep events in chronological order
    public boolean addEvent(Event event) {
        for (Event e : events) {
            if (!(event.getEndTime().before(e.getStartTime()) || event.getStartTime().equals(e.getEndTime()) || event.getStartTime().after(e.getEndTime()))) {
                return false;  // Conflict detected
            }
        }
        events.add(event);
        events.sort(Comparator.comparing(Event::getStartTime));  // Keep events in chronological order
        return true;
    }

    // Automatically add required events
    public void scheduleEvents(List<Event> allEvents, Comparator<Event> strategy) {
        events.clear();
        for (Event event : allEvents) {
            if (event.isRequired()) {
                addEvent(event);  
            }
        }
        // Add non-required events according to strategy
        allEvents.sort(strategy);
        for (Event event : allEvents) {
            if (!event.isRequired()) {
                addEvent(event);  
            }
        }
    }

    // Displaying the schedule as the format given
    public void displaySchedule(String strategyName) {
        System.out.println("\n" + strategyName + ":");
        System.out.println("---------------------");
        for (Event event : events) {
            System.out.println(event);
        }
    }
}

public class Scheduler {
    public static void main(String[] args) throws ParseException {
        List<Event> events = readEventsFromFile("events.txt");

        Schedule shortestFirst = new Schedule();
        shortestFirst.scheduleEvents(events, Comparator.comparingLong(Event::getDuration));
        shortestFirst.displaySchedule("Shortest Event First");

        Schedule longestFirst = new Schedule();
        longestFirst.scheduleEvents(events, Comparator.comparingLong(Event::getDuration).reversed());
        longestFirst.displaySchedule("Longest Event First");

        Schedule earliestStartFirst = new Schedule();
        earliestStartFirst.scheduleEvents(events, Comparator.comparing(Event::getStartTime));
        earliestStartFirst.displaySchedule("Earliest Start Time First");

        Schedule earliestEndFirst = new Schedule();
        earliestEndFirst.scheduleEvents(events, Comparator.comparing(Event::getEndTime));
        earliestEndFirst.displaySchedule("Earliest End Time First");
    }

    public static List<Event> readEventsFromFile(String filename) throws ParseException {
        List<Event> events = new ArrayList<>();
        Scanner sc = new Scanner(Scheduler.class.getResourceAsStream(filename));  
        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            String[] parts = line.split("\\s+");
            String startTime = parts[0];
            String endTime = parts[1];
            String description = String.join(" ", Arrays.copyOfRange(parts, 2, parts.length));
            boolean isRequired = description.startsWith("REQ");
            if (isRequired) {
                description = description.replace("REQ ", "");
            }
            events.add(new Event(startTime, endTime, description, isRequired));
        }
        sc.close();
        return events;
    }
}
