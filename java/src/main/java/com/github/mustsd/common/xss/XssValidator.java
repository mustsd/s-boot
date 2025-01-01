package com.github.mustsd.common.xss;

import org.springframework.util.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author yangz
 * @date 2022-05-10 17:16
 */
public class XssValidator implements ConstraintValidator<Xss, String> {
  private static final String HTML_PATTERN = "<(\\S*?)[^>]*>.*?|<.*? />";

  @Override
  public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
    if (StringUtils.isEmpty(value)) {
      return true;
    }
    return !containsHtml(value);
  }

  public static boolean containsHtml(String value) {
    Pattern pattern = Pattern.compile(HTML_PATTERN);
    Matcher matcher = pattern.matcher(value);
    return matcher.matches();
  }
}
