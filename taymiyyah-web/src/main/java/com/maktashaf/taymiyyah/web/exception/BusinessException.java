package com.maktashaf.taymiyyah.web.exception;

/**
 * Runtime business exception.
 *
 * @author Haroon Anwar Padhyar.
 */
public class BusinessException extends RuntimeException{
  public BusinessException(){
    super();
  }

  public BusinessException(String message){
    super(message);
  }
}
