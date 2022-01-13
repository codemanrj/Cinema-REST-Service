package cinema.Models;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ReturnResponse {

    private Ticket returned_ticket;

    private String error;

    public Ticket getReturned_ticket() {
        return returned_ticket;
    }

    public void setReturned_ticket(Ticket returned_ticket) {
        this.returned_ticket = returned_ticket;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
    public ReturnResponse(Ticket t) {
        returned_ticket = t;
    }
    public ReturnResponse(String error) {
        this.error = error;
    }


}
