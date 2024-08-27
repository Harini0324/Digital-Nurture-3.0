DECLARE
    CURSOR cur_accounts IS
        SELECT AccountID, Balance
        FROM Accounts;

    v_accountID Accounts.AccountID%TYPE;
    v_balance Accounts.Balance%TYPE;
    v_fee NUMBER := 50;
BEGIN
    FOR rec IN cur_accounts LOOP
        v_accountID := rec.AccountID;
        v_balance := rec.Balance;
        UPDATE Accounts
        SET Balance = Balance - v_fee
        WHERE AccountID = v_accountID;

        DBMS_OUTPUT.PUT_LINE('Account ID: ' || v_accountID || ' - Fee of ' || v_fee || ' applied. New Balance: ' || (v_balance - v_fee));
    END LOOP;
    COMMIT;
END;
/
