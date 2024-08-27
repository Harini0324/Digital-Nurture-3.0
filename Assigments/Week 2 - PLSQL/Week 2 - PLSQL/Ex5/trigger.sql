CREATE OR REPLACE TRIGGER LogTransaction
AFTER INSERT ON Transactions
FOR EACH ROW
BEGIN
    INSERT INTO AuditLog (TransactionID, ActionType)
    VALUES (:NEW.TransactionID, 'INSERT');
END;
/
