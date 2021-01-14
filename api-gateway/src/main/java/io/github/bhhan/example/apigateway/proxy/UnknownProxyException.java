package io.github.bhhan.example.apigateway.proxy;

public class UnknownProxyException extends RuntimeException {
  public UnknownProxyException(String message) {
    super(message);
  }
}
