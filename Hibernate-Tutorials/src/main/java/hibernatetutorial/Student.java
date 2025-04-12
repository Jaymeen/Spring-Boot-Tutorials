package hibernatetutorial;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Embedded;

@Entity
public class Student {

    @Id
    public int rollNo;

    public String name;
    public int age;

    @Embedded
    public Address address;

    @OneToOne
    @JoinColumn(referencedColumnName = "id")
    public Laptop laptop;

    public Student() {}

    public Student(int rollNo, String name, int age, Address address, Laptop laptop) {
        this.name = name;
        this.age = age;
        this.rollNo = rollNo;
        this.address = address;
        this.laptop = laptop;
    }

    @Override
    public String toString() {
        return "Student{" +
                "rollNo=" + rollNo +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", address=" + address +
                ", laptop=" + laptop +
                '}';
    }
}
