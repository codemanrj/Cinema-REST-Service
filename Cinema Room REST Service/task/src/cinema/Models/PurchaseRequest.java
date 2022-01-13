package cinema.Models;

public class PurchaseRequest {
    private Integer row;
    private Integer column;

    PurchaseRequest(){}
    PurchaseRequest (int row, int column) {
        this.row = row;
        this.column = column;
    }

    public Integer getRow() {
        return row;
    }
    public Integer getColumn() {
        return column;
    }
    public void setRow(Integer row) {
        this.row = row;
    }
    public void setColumn(Integer column) {
        this.column = column;
    }
}