package src;

import java.util.ArrayList;

/**
 * Created by Oly on 02.04.2017.
 */
public class DataSheet {
    private double k;
    private double b;
    private double xAverage;
    private double yAverage;
    private double sumX;
    private double sumY;
    private double sumXY;
    private double sumXSquared;

    private String name;
    private ArrayList<Data> dataArrayList;

    public DataSheet(String name) {
        this.name = name;
        dataArrayList = new ArrayList<>();
    }

    public double getK() {
        return k;
    }

    public double getB() {
        return b;
    }

    private void resetFields() {k = b = xAverage = yAverage = sumX = sumY = sumXY = sumXSquared = 0d;}

    private void updateFields(Data data) {
        double x = data.getX();
        double y = data.getY();
        sumX += x;
        sumY += y;
        sumXY += x * y;
        sumXSquared += Math.pow(x,2);
        xAverage = sumX/dataArrayList.size();
        yAverage = sumY/dataArrayList.size();
        k = (sumXY - (sumX * yAverage))/(sumXSquared - (sumX * xAverage));
        b = (yAverage - (k * xAverage));
    }

    public void addDataItem(Data data) {
        dataArrayList.add(data);
        updateFields(data);
    }

    public void removeDataItem(int index) {
        dataArrayList.remove(index);
    }

    public String getEquationString() {return "y = " + k + " * x + " + b;}

    public void setDataArrayList(ArrayList<Data> dataArrayList) {
        this.dataArrayList = dataArrayList;
        resetFields();
        this.dataArrayList.forEach(this::updateFields); //TODO find out what's going on
    }

    public String getName() {return name;}
    public void setName(String name) {this.name = name;}

    public ArrayList<Data> getDataArrayList() {
        return dataArrayList;
    }

    public Data getDataItem(int index) {
        return dataArrayList.get(index);
    }

    public int size() {
        return dataArrayList.size();
    }
}
