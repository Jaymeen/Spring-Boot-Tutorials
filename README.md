# Many-to-Many Relationship

In a many-to-many relationship, one student can have multiple laptops, and one laptop can be associated with multiple students. This requires an intermediary table (often called a join table or link table) in the database to manage the associations. This join table will typically contain foreign keys referencing the primary keys of both the Student and Laptop tables.

Steps to Implement Many-to-Many in Hibernate

Here's how you would modify your entities to establish a many-to-many relationship:

1. Modify the Student Entity:

Remove the @OneToMany annotation and the laptop list.
Add a @ManyToMany annotation and a List of Laptop objects.
Use the @JoinTable annotation to specify the details of the join table.
Java

package hibernatetutorial;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.JoinTable;
import jakarta.persistence.JoinColumn;
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

    @ManyToMany
    @JoinTable(
        name = "student_laptop", // Name of the join table
        joinColumns = @JoinColumn(name = "student_rollno"), // Foreign key referencing Student
        inverseJoinColumns = @JoinColumn(name = "laptop_id") // Foreign key referencing Laptop
    )
    public List<Laptop> laptops; // Renamed to be plural

    public Student() {}

    public Student(int rollNo, String name, int age, Address address) {
        this.name = name;
        this.age = age;
        this.rollNo = rollNo;
        this.address = address;
        this.laptops = new java.util.ArrayList<>(); // Initialize the list
    }

    // ... (toString() method remains the same)
}
Explanation of @ManyToMany and @JoinTable in Student:

@ManyToMany: This annotation signifies a many-to-many relationship with the Laptop entity.
@JoinTable: This annotation specifies the details of the join table in the database:
name = "student_laptop": Sets the name of the join table to student_laptop.
joinColumns = @JoinColumn(name = "student_rollno"): Defines the foreign key column in the student_laptop table that references the Student entity's primary key (rollNo).
inverseJoinColumns = @JoinColumn(name = "laptop_id"): Defines the foreign key column in the student_laptop table that references the Laptop entity's primary key (id).
2. Modify the Laptop Entity:

Remove the @ManyToOne annotation and the student field.
Add a @ManyToMany annotation and a List of Student objects.
Use the mappedBy attribute to indicate that the join table is managed by the Student entity.
Java

package hibernatetutorial;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;

import java.util.List;

@Entity
public class Laptop {
    @Id
    public int id;

    public String company;
    public String model;
    public double price;

    @ManyToMany(mappedBy = "laptops")
    public List<Student> students; // Renamed to be plural

    public Laptop() {}

    public Laptop(int id, String company, String model, double price) {
        this.id = id;
        this.company = company;
        this.model = model;
        this.price = price;
        this.students = new java.util.ArrayList<>(); // Initialize the list
    }

    @Override
    public String toString() {
        return "Laptop{" +
                "id=" + id +
                ", company='" + company + '\'' +
                ", model='" + model + '\'' +
                ", price=" + price +
                '}';
    }
}
Explanation of @ManyToMany in Laptop:

@ManyToMany(mappedBy = "laptops"): This annotation also signifies a many-to-many relationship with the Student entity. The mappedBy = "laptops" attribute is crucial here. It tells Hibernate that the relationship is managed by the laptops collection in the Student entity. Hibernate will not create a separate join table definition in the Laptop entity; it will use the one defined in the Student entity. This avoids having two join tables for the same relationship.
3. The Address Entity Remains Unchanged:

The @Embeddable Address entity is not directly involved in the many-to-many relationship and doesn't need any modifications.

How it Works in the Database:

When Hibernate persists these entities, it will create three tables:

student: With columns like rollNo (primary key), name, age, and the embedded address fields (city, state, pincode).
laptop: With columns like id (primary key), company, model, and price.
student_laptop (the join table): With two foreign key columns:
student_rollno: Referencing the rollNo column in the student table.
laptop_id: Referencing the id column in the laptop table.
Each row in the student_laptop table represents an association between a specific student and a specific laptop. For example, if a student with rollNo = 101 has two laptops with id = 1 and id = 2, the student_laptop table would have two rows:

student_rollno | laptop_id
----------------|----------
101             | 1
101             | 2
Managing the Relationship in Code:

To associate students and laptops in your Java code, you would add and remove Laptop objects from the laptops collection of a Student object, and vice versa (though typically, you'd manage it from the owning side, which is Student in this case since it defines the @JoinTable). Hibernate will then handle the necessary insertions and deletions in the student_laptop join table.

Example of Adding a Laptop to a Student:

Java

Student student = entityManager.find(Student.class, 101);
Laptop laptop1 = entityManager.find(Laptop.class, 1);

student.laptops.add(laptop1);
entityManager.persist(student); // Or merge if the student is detached
By making these changes, you've successfully converted the one-to-many/many-to-one relationship into a many-to-many relationship between Student and Laptop using Hibernate. Remember to update your database schema accordingly if you are not using Hibernate's auto-generation capabilities.
