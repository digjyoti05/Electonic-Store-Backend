package com.Digjyoti.electronic.store.validate;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ImageNameValidation implements ConstraintValidator <ImageNameValid,String>{
    private Logger logger= LoggerFactory.getLogger(ImageNameValidation.class);
    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        logger.info("Message For Valid{}",s);
//        Logic for iMAGE Valid
        if (s.isBlank()) {
            return false;

        }else {
            return true;
        }

    }
}
