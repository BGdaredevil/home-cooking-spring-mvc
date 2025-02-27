package com.cooking.home_recipes.utils;

import org.springframework.ui.Model;

public class PureFunctions {

    public static ErrorMessagesCarrier getErrorsObj(Model model) {
        var errors = model.getAttribute(Const.ERRORS_MODEL_NAME);
        if (errors == null) {
            errors = new ErrorMessagesCarrier();
            model.addAttribute(Const.ERRORS_MODEL_NAME, errors);
        }

        return (ErrorMessagesCarrier) errors;
    }
}
