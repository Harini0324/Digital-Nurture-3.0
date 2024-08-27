CREATE OR REPLACE PACKAGE BODY EmployeeManagement AS
    PROCEDURE HireNewEmployee(p_EmployeeID IN NUMBER, p_Name IN VARCHAR2, p_Position IN VARCHAR2, p_Salary IN NUMBER, p_Department IN VARCHAR2) IS
    BEGIN
        INSERT INTO Employees (EmployeeID, Name, Position, Salary, Department, HireDate)
        VALUES (p_EmployeeID, p_Name, p_Position, p_Salary, p_Department, SYSDATE);
        COMMIT;
    END HireNewEmployee;

    PROCEDURE UpdateEmployeeDetails(p_EmployeeID IN NUMBER, p_Name IN VARCHAR2, p_Position IN VARCHAR2) IS
    BEGIN
        UPDATE Employees
        SET Name = p_Name, Position = p_Position
        WHERE EmployeeID = p_EmployeeID;
        COMMIT;
    END UpdateEmployeeDetails;

    FUNCTION CalculateAnnualSalary(p_EmployeeID IN NUMBER) RETURN NUMBER IS
        v_Salary NUMBER;
    BEGIN
        SELECT Salary INTO v_Salary
        FROM Employees
        WHERE EmployeeID = p_EmployeeID;
        RETURN v_Salary * 12;
    END CalculateAnnualSalary;
END EmployeeManagement;
/
