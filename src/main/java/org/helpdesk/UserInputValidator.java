package org.helpdesk;

public class UserInputValidator {

    public static boolean isValidEmailAddress(String email) {
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(email);
        return m.matches();
    }

    public static UrgencyType parseTaskUrgency(String taskUrgency) {
        if (taskUrgency == null || taskUrgency.trim().isEmpty()) {
            return UrgencyType.LOW;
        }
        return UrgencyType.valueOf(taskUrgency.trim().toUpperCase());
    }

    public static boolean isValidTaskUrgency(String taskUrgency) {
        try {
            parseTaskUrgency(taskUrgency);
            return true;
        } catch (IllegalArgumentException exception) {
            return false;
        }
    }

    public static boolean isValidDescriptionLength(String description){
        return description != null && description.length() <= 255;
    }
}
