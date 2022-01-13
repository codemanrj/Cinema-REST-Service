package cinema.Models;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.UUID;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class PurchaseResponse {

    private Ticket ticket;
    private String token;
    private String error;


    public PurchaseResponse(String error) {
        this.error = error;
    }
    public PurchaseResponse(Ticket ticket){
        this.token = UUID.randomUUID().toString();
        this.ticket = ticket;
    }

    public String getError() {
        return error;
    }

    public Ticket getTicket() {
        return ticket;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}