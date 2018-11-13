package cn.qijianke.kkt.model.event;

public class CalculatePosTotalEvent {
    private double total;

    public CalculatePosTotalEvent() {
    }

    public CalculatePosTotalEvent(double total) {
        this.total = total;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
}
