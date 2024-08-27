CREATE OR REPLACE PACKAGE CustomerManagement AS
    PROCEDURE AddNewCustomer(p_CustomerID IN NUMBER, p_Name IN VARCHAR2, p_DOB IN DATE, p_Balance IN NUMBER);
    PROCEDURE UpdateCustomerDetails(p_CustomerID IN NUMBER, p_Name IN VARCHAR2, p_DOB IN DATE);
    FUNCTION GetCustomerBalance(p_CustomerID IN NUMBER) RETURN NUMBER;
END CustomerManagement;
/