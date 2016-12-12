package com.maktashaf.taymiyyah.web.exception;

/**
 * Runtime business exception.
 *
 * @author Haroon Anwar Padhyar.
 */
public class BusinessException extends RuntimeException{
  private boolean isResolved;
  public BusinessException(){
    super();
  }

  public BusinessException(String message){
    super(message);
  }

  public BusinessException(String message, boolean isResolved){
    super(message);
    this.isResolved = isResolved;
  }

  public boolean isResolved() {
    return isResolved;
  }
}
