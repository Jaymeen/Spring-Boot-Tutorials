package hibernatetutorial;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Student {
    @Id
    public int rollNo;
    public String name;
    public int age;
    public Address address;

    public Student(String name, int age, int rollNo, Address address) {
        this.name = name;
        this.age = age;
        this.rollNo = rollNo;
        this.address = address;
    }

    public Student() {
    }

    @Override
    public String toString() {
        return "Student{" +
                "rollNo=" + rollNo +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", address=" + address + ' ' +
                '}';
    }
}
