package com.mintyn.test.validation;

import com.mintyn.test.common.validation.BaseValidator;
import com.mintyn.test.common.validation.CommonValidatorUtils;
import com.mintyn.test.dto.OrderDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class OrderDtoValidator implements BaseValidator<OrderDto> {
    private static final String FIELD_PRODUCT_ID = "Product";
    private final CommonValidatorUtils commonValidatorUtils;

    @Autowired
    public OrderDtoValidator(
            CommonValidatorUtils commonValidatorUtils) {
        this.commonValidatorUtils = commonValidatorUtils;
    }

    @Override
    public void validate(OrderDto obj) {
        this.commonValidatorUtils.validateRequiredField(FIELD_PRODUCT_ID, obj.getProductId());

    }

}
