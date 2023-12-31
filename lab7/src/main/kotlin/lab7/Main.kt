package lab7

import lab2.generated.ProductWebServiceStub
import org.apache.axis2.AxisFault
import org.apache.juddi.api_v3.AccessPointType
import org.apache.juddi.v3.client.UDDIConstants
import org.apache.juddi.v3.client.config.UDDIClient
import org.uddi.api_v3.*
import org.uddi.v3_service.UDDIInquiryPortType
import org.uddi.v3_service.UDDIPublicationPortType
import org.uddi.v3_service.UDDISecurityPortType
import kotlin.system.exitProcess

fun main(args: Array<String>) {
    if (args.size < 2) {
        println("Provide more arguments!")
        exitProcess(-1)
    }

    var mode = ClientMode.Register
    var serviceName = ""
    var wsdlAddress = ""

    for (i in args.indices) {
        if (args[i] == "--mode") {
            mode = ClientMode.valueOf(args[i + 1])
        }
        if (args[i] == "--name") {
            serviceName = args[i + 1]
        }
        if (args[i] == "--wsdl") {
            wsdlAddress = args[i + 1]
        }
    }

    val app = JUDDIApp()
    val token = app.getUDDIToken("uddiadmin", "da_password1")

    if (mode == ClientMode.Register) {
        app.registerNewService(token, "Custom Business", serviceName, wsdlAddress)
        println("Successfully registered service $serviceName on address: $wsdlAddress")
        return
    }

    if (mode == ClientMode.Find) {
        val accessPoint = app.searchService(
            app.getBusinees(token).businessInfos,
            token,
            serviceName
        )

        if (accessPoint == "") {
            println("Can't find service with name: $serviceName")
            exitProcess(-1)
        }

        println("Requesting service(create new product)...")
        val stub = ProductWebServiceStub()
        stub._getServiceClient().options.soapVersionURI =
            org.apache.axiom.soap.SOAP11Constants.SOAP_ENVELOPE_NAMESPACE_URI

        val product = ProductWebServiceStub.Product()
        product.id = 0
        product.name = "Product"
        product.price = 10.0f
        product.producedBy = "By"
        product.sellAmount = 10000

        var resp = ProductWebServiceStub.Response()
        resp.code = -1
        resp.message = "Error"

        val createRequest = ProductWebServiceStub.Create()
        createRequest.arg0 = product

        val createOp = ProductWebServiceStub.CreateE()
        createOp.create = createRequest

        try {
            resp = stub.create(createOp).createResponse._return
        } catch (ex: AxisFault) {
            resp.message = ex.message
        }

        println("Status code: ${resp.code}")
        println("Status message: ${resp.message}")
    }

    app.security.discardAuthToken(DiscardAuthToken(token))
}

class JUDDIApp {
    val security: UDDISecurityPortType
    private val inquiry: UDDIInquiryPortType
    private val publish: UDDIPublicationPortType

    init {
        val client = UDDIClient("META-INF/uddi.xml")
        val transport = client.getTransport("default")

        security = transport.uddiSecurityService
        inquiry = transport.uddiInquiryService
        publish = transport.uddiPublishService
    }

    fun getUDDIToken(jUDDIUserName: String, jUDDIUSerPass: String): String? {
        var token = ""

        val getAuthToken = GetAuthToken()
        getAuthToken.userID = jUDDIUserName
        getAuthToken.cred = jUDDIUSerPass

        val authToken = security!!.getAuthToken(getAuthToken)
        token = authToken.authInfo
        return token
    }

    fun registerNewService(
        token: String?, businessName: String, registeredServiceName: String,
        registeredServiceURL: String
    ) {
        // Creating the parent business entity that will contain our service.
        val myBusEntity = BusinessEntity()
        val myBusName = Name()
        myBusName.value = businessName
        myBusEntity.getName().add(myBusName)

        // Adding the business entity to the "save" structure, using our
        // publisher's authentication info and saving away.
        val sb = SaveBusiness()
        sb.getBusinessEntity().add(myBusEntity)
        sb.authInfo = token
        val bd = publish.saveBusiness(sb)
        val myBusKey = bd.getBusinessEntity()[0].businessKey

        val myService = BusinessService()
        myService.businessKey = myBusKey
        val myServName = Name()
        myServName.value = registeredServiceName
        myService.getName().add(myServName)

        var myBindingTemplate = BindingTemplate()
        val accessPoint = AccessPoint()
        accessPoint.useType = AccessPointType.WSDL_DEPLOYMENT.toString()
        accessPoint.value = registeredServiceURL
        myBindingTemplate.accessPoint = accessPoint
        val myBindingTemplates = BindingTemplates()

        myBindingTemplate = UDDIClient.addSOAPtModels(myBindingTemplate)
        myBindingTemplates.getBindingTemplate().add(myBindingTemplate)
        myService.bindingTemplates = myBindingTemplates

        val ss = SaveService()
        ss.getBusinessService().add(myService)
        ss.authInfo = token
        val sd = publish.saveService(ss)
        val myServKey = sd.getBusinessService()[0].serviceKey

        println("Service auth key:  $myServKey")
        println("New service waiting for requests!")
    }

    fun getBusinees(token: String?): BusinessList {
        val fb = FindBusiness()
        fb.authInfo = token
        val fq = FindQualifiers()
        fq.getFindQualifier().add(UDDIConstants.APPROXIMATE_MATCH)
        fb.findQualifiers = fq
        val searchName = Name()
        searchName.value = UDDIConstants.WILDCARD
        fb.getName().add(searchName)
        return inquiry.findBusiness(fb)
    }

    fun searchService(businessInfos: BusinessInfos, token: String?, serviceName: String): String {
        for (i in businessInfos.getBusinessInfo().indices) {
            val gsd = GetServiceDetail()
            try {
                for (k in businessInfos.getBusinessInfo()[i].serviceInfos.getServiceInfo().indices) {
                    gsd.getServiceKey()
                        .add(businessInfos.getBusinessInfo()[i].serviceInfos.getServiceInfo()[k].serviceKey)
                }
                gsd.authInfo = token
                val serviceDetail = inquiry.getServiceDetail(gsd)
                for (k in serviceDetail.getBusinessService().indices) {
                    val get = serviceDetail.getBusinessService()[k]
                    if (listToString(get.getName()) == serviceName) {
                        println("Fetched access point for business: $businessInfos.getBusinessInfo()[i].businessKey")
                        return getServiceAccessPoint(get.bindingTemplates)
                    }
                }
            } catch (ex: NullPointerException) {
                return ""
            }
        }
        return ""
    }

    private fun getServiceAccessPoint(bindingTemplates: BindingTemplates?): String {
        if (bindingTemplates == null) {
            return ""
        }

        var serviceAccessPoint: String? = null

        for (i in bindingTemplates.getBindingTemplate().indices) {
            if (bindingTemplates.getBindingTemplate()[i].accessPoint != null) {
                if (bindingTemplates.getBindingTemplate()[i].accessPoint.useType != null) {
                    if (bindingTemplates.getBindingTemplate()[i].accessPoint.useType.equals(
                            AccessPointType.WSDL_DEPLOYMENT.toString(),
                            ignoreCase = true
                        )
                    ) {
                        serviceAccessPoint = bindingTemplates.getBindingTemplate()[i].accessPoint.value
                    }
                }
            }
        }
        return serviceAccessPoint ?: ""
    }

    private fun listToString(name: List<Name>): String {
        val sb = StringBuilder()
        for (value in name) {
            sb.append(value.value)
        }
        return sb.toString()
    }
}