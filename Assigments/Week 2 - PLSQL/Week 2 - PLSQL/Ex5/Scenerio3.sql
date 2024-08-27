CREATE OR REPLACE TRIGGER CheckTransactionRules
BEFORE INSERT ON Transactions
FOR EACH ROW
DECLARE
    v_balance NUMBER;
BEGIN
    IF :NEW.TransactionType = 'Withdrawal' THEN
        SELECT Balance INTO v_balance
        FROM Accounts
        WHERE AccountID = :NEW.AccountID;

        IF :NEW.Amount > v_balance THEN
            RAISE_APPLICATION_ERROR(-20004, 'Withdrawal amount exceeds account balance.');
        END IF;
        
    ELSIF :NEW.TransactionType = 'Deposit' THEN
        IF :NEW.Amount <= 0 THEN
            RAISE_APPLICATION_ERROR(-20005, 'Deposit amount must be positive.');
        END IF;
    END IF;
END;
/
