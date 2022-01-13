package cinema.Models;

public class Ticket {
    private Integer row;
    private Integer column;
    private Integer price;

    public Ticket(int row, int column) {
        this.row = row;
        this.column = column;
        if(row <=4) {
            price = 10;
        } else{
            price = 8;
        }
    }

    public Integer getRow() {
        return row;
    }
    public Integer getColumn() {
        return column;
    }
    public Integer getPrice() {
        return price;
    }
}
