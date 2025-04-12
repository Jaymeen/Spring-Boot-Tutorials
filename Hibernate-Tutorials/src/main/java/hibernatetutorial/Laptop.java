package hibernatetutorial;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Laptop {
    @Id
    public int id;

    public String company;
    public String model;
    public double price;

    public Laptop() {}

    public Laptop(int id, String company, String model, double price) {
        this.id = id;
        this.company = company;
        this.model = model;
        this.price = price;
    }

    @Override
    public String toString() {
        return "Laptop{" +
                "id=" + id +
                ", company='" + company + '\'' +
                ", model='" + model + '\'' +
                ", price=" + price + ' ' +
                '}';
    }
}