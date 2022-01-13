package cinema.Controllers;

import cinema.Models.*;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

@RestController
public class SeatsController {

    //initialize a 9x9 array of seats called available seats
    int[][] seats = initializer();

    public int[][] initializer(){
        int[][] s = new int[9][9];
        for(int i = 0; i < 9; i++){
            for(int j = 0; j<9;j++){
                s[i][j] = 1;
            }
        }
        return s;
    }

    @GetMapping("/seats")
    public SeatResponseModel availableSeats(){
        return new SeatResponseModel(seats);
    }

    HashMap<String, Ticket> tickets = new HashMap<>();

    @PostMapping("/purchase")
    public ResponseEntity<PurchaseResponse> purchaseTix(@RequestBody PurchaseRequest req) {

        int row = req.getRow();
        int column = req.getColumn();
        if(row <=0 || row > 9 || column > 9 || column <= 0){
            return new ResponseEntity<>(new PurchaseResponse("The number of a row or a column is out of bounds!"), HttpStatus.BAD_REQUEST);
        }
        else if(isAvailable(row, column)){
            seats[row-1][column-1] = 0;
            PurchaseResponse response = new PurchaseResponse(new Ticket(row, column));
            tickets.put(response.getToken(), response.getTicket());
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        else
            return new ResponseEntity<>(new PurchaseResponse("The ticket has been already purchased!"), HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/return")
    public ResponseEntity<ReturnResponse> returnTix(@RequestBody ReturnRequest req) {

        String token = req.getToken();
        Ticket returnTicket = tickets.get(token);

        if(returnTicket!=null){
            tickets.remove(token);
            seats[returnTicket.getRow()-1][returnTicket.getColumn()-1] = 1;
            return new ResponseEntity<>(new ReturnResponse(returnTicket), HttpStatus.OK);
        }
        else
            return new ResponseEntity<>(new ReturnResponse("Wrong token!"), HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/stats")
    public ResponseEntity<StatsResponse> getStats(@RequestParam(required = false) String password) {

        if(("super_secret").equals(password)){
            int income = 0;
            int tix = 0;
            for(Ticket t : tickets.values()) {
                income += t.getPrice();
                tix++;
            }
            return new ResponseEntity<>(new StatsResponse(income, 81-tix, tix), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new StatsResponse("The password is wrong!"), HttpStatus.UNAUTHORIZED);
        }
    }

    public boolean isAvailable(int row, int column){
        return seats[row-1][column-1] == 1;
    }
    class SeatResponseModel {

        private int total_rows;
        private int total_columns;
        private List<Seat> available_seats;

        SeatResponseModel(int[][] seatList) {
            total_columns = 9;
            total_rows = 9;
            List<Seat> available = new ArrayList();
            for(int i = 0; i < 9; i++){
                for(int j = 0; j<9;j++){
                    if(seatList[i][j] == 1)
                        available.add(new Seat(i+1, j+1));
                }
            }
            available_seats=available;
        }
        public int getTotal_rows() {
            return total_rows;
        }

        public int getTotal_columns() {
            return total_columns;
        }

        public List<Seat> getAvailable_seats() {
            return available_seats;
        }

    }

    class Seat {
        private int row;
        private int column;
        private int price;

        public Seat(int row, int column){
            this.row = row;
            this.column = column;
            if(row <=4) {
                price = 10;
            } else{
                price = 8;
            }
        }

        //getters
        public int getRow(){
            return row;
        }
        public int getColumn(){
            return column;
        }
        public int getPrice() { return price; }

    }
}