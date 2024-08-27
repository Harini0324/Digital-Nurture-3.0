BEGIN
    FOR rec IN (SELECT LoanID, CustomerID, EndDate FROM Loans
                WHERE EndDate BETWEEN SYSDATE AND SYSDATE + 30) LOOP
        DBMS_OUTPUT.PUT_LINE('Reminder: Loan ' || rec.LoanID || ' for customer ' || rec.CustomerID || ' is due on ' || rec.EndDate);
    END LOOP;
END;