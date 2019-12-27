package stepdefinitions.dwp.contracts.b2b;

public class QuoteB2B {

  private String productType;
  private String isFakeAddress;
  private String switchType;
  private String meterType;
  private String kwMax;

  public String getProductType() {
    return productType;
  }

  public void setProductType(String productType) {
    this.productType = productType;
  }

  public String getIsFakeAddress() {
    return isFakeAddress;
  }

  public void setIsFakeAddress(String isFakeAddress) {
    this.isFakeAddress = isFakeAddress;
  }

  public String getSwitchType() {
    return switchType;
  }

  public void setSwitchType(String switchType) {
    this.switchType = switchType;
  }

  public String getMeterType() {
    return meterType;
  }

  public void setMeterType(String meterType) {
    this.meterType = meterType;
  }

  public String getKwMax() {
    return kwMax;
  }

  public void setKwMax(String kwMax) {
    this.kwMax = kwMax;
  }
}
