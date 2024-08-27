DECLARE
    CURSOR cur_loans IS
        SELECT LoanID, InterestRate
        FROM Loans;

    v_loanID Loans.LoanID%TYPE;
    v_current_rate Loans.InterestRate%TYPE;
    v_new_rate NUMBER;
BEGIN
    FOR rec IN cur_loans LOOP
        v_loanID := rec.LoanID;
        v_current_rate := rec.InterestRate;
        v_new_rate := v_current_rate + 0.5;
        UPDATE Loans
        SET InterestRate = v_new_rate
        WHERE LoanID = v_loanID;

        DBMS_OUTPUT.PUT_LINE('Loan ID: ' || v_loanID || ' - Interest rate updated from ' || v_current_rate || ' to ' || v_new_rate);
    END LOOP;
    COMMIT;
END;
/
