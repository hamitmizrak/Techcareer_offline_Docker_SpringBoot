package com.hamitmizrak.annotation;


import com.hamitmizrak.data.repository.IBlogCategoryRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

// LOMBOK
@RequiredArgsConstructor

// Annotation
public class UniqueBlogCategoryValidation implements ConstraintValidator<UniqueBlogCategoryValidationName,String> {

    // Injection
    private final IBlogCategoryRepository iCategoryRepository;

    // Database'de bu kategori isminden var mı ?
    @Override
    public boolean isValid(String categoryName, ConstraintValidatorContext constraintValidatorContext) {
        Boolean isOtherCategoryName=iCategoryRepository.findByCategoryName(categoryName).isPresent();
        //Eğer database böyle bir category adı varsa bilgilendirme yapsın
        if(isOtherCategoryName){
            return false;
        }
        return true;
    }
}
