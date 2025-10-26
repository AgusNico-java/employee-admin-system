package core;

import java.util.Date;

public class Employee {
    public String name;
    public String id;
    public String role;

    public Date employmentDate;
    public Date birthdayDate;

    public boolean isAdmin;
    public boolean canManageFood;

    public Employee(String name, String id, String role, Date employmentDate, Date birthdayDate, boolean isAdmin, boolean canManageFood) {
        this.name = name;
        this.id = id;
        this.role = role;
        this.employmentDate = employmentDate;
        this.birthdayDate = birthdayDate;
        this.isAdmin = isAdmin;
        this.canManageFood = canManageFood;
    }

}
