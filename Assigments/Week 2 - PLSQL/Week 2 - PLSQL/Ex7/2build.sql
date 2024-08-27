CREATE OR REPLACE PACKAGE EmployeeManagement AS
    PROCEDURE HireNewEmployee(p_EmployeeID IN NUMBER, p_Name IN VARCHAR2, p_Position IN VARCHAR2, p_Salary IN NUMBER, p_Department IN VARCHAR2);
    PROCEDURE UpdateEmployeeDetails(p_EmployeeID IN NUMBER, p_Name IN VARCHAR2, p_Position IN VARCHAR2);
    FUNCTION CalculateAnnualSalary(p_EmployeeID IN NUMBER) RETURN NUMBER;
END EmployeeManagement;
/
