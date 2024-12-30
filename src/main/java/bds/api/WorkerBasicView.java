package bds.api;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class WorkerBasicView {
    private LongProperty idWorker = new SimpleLongProperty();
    private StringProperty firstName = new SimpleStringProperty();
    private StringProperty middleName = new SimpleStringProperty();
    private StringProperty lastName = new SimpleStringProperty();
    private IntegerProperty age = new SimpleIntegerProperty();  // Changed from LongProperty to IntegerProperty
    private StringProperty gender = new SimpleStringProperty();
    private StringProperty position = new SimpleStringProperty();
    private IntegerProperty salary = new SimpleIntegerProperty();  // Changed from LongProperty to IntegerProperty

    // Getters and Setters
    public Long getIdWorker() {
        return idWorker.get();
    }

    public void setIdWorker(Long idWorker) {
        this.idWorker.set(idWorker);
    }

    public String getFirstName() {
        return firstName.get();
    }

    public void setFirstName(String firstName) {
        this.firstName.set(firstName);
    }

    public String getMiddleName() {
        return middleName.get();
    }

    public void setMiddleName(String middleName) {
        this.middleName.set(middleName);
    }

    public String getLastName() {
        return lastName.get();
    }

    public void setLastName(String lastName) {
        this.lastName.set(lastName);
    }

    public int getAge() {  // Changed return type to int
        return age.get();
    }

    public void setAge(int age) {  // Changed parameter type to int
        this.age.set(age);
    }

    public String getGender() {
        return gender.get();
    }

    public void setGender(String gender) {
        this.gender.set(gender);
    }

    public String getPosition() {
        return position.get();
    }

    public void setPosition(String position) {
        this.position.set(position);
    }

    public int getSalary() {  // Changed return type to int
        return salary.get();
    }

    public void setSalary(int salary) {  // Changed parameter type to int
        this.salary.set(salary);
    }

    // Property Getters
    public LongProperty idWorkerProperty() {
        return idWorker;
    }

    public StringProperty firstNameProperty() {
        return firstName;
    }

    public StringProperty middleNameProperty() {
        return middleName;
    }

    public StringProperty lastNameProperty() {
        return lastName;
    }

    public IntegerProperty ageProperty() {  // Changed to IntegerProperty
        return age;
    }

    public StringProperty genderProperty() {
        return gender;
    }

    public StringProperty positionProperty() {
        return position;
    }

    public IntegerProperty salaryProperty() {  // Changed to IntegerProperty
        return salary;
    }

    @Override
    public String toString() {
        return "WorkerBasicView{" +
                "idWorker=" + idWorker.get() +
                ", firstName='" + firstName.get() + '\'' +
                ", middleName='" + middleName.get() + '\'' +
                ", lastName='" + lastName.get() + '\'' +
                ", age=" + age.get() +
                ", gender='" + gender.get() + '\'' +
                ", position='" + position.get() + '\'' +
                ", salary=" + salary.get() +
                '}';
    }
}
