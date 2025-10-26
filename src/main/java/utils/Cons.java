package utils;

public class Cons {
    //Prefixes
    public static final String GET_REQUEST_PREFIX = "get-";
    public static final String POST_REQUEST_PREFIX = "post-";

    //Event Bus Directions
    public static final String EV_LIST_EMPLOYEES_HANDLER = "get-list_employees";
    public static final String EV_SPECIFIC_EMPLOYEE_HANDLER = "get-specific_employee";
    public static final String EV_GET_EMPLOYEES_INFO_HANDLER = "get-all_employees_info";
    public static final String EV_CREATE_EMPLOYEE_HANDLER = "post-create_employee";

    public static final String DBV_INSERT_EMPLOYEE__HANDLER = "insert_employee";
    public static final String DBV_GET_EMPLOYEE_HANDLER = "get_employeee";
    public static final String DBV_GET_ALL_EMPLOYEES_HANDLER = "get_employees";

    //Useful literals
    public static final String APPLICATION_JSON = "application/json";

    //DB Queries
    public static final String INSERT_EMPLOYEE_QUERY = "INSERT INTO Employees (id, name, role, employmentDate, birthdayDate, isAdmin, canManageFood) " +
            "VALUES (?, ?, ?, ?, ?, ?, ?)";
    public static final String GET_EMPLOYEE_QUERY = "SELECT * FROM Employees WHERE id = ?";
    public static final String GET_EMPLOYEES_QUERY  = "SELECT * FROM Employees";

}
