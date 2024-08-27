CREATE OR REPLACE PROCEDURE UpdateSalary (
    p_employee_id IN NUMBER,
    p_percentage IN NUMBER
) AS
    v_current_salary NUMBER;
BEGIN
    SELECT Salary INTO v_current_salary
    FROM Employees
    WHERE EmployeeID = p_employee_id;

    UPDATE Employees
    SET Salary = Salary * (1 + p_percentage / 100)
    WHERE EmployeeID = p_employee_id;

    COMMIT;
EXCEPTION
    WHEN NO_DATA_FOUND THEN
        RAISE_APPLICATION_ERROR(-20004, 'Employee ID does not exist.');
    WHEN OTHERS THEN
        RAISE_APPLICATION_ERROR(-20005, 'An unexpected error occurred: ' || SQLERRM);
END;
/