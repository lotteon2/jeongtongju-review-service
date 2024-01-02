package com.jeontongju.review.exception.common;

import io.github.bitbox.bitbox.enums.FailureTypeEnum;

public class CustomFailureException extends RuntimeException {

  FailureTypeEnum failure;

  public CustomFailureException(FailureTypeEnum failure) {
    super(failure.getValue());
    this.failure = failure;
  }

  public FailureTypeEnum getFailure() {
    return failure;
  }

}
