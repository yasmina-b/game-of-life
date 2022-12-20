package events;

public class CellEvents {
    private String cellID;
    private EventType eventType;

    public CellEvents(String cellID, EventType eventType) {
        this.cellID = cellID;
        this.eventType = eventType;
    }

    public String getCellID() {
        return cellID;
    }

    public EventType getEventType() {
        return eventType;
    }

    public void setCellID(String cellID) {
        this.cellID = cellID;
    }

    public void setEventType(EventType eventType) {
        this.eventType = eventType;
    }
}
