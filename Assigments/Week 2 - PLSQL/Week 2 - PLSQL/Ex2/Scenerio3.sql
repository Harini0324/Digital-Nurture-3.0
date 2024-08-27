CREATE OR REPLACE PROCEDURE AddNewCustomer (
    p_customer_id IN NUMBER,
    p_name IN VARCHAR2,
    p_dob IN DATE,
    p_balance IN NUMBER,
    p_last_modified IN DATE
) AS
BEGIN
    BEGIN
        INSERT INTO Customers (CustomerID, Name, DOB, Balance, LastModified)
        VALUES (p_customer_id, p_name, p_dob, p_balance, p_last_modified);

        COMMIT;
    EXCEPTION
        WHEN DUP_VAL_ON_INDEX THEN
            RAISE_APPLICATION_ERROR(-20006, 'A customer with this ID already exists.');
        WHEN OTHERS THEN
            RAISE_APPLICATION_ERROR(-20007, 'An unexpected error occurred: ' || SQLERRM);
    END;
END;
/
