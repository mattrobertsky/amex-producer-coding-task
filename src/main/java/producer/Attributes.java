package producer;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Attributes {

  private String accountNumber;
  private String transactionAmount;
  private String name;
  private String product;

  @JsonProperty("Account Number")
  public String getAccountNumber() {
    return accountNumber;
  }

  @JsonProperty("Account Number")
  public void setAccountNumber(String accountNumber) {
    this.accountNumber = accountNumber;
  }

  @JsonProperty("Transaction Amount")
  public String getTransactionAmount() {
    return transactionAmount;
  }

  @JsonProperty("Transaction Amount")
  public void setTransactionAmount(String transactionAmount) {
    this.transactionAmount = transactionAmount;
  }

  @JsonProperty("Name")
  public String getName() {
    return name;
  }

  @JsonProperty("Name")
  public void setName(String name) {
    this.name = name;
  }

  @JsonProperty("Product")
  public String getProduct() {
    return product;
  }

  @JsonProperty("Product")
  public void setProduct(String product) {
    this.product = product;
  }

}
