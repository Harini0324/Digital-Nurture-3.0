CREATE OR REPLACE PACKAGE BODY AccountOperations AS
    PROCEDURE OpenNewAccount(p_AccountID IN NUMBER, p_CustomerID IN NUMBER, p_AccountType IN VARCHAR2, p_Balance IN NUMBER) IS
    BEGIN
        INSERT INTO Accounts (AccountID, CustomerID, AccountType, Balance, LastModified)
        VALUES (p_AccountID, p_CustomerID, p_AccountType, p_Balance, SYSDATE);
        COMMIT;
    END OpenNewAccount;

    PROCEDURE CloseAccount(p_AccountID IN NUMBER) IS
    BEGIN
        DELETE FROM Accounts
        WHERE AccountID = p_AccountID;
        COMMIT;
    END CloseAccount;

    FUNCTION GetTotalBalance(p_CustomerID IN NUMBER) RETURN NUMBER IS
        v_TotalBalance NUMBER;
    BEGIN
        SELECT SUM(Balance) INTO v_TotalBalance
        FROM Accounts
        WHERE CustomerID = p_CustomerID;
        RETURN v_TotalBalance;
    END GetTotalBalance;
END AccountOperations;
/