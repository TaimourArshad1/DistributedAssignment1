import java.util.List;

public class Receipt {
    private List<String> items;
    private double totalCost;

    public Receipt(List<String> items, double totalCost) {
        this.items = items;
        this.totalCost = totalCost;
    }

    public List<String> getItems() {
        return items;
    }

    public double getTotalCost() {
        return totalCost;
    }
}
