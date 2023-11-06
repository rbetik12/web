/**
 * ProductWebServiceCallbackHandler.java
 *
 * <p>This file was auto-generated from WSDL by the Apache Axis2 version: 1.8.2 Built on : Jul 13,
 * 2022 (06:38:03 EDT)
 */
package lab2.generated;

/**
 * ProductWebServiceCallbackHandler Callback class, Users can extend this class and implement their
 * own receiveResult and receiveError methods.
 */
public abstract class ProductWebServiceCallbackHandler {

  protected Object clientData;

  /**
   * User can pass in any object that needs to be accessed once the NonBlocking Web service call is
   * finished and appropriate method of this CallBack is called.
   *
   * @param clientData Object mechanism by which the user can pass in user data that will be
   *     avilable at the time this callback is called.
   */
  public ProductWebServiceCallbackHandler(Object clientData) {
    this.clientData = clientData;
  }

  /** Please use this constructor if you don't want to set any clientData */
  public ProductWebServiceCallbackHandler() {
    this.clientData = null;
  }

  /** Get the client data */
  public Object getClientData() {
    return clientData;
  }

  /**
   * auto generated Axis2 call back method for delete method override this method for handling
   * normal response from delete operation
   */
  public void receiveResultdelete(lab2.generated.ProductWebServiceStub.DeleteResponseE result) {}

  /**
   * auto generated Axis2 Error handler override this method for handling error response from delete
   * operation
   */
  public void receiveErrordelete(java.lang.Exception e) {}

  /**
   * auto generated Axis2 call back method for update method override this method for handling
   * normal response from update operation
   */
  public void receiveResultupdate(lab2.generated.ProductWebServiceStub.UpdateResponseE result) {}

  /**
   * auto generated Axis2 Error handler override this method for handling error response from update
   * operation
   */
  public void receiveErrorupdate(java.lang.Exception e) {}

  /**
   * auto generated Axis2 call back method for create method override this method for handling
   * normal response from create operation
   */
  public void receiveResultcreate(lab2.generated.ProductWebServiceStub.CreateResponseE result) {}

  /**
   * auto generated Axis2 Error handler override this method for handling error response from create
   * operation
   */
  public void receiveErrorcreate(java.lang.Exception e) {}

  /**
   * auto generated Axis2 call back method for receiveBinary method override this method for
   * handling normal response from receiveBinary operation
   */
  public void receiveResultreceiveBinary(
      lab2.generated.ProductWebServiceStub.ReceiveBinaryResponseE result) {}

  /**
   * auto generated Axis2 Error handler override this method for handling error response from
   * receiveBinary operation
   */
  public void receiveErrorreceiveBinary(java.lang.Exception e) {}

  /**
   * auto generated Axis2 call back method for read method override this method for handling normal
   * response from read operation
   */
  public void receiveResultread(lab2.generated.ProductWebServiceStub.ReadResponseE result) {}

  /**
   * auto generated Axis2 Error handler override this method for handling error response from read
   * operation
   */
  public void receiveErrorread(java.lang.Exception e) {}
}
