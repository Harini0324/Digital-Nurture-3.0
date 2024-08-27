DECLARE
    CURSOR cur_transactions IS
        SELECT t.TransactionID, t.AccountID, t.TransactionDate, t.Amount, t.TransactionType, a.CustomerID
        FROM Transactions t
        JOIN Accounts a ON t.AccountID = a.AccountID
        WHERE EXTRACT(MONTH FROM t.TransactionDate) = EXTRACT(MONTH FROM SYSDATE)
          AND EXTRACT(YEAR FROM t.TransactionDate) = EXTRACT(YEAR FROM SYSDATE);
    
    v_transactionID Transactions.TransactionID%TYPE;
    v_accountID Transactions.AccountID%TYPE;
    v_transactionDate Transactions.TransactionDate%TYPE;
    v_amount Transactions.Amount%TYPE;
    v_transactionType Transactions.TransactionType%TYPE;
    v_customerID Accounts.CustomerID%TYPE;
BEGIN
    FOR rec IN cur_transactions LOOP
        v_transactionID := rec.TransactionID;
        v_accountID := rec.AccountID;
        v_transactionDate := rec.TransactionDate;
        v_amount := rec.Amount;
        v_transactionType := rec.TransactionType;
        v_customerID := rec.CustomerID;

        DBMS_OUTPUT.PUT_LINE('Customer ID: ' || v_customerID || ', Transaction ID: ' || v_transactionID || ', Account ID: ' || v_accountID || ', Date: ' || v_transactionDate || ', Amount: ' || v_amount || ', Type: ' || v_transactionType);
    END LOOP;
END;
/
