CREATE OR REPLACE FUNCTION CalculateMonthlyInstallment (
    p_loan_amount IN NUMBER,
    p_annual_interest_rate IN NUMBER,
    p_loan_duration_years IN NUMBER
) RETURN NUMBER IS
    v_monthly_installment NUMBER;
    v_monthly_interest_rate NUMBER;
    v_total_months NUMBER;
BEGIN
    v_monthly_interest_rate := p_annual_interest_rate / 12 / 100;
    v_total_months := p_loan_duration_years * 12;
    IF v_monthly_interest_rate > 0 THEN
        v_monthly_installment := p_loan_amount * (v_monthly_interest_rate * POWER(1 + v_monthly_interest_rate, v_total_months)) / (POWER(1 + v_monthly_interest_rate, v_total_months) - 1);
    ELSE
        v_monthly_installment := p_loan_amount / v_total_months;
    END IF;

    RETURN v_monthly_installment;
END;
/