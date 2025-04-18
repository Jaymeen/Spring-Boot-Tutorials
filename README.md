# One-to-Many/Many-to-One Relationship

**Understanding the Relationships**

-   **One-to-Many (Student to Laptop):** This relationship implies that one student can own or be associated with multiple laptops. From the `Student` entity's perspective, it has a collection of `Laptop` objects.
    
-   **Many-to-One (Laptop to Student):** This is the inverse of the One-to-Many relationship. It means that multiple laptops can be owned by or associated with a single student. From the `Laptop` entity's perspective, it refers to one `Student` object.
    

**Implementing the Relationships in Hibernate**

To establish these relationships in Hibernate, you'll need to add annotations to your `Student` and `Laptop` classes.

**1. One-to-Many (Student to Laptop)**

To implement a One-to-Many relationship from `Student` to `Laptop`, you'll typically:

-   Add a collection (e.g., `List`, `Set`) of `Laptop` objects to the `Student` entity.
-   Use the `@OneToMany` annotation on this collection field in the `Student` class.
-   Use the `@JoinColumn` annotation on the _owning_ side (usually the `Laptop` entity in a unidirectional One-to-Many or the "many" side in a bidirectional relationship) to specify the foreign key column in the `Laptop` table that references the `Student` table.

Here's how you would modify the `Student` class:

Java

```
package hibernatetutorial;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Embedded;
import java.util.List; // Or Set

@Entity
public class Student {

    @Id
    public int rollNo;

    public String name;
    public int age;

    @Embedded
    public Address address;

    @OneToMany
    @JoinColumn(name = "student_roll_no") // Foreign key column in Laptop table
    public List<Laptop> laptops; // Collection to hold multiple laptops

    public Student() {}

    public Student(int rollNo, String name, int age, Address address) {
        this.name = name;
        this.age = age;
        this.rollNo = rollNo;
        this.address = address;
        this.laptops = new java.util.ArrayList<>(); // Initialize the list
    }

    // Getter and Setter for 'laptops'
    public List<Laptop> getLaptops() {
        return laptops;
    }

    public void setLaptops(List<Laptop> laptops) {
        this.laptops = laptops;
    }

    @Override
    public String toString() {
        return "Student{" +
                "rollNo=" + rollNo +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", address=" + address +
                ", laptops=" + (laptops != null ? laptops.toString() : "null") +
                '}';
    }
}

```

**Explanation:**

-   `@OneToMany`: This annotation indicates a One-to-Many relationship with the `Laptop` entity.
-   `@JoinColumn(name = "student_roll_no")`: This annotation specifies the foreign key column in the `Laptop` table. The `student_roll_no` column in the `Laptop` table will hold the `rollNo` of the `Student` who owns the laptop. Hibernate will use this column to manage the association.
-   `public List<Laptop> laptops;`: This declares a `List` (you could also use a `Set`) to hold the multiple `Laptop` objects associated with this `Student`.

**2. Many-to-One (Laptop to Student)**

To implement the Many-to-One relationship from `Laptop` to `Student`, you'll:

-   Add a field of type `Student` to the `Laptop` entity.
-   Use the `@ManyToOne` annotation on this field in the `Laptop` class.
-   Use the `@JoinColumn` annotation to specify the foreign key column in the `Laptop` table that links back to the `Student` table's primary key.

Here's how you would modify the `Laptop` class:

Java

```
package hibernatetutorial;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;

@Entity
public class Laptop {
    @Id
    public int id;

    public String company;
    public String model;
    public double price;

    @ManyToOne
    @JoinColumn(name = "student_roll_no") // Foreign key column referencing Student
    public Student student;

    public Laptop() {}

    public Laptop(int id, String company, String model, double price) {
        this.id = id;
        this.company = company;
        this.model = model;
        this.price = price;
    }

    // Constructor to associate Laptop with a Student
    public Laptop(int id, String company, String model, double price, Student student) {
        this.id = id;
        this.company = company;
        this.model = model;
        this.price = price;
        this.student = student;
    }

    // Getter and Setter for 'student'
    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    @Override
    public String toString() {
        return "Laptop{" +
                "id=" + id +
                ", company='" + company + '\'' +
                ", model='" + model + '\'' +
                ", price=" + price +
                ", student=" + (student != null ? "Student(rollNo=" + student.rollNo + ")" : "null") +
                '}';
    }
}

```

**Explanation:**

-   `@ManyToOne`: This annotation indicates a Many-to-One relationship with the `Student` entity.
-   `@JoinColumn(name = "student_roll_no")`: This annotation specifies the foreign key column in the `Laptop` table (`student_roll_no`) that references the primary key column (`rollNo`) of the `Student` table. Hibernate uses this to know which student owns this laptop.
-   `public Student student;`: This declares a field of type `Student` to hold the reference to the owning student.

**Database Schema Implications**

When Hibernate maps these entities to database tables, you'll typically have:

-   **`Student` Table:** With columns like `rollNo` (primary key), `name`, `age`, `city`, `state`, `pincode`.
-   **`Laptop` Table:** With columns like `id` (primary key), `company`, `model`, `price`, and a foreign key column `student_roll_no` that references the `rollNo` column of the `Student` table.

**Bidirectional vs. Unidirectional**

The implementation above creates a **bidirectional** relationship. This means you can navigate the relationship from both sides:

-   From a `Student` object, you can access its list of `Laptop` objects using the `laptops` collection.
-   From a `Laptop` object, you can access the associated `Student` object using the `student` field.

If you only need to navigate the relationship in one direction (e.g., only from `Laptop` to `Student` to find the owner), you could implement a **unidirectional** Many-to-One relationship by only adding the `@ManyToOne` and `@JoinColumn` annotations to the `Laptop` entity and omitting the `@OneToMany` and `laptops` collection in the `Student` entity.

**In Summary**

By using the `@OneToMany` and `@ManyToOne` annotations along with `@JoinColumn`, you can effectively define and manage One-to-Many and Many-to-One relationships between your `Student` and `Laptop` entities in Hibernate, allowing you to persist and retrieve related data from your database. Remember to adjust your constructors and potentially add helper methods to manage the associations between the entities.
