package producer;

public class EventLine {
  
    private Events[] events;

    public Events[] getEvents ()
    {
        return events;
    }

    public void setEvents (Events[] events)
    {
        this.events = events;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [events = "+events+"]";
    }
}