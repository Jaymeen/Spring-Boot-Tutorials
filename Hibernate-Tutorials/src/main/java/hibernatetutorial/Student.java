package hibernatetutorial;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Embedded;

import java.util.List;

@Entity
public class Student {

    @Id
    public int rollNo;

    public String name;
    public int age;

    @Embedded
    public Address address;

    @OneToMany(mappedBy = "student")
    public List<Laptop> laptop;

    public Student() {}

    public Student(int rollNo, String name, int age, Address address) {
        this.name = name;
        this.age = age;
        this.rollNo = rollNo;
        this.address = address;
    }

    @Override
    public String toString() {
        return "Student{" +
                "rollNo=" + rollNo +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", address=" + address +
                '}';
    }
}
