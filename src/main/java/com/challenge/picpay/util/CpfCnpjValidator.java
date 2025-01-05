package com.challenge.picpay.util;

/**
 * Utility class for validating CPF and CNPJ numbers.
 *
 * <p>Rules for validation:</p>
 * - CPF: Must have 11 digits. The last two digits (check digits) are calculated
 *   based on the first 9 digits using the Modulo 11 algorithm.
 * - CNPJ: Must have 14 digits. The last two digits (check digits) are calculated
 *   based on the first 12 digits using the Modulo 11 algorithm.
 * - Numbers with all digits equal (e.g., 111.111.111-11 or 22.222.222/2222-22) are invalid.
 *
 * This class validates both format (with or without mask) and mathematical consistency.
 */
public class CpfCnpjValidator {

    /**
     * Checks whether the given CPF or CNPJ is valid.
     *
     * @param cpfCnpj The CPF or CNPJ to validate.
     * @return {@code true} if the CPF or CNPJ is valid; {@code false} otherwise.
     */
    public static boolean isValid(String cpfCnpj) {

        // Remove non-numeric characters
        String sanitized = cpfCnpj.replaceAll("\\D", "");

        if (sanitized.length() == 11) {
            return isValidCpf(sanitized);
        } else if (sanitized.length() == 14) {
            return isValidCnpj(sanitized);
        }

        return false;
    }

    /**
     * Validates a CPF using the Modulo 11 algorithm.
     *
     * @param cpf The CPF to validate (numeric characters only).
     * @return {@code true} if the CPF is valid; {@code false} otherwise.
     */
    private static boolean isValidCpf(String cpf) {
        // Reject CPFs with all digits equal (e.g., 111.111.111-11)
        if (cpf.matches("(\\d)\\1{10}")) {
            return false;
        }

        // Validate the first check digit
        int[] weights1 = {10, 9, 8, 7, 6, 5, 4, 3, 2};
        if (!validateDigit(cpf, weights1, 9)) {
            return false;
        }

        // Validate the second check digit
        int[] weights2 = {11, 10, 9, 8, 7, 6, 5, 4, 3, 2};
        return validateDigit(cpf, weights2, 10);
    }

    /**
     * Validates a CNPJ using the Modulo 11 algorithm.
     *
     * @param cnpj The CNPJ to validate (numeric characters only).
     * @return {@code true} if the CNPJ is valid; {@code false} otherwise.
     */
    private static boolean isValidCnpj(String cnpj) {
        // Reject CNPJs with all digits equal (e.g., 22.222.222/2222-22)
        if (cnpj.matches("(\\d)\\1{13}")) {
            return false;
        }

        // Validate the first check digit
        int[] weights1 = {5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};
        if (!validateDigit(cnpj, weights1, 12)) {
            return false;
        }

        // Validate the second check digit
        int[] weights2 = {6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};
        return validateDigit(cnpj, weights2, 13);
    }

    /**
     * Validates a check digit using weights and the Modulo 11 algorithm.
     *
     * @param number The number to validate.
     * @param weights The weights applied for validation.
     * @param position The position of the check digit in the number.
     * @return {@code true} if the check digit is valid; {@code false} otherwise.
     */
    private static boolean validateDigit(String number, int[] weights, int position) {
        int sum = 0;

        for (int i = 0; i < weights.length; i++) {
            sum += Character.getNumericValue(number.charAt(i)) * weights[i];
        }

        int remainder = sum % 11;
        int digit = (remainder < 2) ? 0 : 11 - remainder;

        return digit == Character.getNumericValue(number.charAt(position));
    }
}
